package com.vms.transportmanagementsystem.controller;

import com.vms.transportmanagementsystem.enitiy.Maintenance;
import com.vms.transportmanagementsystem.enitiy.Vehicle;
import com.vms.transportmanagementsystem.service.MaintenanceService;
import com.vms.transportmanagementsystem.service.VehicleService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaintenanceController {

    @FXML private ComboBox<String> serviceTypeField;
    @FXML private DatePicker datePicker;
    @FXML private TextField costField;
    @FXML private TextArea descriptionField;
    @FXML private TextField statusField;
    @FXML
    private ComboBox<String> vehicleNumComboBox;
    private byte[] imageBytes;

    @FXML private TextField searchField;
    @FXML
    private TableView<Maintenance> maintenanceTable;
    @FXML
    private TableColumn<Maintenance, String> registrationNumColumn;
    @FXML
    private TableColumn<Maintenance, String> serviceTypeColumn;
    @FXML
    private TableColumn<Maintenance, LocalDate> dateColumn;
    @FXML
    private TableColumn<Maintenance, Double> costColumn;
    @FXML
    private TableColumn<Maintenance, String> descriptionColumn;
    @FXML
    private TableColumn<Maintenance, ImageView> imageColumn;

    private FilteredList<Maintenance> filteredData;
    private final MaintenanceService maintenanceService = new MaintenanceService();
    private final VehicleService vehicleService = new VehicleService();
    private Map<String, Integer> vehicleNumToIdMap = new HashMap<>();

    @FXML
    public void initialize() {

        serviceTypeField.getItems().addAll("Tire repair", "Battery repair", "Repairs carried out by the institution",
                "Repairs carried out outside the institution","Other");
        // Set cell value factories for basic columns
        registrationNumColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getVehicle().getRegistrationNum()));
        serviceTypeColumn.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Load maintenance list once
        List<Maintenance> list = maintenanceService.getAllMaintenance();

        // Wrap list into FilteredList
        filteredData = new FilteredList<>(FXCollections.observableArrayList(list), p -> true);

        // Add listener to searchField to filter on typing
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            String filter = newVal.toLowerCase().trim();

            filteredData.setPredicate(maintenance -> {
                if (filter.isEmpty()) {
                    return true; // No filter, show all
                }
                if (maintenance.getVehicle().getRegistrationNum().toLowerCase().contains(filter)) {
                    return true;
                }
                if (maintenance.getServiceType().toLowerCase().contains(filter)) {
                    return true;
                }
                if (maintenance.getDescription() != null && maintenance.getDescription().toLowerCase().contains(filter)) {
                    return true;
                }
                if (maintenance.getStatus() != null && maintenance.getStatus().toLowerCase().contains(filter)) {
                    return true;
                }
                return false;
            });
        });

        // Wrap FilteredList in SortedList for TableView sorting support
        SortedList<Maintenance> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(maintenanceTable.comparatorProperty());

        // Bind sorted and filtered data to the table
        maintenanceTable.setItems(sortedData);

        // Load vehicle numbers for ComboBox
        loadVehicleNumbers();
    }




    private void loadMaintenanceData() {
        List<Maintenance> list = maintenanceService.getAllMaintenance();
        maintenanceTable.setItems(FXCollections.observableArrayList(list));
    }
    private void loadVehicleNumbers() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();

        for (Vehicle vehicle : vehicles) {
            vehicleNumToIdMap.put(vehicle.getRegistrationNum(), vehicle.getVehicleId());
        }

        vehicleNumComboBox.setItems(FXCollections.observableArrayList(vehicleNumToIdMap.keySet()));
    }




    @FXML
    private void handleChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (FileInputStream fis = new FileInputStream(file)) {
                imageBytes = fis.readAllBytes();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Image selected successfully");
                alert.show();
            } catch (IOException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to load image").show();
            }
        }
    }

    @FXML
    private void handleSave() {
        try {
            String selectedVehicleNum = vehicleNumComboBox.getValue();
            if (selectedVehicleNum == null || !vehicleNumToIdMap.containsKey(selectedVehicleNum)) {
                new Alert(Alert.AlertType.ERROR, "Please select a valid vehicle number").show();
                return;
            }

            int vehicleId = vehicleNumToIdMap.get(selectedVehicleNum);

            Maintenance maintenance = new Maintenance();
            maintenance.setVehicleId(vehicleId);
            maintenance.setServiceType(serviceTypeField.getValue());
            maintenance.setDate(Date.valueOf(datePicker.getValue()).toLocalDate());
            maintenance.setCost(Double.parseDouble(costField.getText()));
            maintenance.setDescription(descriptionField.getText());
            maintenance.setStatus(statusField.getText());
            maintenance.setImage(imageBytes);

            boolean saved = maintenanceService.saveMaintenance(maintenance);

            if (saved) {
                new Alert(Alert.AlertType.INFORMATION, "Maintenance saved successfully!").show();
                clearFields();
                refreshTableData();   // <-- Call this method here
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save maintenance").show();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please check inputs: " + e.getMessage()).show();
        }
    }


    @SuppressWarnings("unchecked")
    private void refreshTableData() {
        List<Maintenance> newList = maintenanceService.getAllMaintenance();
        ((javafx.collections.ObservableList<Maintenance>) filteredData.getSource()).setAll(newList);
    }


    private void clearFields() {
        serviceTypeField.setValue(null);
        datePicker.setValue(null);
        costField.clear();
        descriptionField.clear();
        statusField.clear();
        imageBytes = null;
    }
}
