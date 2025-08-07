package com.vms.transportmanagementsystem.controller;

import com.vms.transportmanagementsystem.enitiy.Fuel;
import com.vms.transportmanagementsystem.service.FuelService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

public class FuelViewController {

    public TableView<Fuel> fuelTable;

    public TableColumn<Fuel, String> registrationNumColumn;
    public TableColumn<Fuel, String> colFuelType;
    public TableColumn<Fuel, LocalDate> colDate;
    public TableColumn<Fuel, Float> colPerMileage;
    public TableColumn<Fuel, Float> colPostMileage;
    public TableColumn<Fuel, Float> quantity;
    @FXML
    private javafx.scene.control.TextField searchField;
    private ObservableList<Fuel> masterData = FXCollections.observableArrayList();

    private final FuelService fuelService = new FuelService();

    @FXML
    private Pagination pagination;

    private static final int ROWS_PER_PAGE = 10;
    private FilteredList<Fuel> filteredData;

    @FXML
    public void initialize() {
        loadFuelTable();

        // Filtering logic
        filteredData = new FilteredList<>(masterData, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(fuel -> {
                if (newValue == null || newValue.isEmpty()) return true;

                String lowerCaseFilter = newValue.toLowerCase();
                String regNum = (fuel.getVehicle() != null && fuel.getVehicle().getRegistrationNum() != null)
                        ? fuel.getVehicle().getRegistrationNum().toLowerCase()
                        : "";

                return regNum.contains(lowerCaseFilter);
            });

            updatePagination(); // Re-calculate pagination on search
        });

        updatePagination(); // Initial pagination setup
    }

    private void updatePagination() {
        int pageCount = (int) Math.ceil((double) filteredData.size() / ROWS_PER_PAGE);
        pagination.setPageCount(pageCount > 0 ? pageCount : 1);
        pagination.setPageFactory(this::createPage);
    }

    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, filteredData.size());

        SortedList<Fuel> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(fromIndex, toIndex))
        );
        sortedData.comparatorProperty().bind(fuelTable.comparatorProperty());

        fuelTable.setItems(sortedData);
        return fuelTable;
    }



    private void loadFuelTable() {
        List<Fuel> fuels = fuelService.getAllFuel();

        // Fill masterData instead of creating a separate list
        masterData.setAll(fuels);

        registrationNumColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getVehicle().getRegistrationNum()));

        colFuelType.setCellValueFactory(new PropertyValueFactory<>("fuelType"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPerMileage.setCellValueFactory(new PropertyValueFactory<>("perMileage"));
        colPostMileage.setCellValueFactory(new PropertyValueFactory<>("postMileage"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

    }

}
