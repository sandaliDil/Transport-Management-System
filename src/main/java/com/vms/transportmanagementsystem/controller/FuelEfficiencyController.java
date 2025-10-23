package com.vms.transportmanagementsystem.controller;

import com.vms.transportmanagementsystem.DTO.FuelEfficiencyDTO;
import com.vms.transportmanagementsystem.service.FuelService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class FuelEfficiencyController {
    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private ComboBox<Integer> yearComboBox;

    @FXML
    private TableView<FuelEfficiencyDTO> efficiencyTable;

    @FXML
    private TableColumn<FuelEfficiencyDTO, String> vehicleCol;

    @FXML
    private TableColumn<FuelEfficiencyDTO, Double> distanceCol;

    @FXML
    private TableColumn<FuelEfficiencyDTO, Double> fuelQtyCol;

    @FXML
    private TableColumn<FuelEfficiencyDTO, Double> efficiencyCol;

    private final FuelService fuelService = new FuelService();

    @FXML
    public void initialize() {
        // Initialize month combo (Jan-Dec)
        monthComboBox.getItems().addAll(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );

        // Initialize year combo (e.g., 2020-2030)
        for (int year = 2020; year <= 2030; year++) {
            yearComboBox.getItems().add(year);
        }

        // Select current month/year by default
        LocalDate now = LocalDate.now();
        monthComboBox.setValue(now.getMonth().name().substring(0,1).toUpperCase() + now.getMonth().name().substring(1).toLowerCase());
        yearComboBox.setValue(now.getYear());

        // Setup table columns
        vehicleCol.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        distanceCol.setCellValueFactory(new PropertyValueFactory<>("totalDistance"));
        fuelQtyCol.setCellValueFactory(new PropertyValueFactory<>("totalFuelQuantity"));
        efficiencyCol.setCellValueFactory(cellData -> {
            FuelEfficiencyDTO dto = cellData.getValue();
            double efficiency = dto.getTotalQuantity() == 0 ? 0 : dto.getTotalDistance() / dto.getTotalQuantity();
            return javafx.beans.property.SimpleDoubleProperty.doubleProperty(new SimpleDoubleProperty(efficiency).asObject()).asObject();
        });

        // Trigger initial load
        loadReport();
    }

    @FXML
    private void onGenerateReport() {
        loadReport();
    }

    private void loadReport() {
        String monthName = monthComboBox.getValue();
        Integer year = yearComboBox.getValue();

        if (monthName == null || year == null) {
            showAlert("Please select both month and year.");
            return;
        }

        int month = monthComboBox.getItems().indexOf(monthName) + 1; // Jan=1, Feb=2, etc.
        YearMonth yearMonth = YearMonth.of(year, month);

        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<FuelEfficiencyDTO> report = fuelService.calculateMonthlyFuelEfficiency(startDate, endDate);

        efficiencyTable.setItems(FXCollections.observableArrayList(report));
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
