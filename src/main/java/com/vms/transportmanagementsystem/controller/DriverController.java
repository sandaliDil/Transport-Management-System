package com.vms.transportmanagementsystem.controller;

import com.vms.transportmanagementsystem.enitiy.Driver;
import com.vms.transportmanagementsystem.service.DriverService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DriverController {

    @FXML private TextField nameField;
    @FXML private TextField licenseField;
    @FXML private TextField contactField;
    @FXML private TextArea addressArea;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private TextField searchField;
    @FXML private TableView<Driver> driverTable;
    @FXML private TableColumn<Driver, Integer> idColumn;
    @FXML private TableColumn<Driver, String> nameColumn;
    @FXML private TableColumn<Driver, String> licenseColumn;
    @FXML private TableColumn<Driver, String> contactColumn;
    @FXML private TableColumn<Driver, String> addressColumn;
    @FXML private TableColumn<Driver, String> statusColumn;

    private ObservableList<Driver> masterDriverList;

    private final DriverService service = new DriverService();
    private Driver currentDriver;
    @FXML
    public void initialize() {
        statusComboBox.getItems().addAll("Active", "Inactive");

        // Set table columns
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getDriverId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        licenseColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getLicenseNum()));
        contactColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getContactNum()));
        addressColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAddress()));
        statusColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatus()));

        // Load data initially
        masterDriverList = FXCollections.observableArrayList(service.getAllDrivers());
        driverTable.setItems(masterDriverList);

        // Live filtering logic
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterDriverList(newValue);
        });
    }

    private void filterDriverList(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            driverTable.setItems(masterDriverList);
            return;
        }

        String lowerCaseKeyword = keyword.toLowerCase();
        ObservableList<Driver> filteredList = FXCollections.observableArrayList();

        for (Driver driver : masterDriverList) {
            if (
                    String.valueOf(driver.getDriverId()).contains(lowerCaseKeyword) ||
                            driver.getName().toLowerCase().contains(lowerCaseKeyword) ||
                            driver.getLicenseNum().toLowerCase().contains(lowerCaseKeyword) ||
                            driver.getContactNum().toLowerCase().contains(lowerCaseKeyword) ||
                            driver.getAddress().toLowerCase().contains(lowerCaseKeyword) ||
                            driver.getStatus().toLowerCase().contains(lowerCaseKeyword)
            ) {
                filteredList.add(driver);
            }
        }

        driverTable.setItems(filteredList);
    }

    private void refreshDriverList() {
        masterDriverList.setAll(service.getAllDrivers());
    }


    @FXML
    private void handleSaveDriver() {
        String name = nameField.getText();
        String licenseNum = licenseField.getText();
        String contactNum = contactField.getText();
        String address = addressArea.getText();
        String status = statusComboBox.getValue();

        if (name.isEmpty() && licenseNum.isEmpty() && contactNum.isEmpty() && address.isEmpty() && status.isEmpty()) {
            showAlert("Validation Error", "Please fill in all fields.");
            return;
        }

        Driver driver = new Driver(name, licenseNum, contactNum, address, status);
        System.out.println(driver);
        service.saveDriver(driver);
        showAlert("Success", "Driver saved successfully!");
        clearFields();
        refreshDriverList();
    }

    private void clearFields() {
        nameField.clear();
        licenseField.clear();
        contactField.clear();
        addressArea.clear();
        searchField.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().trim();
        if (!keyword.isEmpty()) {
            currentDriver = service.findByKeyword(keyword);
            if (currentDriver != null) {
                populateForm(currentDriver);
            } else {
                showAlert("No driver found with that keyword.");
                clearForm();
            }
        }
    }

    private void populateForm(Driver driver) {
        nameField.setText(driver.getName());
        licenseField.setText(driver.getLicenseNum());
        contactField.setText(driver.getContactNum());
        addressArea.setText(driver.getAddress());
        statusComboBox.setValue(driver.getStatus());
    }

    private void loadDriverTable() {
        driverTable.setItems(FXCollections.observableArrayList(service.getAllDrivers()));
    }

    @FXML
    private void handleUpdateDriver() {
        if (currentDriver != null) {
            currentDriver.setName(nameField.getText());
            currentDriver.setLicenseNum(licenseField.getText());
            currentDriver.setContactNum(contactField.getText());
            currentDriver.setAddress(addressArea.getText());
            currentDriver.setStatus(statusComboBox.getValue());

            service.updateDriver(currentDriver);
            showAlert("Driver updated successfully!");
        } else {
            showAlert("Please search and load a driver first.");
        }
    }

    @FXML
    private void clearForm() {
        nameField.clear();
        licenseField.clear();
        contactField.clear();
        addressArea.clear();
        statusComboBox.setValue(null);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
