package com.vms.transportmanagementsystem.controller;

import com.vms.transportmanagementsystem.DTO.FuelPivotRow;
import com.vms.transportmanagementsystem.service.FuelService;
import com.vms.transportmanagementsystem.utill.ExcelExportUtil;
import com.vms.transportmanagementsystem.utill.PDFExportUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FuelReportController {
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private TableView<FuelPivotRow> pivotTable;
    @FXML private TableColumn<FuelPivotRow, String> vehicleCol;
    @FXML private ComboBox<String> fuelStationComboBox;
    @FXML private javafx.scene.control.ComboBox<String> vehicleComboBox;
    // Add "Total" column with custom cell factory
    TableColumn<FuelPivotRow, Float> totalCol = new TableColumn<>("Total");


    //    private List<LocalDate> dateList;
    private final FuelService fuelService = new FuelService();
    private List<LocalDate> dateList = new ArrayList<>();

    @FXML
    public void initialize() {
        vehicleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVehicleRegNum()));
        vehicleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVehicleRegNum()));

        // Load distinct fuel stations into ComboBox
        List<String> stations = fuelService.getDistinctFuelStations();
        fuelStationComboBox.getItems().add("All"); // Optional: show all fuel stations
        fuelStationComboBox.getItems().addAll(stations);
        fuelStationComboBox.getSelectionModel().selectFirst(); // Select "All" by default

        // Load vehicles
        List<String> vehicles = fuelService.getDistinctVehicleNumbers();
        vehicleComboBox.getItems().add("All");
        vehicleComboBox.getItems().addAll(vehicles);
        vehicleComboBox.getSelectionModel().selectFirst();

    }

    @FXML
    private void onGeneratePivotReport() {
        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();

        if (start == null || end == null || end.isBefore(start)) {
            showAlert("Select a valid start and end date.");
            return;
        }

        dateList = start.datesUntil(end.plusDays(1)).toList();
        pivotTable.getColumns().clear();
        pivotTable.getColumns().add(vehicleCol);

        String selectedStation = fuelStationComboBox.getValue();
        if ("All".equals(selectedStation)) {
            selectedStation = null;
        }

        String selectedVehicle = vehicleComboBox.getValue();
        if ("All".equals(selectedVehicle)) {
            selectedVehicle = null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");

        List<FuelPivotRow> rows = fuelService.getPivotReport(start, end, dateList, selectedStation, selectedVehicle);

        // Cell factory for formatting floats with optional decimals
        StringConverter<Float> floatStringConverter = new StringConverter<Float>() {
            @Override
            public String toString(Float value) {
                if (value == null) return "";
                if (value == Math.floor(value)) {
                    return String.format("%.0f", value);
                } else {
                    return String.format("%.3f", value)
                            .replaceAll("0*$", "")  // Remove trailing zeros
                            .replaceAll("\\.$", ""); // Remove trailing decimal point if any
                }
            }

            @Override
            public Float fromString(String string) {
                try {
                    return Float.parseFloat(string);
                } catch (NumberFormatException e) {
                    return 0f;
                }
            }
        };

        // Add date columns with formatted header and custom cell factory
        for (LocalDate date : dateList) {
            TableColumn<FuelPivotRow, Float> dateCol = new TableColumn<>(date.format(formatter));

            dateCol.setCellValueFactory(data -> data.getValue().quantityProperty(date).asObject());

            dateCol.setCellFactory(column -> new TextFieldTableCell<>(floatStringConverter));

            pivotTable.getColumns().add(dateCol);
        }

        // Add "Total" column with custom cell factory
        TableColumn<FuelPivotRow, Float> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(data -> data.getValue().totalQuantityProperty().asObject());
        totalCol.setCellFactory(column -> new TextFieldTableCell<>(floatStringConverter));
        pivotTable.getColumns().add(totalCol);

        // Calculate per-row totals (rounded to 3 decimals)
        for (FuelPivotRow row : rows) {
            float rowTotal = 0f;
            for (LocalDate date : dateList) {
                rowTotal += row.getQuantityForDate(date);
            }
            rowTotal = Math.round(rowTotal * 1000f) / 1000f;
            row.setTotalQuantity(rowTotal);
        }

        // Calculate per-column totals and grand total (rounded to 3 decimals)
        FuelPivotRow totalRow = new FuelPivotRow();
        totalRow.setVehicleRegNum("TOTAL");

        for (LocalDate date : dateList) {
            float columnTotal = 0f;
            for (FuelPivotRow row : rows) {
                columnTotal += row.getQuantityForDate(date);
            }
            columnTotal = Math.round(columnTotal * 1000f) / 1000f;
            totalRow.setQuantityForDate(date, columnTotal);
        }

        float grandTotal = rows.stream()
                .map(FuelPivotRow::getTotalQuantity)
                .reduce(0f, Float::sum);

        grandTotal = Math.round(grandTotal * 1000f) / 1000f;
        totalRow.setTotalQuantity(grandTotal);

        rows.add(totalRow);

        // Set items to the table
        pivotTable.setItems(FXCollections.observableArrayList(rows));

        pivotTable.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(FuelPivotRow item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else if ("TOTAL".equalsIgnoreCase(item.getVehicleRegNum())) {
                    setStyle("-fx-background-color: #fce4ec; -fx-font-weight: bold;"); // Light pink row
                } else {
                    setStyle("");
                }
            }
        });
        totalCol.setCellFactory(column -> {
            return new TextFieldTableCell<>(floatStringConverter) {
                @Override
                public void updateItem(Float item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        setStyle("-fx-background-color: #e0f7fa; -fx-font-weight: bold;"); // Light cyan
                    } else {
                        setStyle("");
                    }
                }
            };
        });

        vehicleCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setText(item);
                    setStyle("-fx-alignment: CENTER; -fx-border-color: lightgray; -fx-border-width: 0 0 1 0;");
                } else {
                    setText(null);
                    setStyle("");
                }
            }
        });


    }


    @FXML
    private void onExportToPDF() {
        if (pivotTable.getItems().isEmpty()) {
            showAlert("No data to export.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF Report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(pivotTable.getScene().getWindow());

        if (file != null) {
            PDFExportUtil.exportPivotToPDF(pivotTable.getItems(), dateList, file.getAbsolutePath());
            showAlert("PDF file saved successfully.");
        }
    }

    @FXML
    private void onExportToExcel() {
        if (pivotTable.getItems().isEmpty()) {
            showAlert("No data to export.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel Report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showSaveDialog(pivotTable.getScene().getWindow());

        if (file != null) {
            ExcelExportUtil.exportPivotToExcel(pivotTable.getItems(), dateList, file.getAbsolutePath());
            showAlert("Excel file saved successfully.");
        }
    }


    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(msg);
        alert.show();
    }

}
