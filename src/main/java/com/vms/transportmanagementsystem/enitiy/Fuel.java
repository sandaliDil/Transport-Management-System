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

    private String fuelStation;

    public String getFuelStation() {
        return fuelStation;
    }

    public void setFuelStation(String fuelStation) {
        this.fuelStation = fuelStation;
    }

    public Fuel() {}

    public Fuel(String fuelStation, float quantity, Vehicle vehicle, float postMileage, float perMileage,
                LocalDate date, String fuelType, int vehicleId, int fuelId) {
        this.fuelStation = fuelStation;
        this.quantity = quantity;
        this.vehicle = vehicle;
        this.postMileage = postMileage;
        this.perMileage = perMileage;
        this.date = date;
        this.fuelType = fuelType;
        this.vehicleId = vehicleId;
        this.fuelId = fuelId;
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