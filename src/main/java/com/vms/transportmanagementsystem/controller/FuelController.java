package com.vms.transportmanagementsystem.controller;

import com.vms.transportmanagementsystem.enitiy.Fuel;
import com.vms.transportmanagementsystem.enitiy.Vehicle;
import com.vms.transportmanagementsystem.service.FuelService;
import com.vms.transportmanagementsystem.service.VehicleService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.util.List;

public class FuelController {

    @FXML
    private ComboBox<Vehicle> vehicleComboBox;
    @FXML
    private ComboBox<String> fuelTypeField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField perMileageField;
    @FXML
    private TextField postMileageField;
    @FXML
    private TextField quantityField;
    @FXML
    private ComboBox<String> fuelStationField;


    private final FuelService fuelService = new FuelService();
    private final VehicleService vehicleService = new VehicleService();

    @FXML
    public void initialize() {
        loadVehicles();
        loadFuelTypes();
        setFuelStations();
    }

    private void loadFuelTypes() {
        ObservableList<String> fuelTypes = FXCollections.observableArrayList("Diesel", "Petrol");
        fuelTypeField.setItems(fuelTypes);
    }

    private void setFuelStations() {
        ObservableList<String> stations = FXCollections.observableArrayList("Retiyala", "Kandana");
        fuelStationField.setItems(stations);
    }


    private void loadVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        vehicleComboBox.setItems(FXCollections.observableArrayList(vehicles));
        vehicleComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Vehicle vehicle) {
                return vehicle != null ? vehicle.getRegistrationNum() : "";
            }

            @Override
            public Vehicle fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    private void handleSaveButtonAction() {
        try {
            Vehicle selectedVehicle = vehicleComboBox.getSelectionModel().getSelectedItem();
            if (selectedVehicle == null) {
                showAlert("Validation Error", "Please select a vehicle.");
                return;
            }

            Fuel fuel = new Fuel();
            fuel.setVehicleId(selectedVehicle.getVehicleId());
            String selectedFuelType = fuelTypeField.getSelectionModel().getSelectedItem();
            if (selectedFuelType == null) {
                showAlert("Validation Error", "Please select a fuel type.");
                return;
            }
            fuel.setFuelType(selectedFuelType);
            fuel.setDate(datePicker.getValue());
            fuel.setPerMileage(Float.parseFloat(perMileageField.getText()));
            fuel.setPostMileage(Float.parseFloat(postMileageField.getText()));
            fuel.setQuantity(Float.parseFloat(quantityField.getText()));
            String pickupLocation = fuelStationField.getSelectionModel().getSelectedItem();
            if (pickupLocation == null) {
                showAlert("Validation Error", "Please select a fuel pick-up location.");
                return;
            }
            fuel.setFuelStation(pickupLocation);


            fuelService.saveFuel(fuel);
            showAlert("Success", "Fuel record saved successfully.");
            clearForm();

        } catch (Exception e) {
            showAlert("Error", "Failed to save fuel data: " + e.getMessage());
        }
    }

    private void clearForm() {
        vehicleComboBox.getSelectionModel().clearSelection();
        fuelTypeField.setItems(null);
        datePicker.setValue(null);
        perMileageField.clear();
        postMileageField.clear();
        quantityField.clear();
        fuelTypeField.getSelectionModel().clearSelection();  // ✅ keeps items

    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
