package com.vms.transportmanagementsystem.controller;

import com.vms.transportmanagementsystem.enitiy.Message;
import com.vms.transportmanagementsystem.enitiy.Vehicle;
import com.vms.transportmanagementsystem.service.VehicleService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MessageCenterController {

    @FXML
    private TableView<Message> messageTable;

    @FXML
    private TableColumn<Message, String> titleColumn;

    @FXML
    private TableColumn<Message, String> contentColumn;

    @FXML
    private TableColumn<Message, LocalDate> dateColumn;

    private final VehicleService vehicleService = new VehicleService();

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadMessages();

        // Show popup on row click
        messageTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showMessagePopup(newSelection);
            }
        });

        // Custom cell factory for coloring each cell if date is in the past
        titleColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String title, boolean empty) {
                super.updateItem(title, empty);
                if (empty || getTableRow().getItem() == null) {
                    setText(null);
                    setStyle("");
                } else {
                    Message message = getTableRow().getItem();
                    setText(title);
                    if (message.getDate().isBefore(LocalDate.now())) {
                        setStyle("-fx-text-fill: red;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        contentColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String content, boolean empty) {
                super.updateItem(content, empty);
                if (empty || getTableRow().getItem() == null) {
                    setText(null);
                    setStyle("");
                } else {
                    Message message = getTableRow().getItem();
                    setText(content);
                    if (message.getDate().isBefore(LocalDate.now())) {
                        setStyle("-fx-text-fill: red;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        dateColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || getTableRow().getItem() == null) {
                    setText(null);
                    setStyle("");
                } else {
                    Message message = getTableRow().getItem();
                    setText(date.toString());
                    if (message.getDate().isBefore(LocalDate.now())) {
                        setStyle("-fx-text-fill: red;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });
    }


    private void showMessagePopup(Message message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(message.getTitle());
        alert.setHeaderText(null);
        alert.setContentText(message.getContent() + "\n\nDate: " + message.getDate());
        alert.showAndWait();

        // Clear selection after alert closes — delay with Platform.runLater to avoid IndexOutOfBoundsException
        javafx.application.Platform.runLater(() -> messageTable.getSelectionModel().clearSelection());
    }



    private void loadMessages() {
        List<Message> messageList = new ArrayList<>();
        List<Vehicle> allVehicles = vehicleService.getAllVehicles();

        LocalDate today = LocalDate.now();
        LocalDate oneWeekFromNow = today.plusDays(7);

        for (Vehicle v : allVehicles) {
            // Emission Test
            if (v.getVehicleEmissionTestingDate() != null &&
                    (v.getVehicleEmissionTestingDate().isBefore(today) ||
                            !v.getVehicleEmissionTestingDate().isAfter(oneWeekFromNow))) {
                messageList.add(new Message(
                        "Emission Test Reminder",
                        "Emission test for vehicle " + v.getRegistrationNum() +
                                " is due on " + v.getVehicleEmissionTestingDate() + ".",
                        v.getVehicleEmissionTestingDate()
                ));
            }

            // License Renewal
            if (v.getVehicleLicenseRenewalDate() != null &&
                    (v.getVehicleLicenseRenewalDate().isBefore(today) ||
                            !v.getVehicleLicenseRenewalDate().isAfter(oneWeekFromNow))) {
                messageList.add(new Message(
                        "License Renewal Reminder",
                        "License for vehicle " + v.getRegistrationNum() +
                                " needs renewal by " + v.getVehicleLicenseRenewalDate() + ".",
                        v.getVehicleLicenseRenewalDate()
                ));
            }

            // Insurance Renewal
            if (v.getVehicleInsuranceRenewalDate() != null &&
                    (v.getVehicleInsuranceRenewalDate().isBefore(today) ||
                            !v.getVehicleInsuranceRenewalDate().isAfter(oneWeekFromNow))) {
                messageList.add(new Message(
                        "Insurance Renewal Reminder",
                        "Insurance for vehicle " + v.getRegistrationNum() +
                                " is due on " + v.getVehicleInsuranceRenewalDate() + ".",
                        v.getVehicleInsuranceRenewalDate()
                ));
            }

            // Service Date
            if (v.getVehicleServiceDate() != null &&
                    (v.getVehicleServiceDate().isBefore(today) ||
                            !v.getVehicleServiceDate().isAfter(oneWeekFromNow))) {
                messageList.add(new Message(
                        "Vehicle Service Reminder",
                        "Service for vehicle " + v.getRegistrationNum() +
                                " is scheduled on " + v.getVehicleServiceDate() + ".",
                        v.getVehicleServiceDate()
                ));
            }
        }

        ObservableList<Message> observableList = FXCollections.observableArrayList(messageList);
        messageTable.setItems(observableList);
    }


}
