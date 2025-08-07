package com.vms.transportmanagementsystem.controller;

import com.vms.transportmanagementsystem.enitiy.Vehicle;
import com.vms.transportmanagementsystem.service.VehicleService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class VehicleController {
    @FXML
    private TextField registrationNumField;
    @FXML private TextField modelField;
    @FXML private TextField typeField;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private DatePicker licenseRenewalDatePicker;
    @FXML private DatePicker emissionTestDatePicker;
    @FXML private DatePicker insuranceRenewalDatePicker;
    @FXML private DatePicker serviceDatePicker;
    @FXML private TextField searchField;
    @FXML private TableView<Vehicle> vehicleTable;
    @FXML private TableColumn<Vehicle, Integer> idColumn;
    @FXML private TableColumn<Vehicle, String> regNumColumn;
    @FXML private TableColumn<Vehicle, String> modelColumn;
    @FXML private TableColumn<Vehicle, String> typeColumn;
    @FXML private TableColumn<Vehicle, String> statusColumn;
    @FXML private TableColumn<Vehicle, LocalDate> licenseDateColumn;
    @FXML private TableColumn<Vehicle, LocalDate> emissionDateColumn;
    @FXML private TableColumn<Vehicle, LocalDate> insuranceDateColumn;
    @FXML private TableColumn<Vehicle, LocalDate> serviceDateColumn;
    private Vehicle currentVehicle;


    private final VehicleService service = new VehicleService();
    private final ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        statusComboBox.getItems().addAll("Active", "Inactive", "Unavailable");

        // Table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        regNumColumn.setCellValueFactory(new PropertyValueFactory<>("registrationNum"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        licenseDateColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleLicenseRenewalDate"));
        emissionDateColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleEmissionTestingDate"));
        insuranceDateColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleInsuranceRenewalDate"));
        serviceDateColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleServiceDate"));

        loadVehicles();

        // Live search
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterVehicleList(newValue);
        });


    }


    private void filterVehicleList(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            vehicleTable.setItems(vehicleList);
            return;
        }

        String lowerKeyword = keyword.toLowerCase();
        ObservableList<Vehicle> filtered = FXCollections.observableArrayList();

        for (Vehicle v : vehicleList) {
            if (String.valueOf(v.getVehicleId()).contains(lowerKeyword)
                    || v.getRegistrationNum().toLowerCase().contains(lowerKeyword)
                    || v.getModel().toLowerCase().contains(lowerKeyword)
                    || v.getType().toLowerCase().contains(lowerKeyword)
                    || v.getStatus().toLowerCase().contains(lowerKeyword)) {
                filtered.add(v);
            }
        }

        vehicleTable.setItems(filtered);
    }


    @FXML
    private void handleSaveVehicle() {
        String regNum = registrationNumField.getText();
        String model = modelField.getText();
        String type = typeField.getText();
        String status = statusComboBox.getValue();
        LocalDate licenseDate = licenseRenewalDatePicker.getValue();
        LocalDate emissionDate = emissionTestDatePicker.getValue();
        LocalDate insuranceDate = insuranceRenewalDatePicker.getValue();
        LocalDate serviceDate = serviceDatePicker.getValue();

        if (regNum.isEmpty() || model.isEmpty() || type.isEmpty() || status == null
                || licenseDate == null || emissionDate == null || insuranceDate == null || serviceDate == null) {
            showAlert("Validation Error", "Please fill in all fields.");
            return;
        }

        Vehicle vehicle = new Vehicle(0, serviceDate, insuranceDate, emissionDate, licenseDate, status, type, model, regNum);
        service.saveVehicle(vehicle);
        showAlert("Success", "Vehicle saved successfully!");
        clearFields();
        loadVehicles();

    }
    private void loadVehicles() {
        List<Vehicle> list = service.getAllVehicles();
        Collections.reverse(list); // Optional: newest first
        vehicleList.setAll(list);
        vehicleTable.setItems(vehicleList);
    }

    private void clearFields() {
        registrationNumField.clear();
        modelField.clear();
        typeField.clear();
        statusComboBox.setValue(null);
        licenseRenewalDatePicker.setValue(null);
        emissionTestDatePicker.setValue(null);
        insuranceRenewalDatePicker.setValue(null);
        serviceDatePicker.setValue(null);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleSearchVehicle() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            showAlert("Search Error", "Please enter registration number or ID.");
            return;
        }

        Vehicle found = service.findByKeyword(keyword);
        if (found != null) {
            currentVehicle = found;
            populateForm(found);
        } else {
            showAlert("Not Found", "No vehicle found with given keyword.");
        }
    }

    private void populateForm(Vehicle vehicle) {
        registrationNumField.setText(vehicle.getRegistrationNum());
        modelField.setText(vehicle.getModel());
        typeField.setText(vehicle.getType());
        statusComboBox.setValue(vehicle.getStatus());
        licenseRenewalDatePicker.setValue(vehicle.getVehicleLicenseRenewalDate());
        emissionTestDatePicker.setValue(vehicle.getVehicleEmissionTestingDate());
        insuranceRenewalDatePicker.setValue(vehicle.getVehicleInsuranceRenewalDate());
        serviceDatePicker.setValue(vehicle.getVehicleServiceDate());
    }

    @FXML
    private void handleUpdateVehicle() {
        if (currentVehicle == null) {
            showAlert("Update Error", "Please search and load a vehicle first.");
            return;
        }

        currentVehicle.setRegistrationNum(registrationNumField.getText());
        currentVehicle.setModel(modelField.getText());
        currentVehicle.setType(typeField.getText());
        currentVehicle.setStatus(statusComboBox.getValue());
        currentVehicle.setVehicleLicenseRenewalDate(licenseRenewalDatePicker.getValue());
        currentVehicle.setVehicleEmissionTestingDate(emissionTestDatePicker.getValue());
        currentVehicle.setVehicleInsuranceRenewalDate(insuranceRenewalDatePicker.getValue());
        currentVehicle.setVehicleServiceDate(serviceDatePicker.getValue());

        service.updateVehicle(currentVehicle);
        showAlert("Success", "Vehicle updated successfully!");

        clearFields();
        loadVehicles(); // ✅ Refresh the table
    }


}
