package com.vms.transportmanagementsystem;

import com.vms.transportmanagementsystem.enitiy.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloController {
    private final ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList();

    @FXML
    public void initialize(){


    }

    public void openVehicleTab(ActionEvent event) {
        try {
            // Load the TotalSummary.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource
                    ("/com/vms/transportmanagementsystem/vehicle.fxml"));

            Parent root = loader.load();
            // Create a new stage (window) to show the TotalSummary page
            Stage stage = new Stage();
            stage.setTitle("Message Center!");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openDriverTab(ActionEvent event) {
        try {
            // Load the TotalSummary.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource
                    ("/com/vms/transportmanagementsystem/driver-view.fxml"));

            Parent root = loader.load();
            // Create a new stage (window) to show the TotalSummary page
            Stage stage = new Stage();
            stage.setTitle("Message Center!");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void openMessageTab(ActionEvent event) {
        try {
            // Load the TotalSummary.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource
                    ("/com/vms/transportmanagementsystem/message_center.fxml"));

            Parent root = loader.load();
            // Create a new stage (window) to show the TotalSummary page
            Stage stage = new Stage();
            stage.setTitle("Message Center!");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openMaintenanceTab(ActionEvent event) {
        try {
            // Load the TotalSummary.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource
                    ("/com/vms/transportmanagementsystem/maintenanceForm.fxml"));

            Parent root = loader.load();
            // Create a new stage (window) to show the TotalSummary page
            Stage stage = new Stage();
            stage.setTitle("Message Center!");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openFuelTab(ActionEvent event) {
        try {
            // Load the TotalSummary.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource
                    ("/com/vms/transportmanagementsystem/fuel_form.fxml"));

            Parent root = loader.load();
            // Create a new stage (window) to show the TotalSummary page
            Stage stage = new Stage();
            stage.setTitle("Message Center!");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openFuelViewTab(ActionEvent event) {
        try {
            // Load the TotalSummary.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource
                    ("/com/vms/transportmanagementsystem/fuel_view.fxml"));

            Parent root = loader.load();
            // Create a new stage (window) to show the TotalSummary page
            Stage stage = new Stage();
            stage.setTitle("fuel view");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleFuelReport(ActionEvent event) {
        try {
            // Load the TotalSummary.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource
                    ("/com/vms/transportmanagementsystem/fuel_report.fxml"));

            Parent root = loader.load();
            // Create a new stage (window) to show the TotalSummary page
            Stage stage = new Stage();
            stage.setTitle("fuel view");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}