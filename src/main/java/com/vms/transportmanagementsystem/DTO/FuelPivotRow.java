package com.vms.transportmanagementsystem.DTO;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class FuelPivotRow {
    private final SimpleStringProperty vehicleRegNum = new SimpleStringProperty();
    private final Map<LocalDate, SimpleFloatProperty> dateToQuantity = new HashMap<>();
    private final FloatProperty totalQuantity = new SimpleFloatProperty(0f);

    // Constructor
    public FuelPivotRow(String regNum) {
        this.vehicleRegNum.set(regNum);
    }

    // Default constructor (for total row)
    public FuelPivotRow() {
        this.vehicleRegNum.set("");
    }

    // Property getters and setters

    public String getVehicleRegNum() {
        return vehicleRegNum.get();
    }

    public void setVehicleRegNum(String regNum) {
        this.vehicleRegNum.set(regNum);
    }

    public SimpleStringProperty vehicleRegNumProperty() {
        return vehicleRegNum;
    }

    public void setQuantityForDate(LocalDate date, float qty) {
        dateToQuantity.put(date, new SimpleFloatProperty(qty));
    }

    public float getQuantityForDate(LocalDate date) {
        return dateToQuantity.getOrDefault(date, new SimpleFloatProperty(0)).get();
    }

    public SimpleFloatProperty quantityProperty(LocalDate date) {
        return dateToQuantity.getOrDefault(date, new SimpleFloatProperty(0));
    }

    public FloatProperty totalQuantityProperty() {
        return totalQuantity;
    }

    public void setTotalQuantity(float total) {
        totalQuantity.set(total);
    }

    public float getTotalQuantity() {
        return totalQuantity.get();
    }


}
