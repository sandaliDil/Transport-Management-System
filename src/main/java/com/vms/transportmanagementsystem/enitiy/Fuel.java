package com.vms.transportmanagementsystem.enitiy;

import java.time.LocalDate;

public class Fuel {

    private int fuelId;
    private int vehicleId;
    private String fuelType;
    private LocalDate date;
    private float perMileage;
    private float postMileage;
    private float quantity;
    private Vehicle vehicle;

    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Fuel() {}

    public Fuel(int fuelId, int vehicleId, String fuelType, LocalDate date, float perMileage, float postMileage, float quantity) {
        this.fuelId = fuelId;
        this.vehicleId = vehicleId;
        this.fuelType = fuelType;
        this.date = date;
        this.perMileage = perMileage;
        this.postMileage = postMileage;
        this.quantity = quantity;
    }

    // Getters and Setters

    public int getFuelId() {
        return fuelId;
    }

    public void setFuelId(int fuelId) {
        this.fuelId = fuelId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getPerMileage() {
        return perMileage;
    }

    public void setPerMileage(float perMileage) {
        this.perMileage = perMileage;
    }

    public float getPostMileage() {
        return postMileage;
    }

    public void setPostMileage(float postMileage) {
        this.postMileage = postMileage;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }


}