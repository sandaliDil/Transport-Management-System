package com.vms.transportmanagementsystem.enitiy;

import java.time.LocalDate;
import java.util.Arrays;

public class Maintenance {
    private int maintenanceId;
    private int vehicleId;
    private String serviceType;
    private LocalDate date;
    private double cost;
    private byte[] image;
    private String description;
    private String status;
    private Vehicle vehicle;

    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "Maintenance{" +
                "maintenanceId=" + maintenanceId +
                ", vehicleId=" + vehicleId +
                ", serviceType='" + serviceType + '\'' +
                ", date=" + date +
                ", cost=" + cost +
                ", image=" + Arrays.toString(image) +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public Maintenance() {
    }

    public Maintenance(int maintenanceId, int vehicleId, String serviceType, LocalDate date, double cost, byte[] image,
                       String description, String status) {
        this.maintenanceId = maintenanceId;
        this.vehicleId = vehicleId;
        this.serviceType = serviceType;
        this.date = date;
        this.cost = cost;
        this.image = image;
        this.description = description;
        this.status = status;
    }

    public int getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(int maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
