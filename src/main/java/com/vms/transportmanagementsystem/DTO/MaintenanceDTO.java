package com.vms.transportmanagementsystem.DTO;

import java.time.LocalDate;

public class MaintenanceDTO {
    private int maintenanceId;
    private String registrationNum; // from Vehicle table
    private String serviceType;
    private LocalDate date;
    private double cost;
    private String image;
    private String description;
    private String status;

    // Constructor
    public MaintenanceDTO(int maintenanceId, String registrationNum, String serviceType, LocalDate date, double cost, String image, String description, String status) {
        this.maintenanceId = maintenanceId;
        this.registrationNum = registrationNum;
        this.serviceType = serviceType;
        this.date = date;
        this.cost = cost;
        this.image = image;
        this.description = description;
        this.status = status;
    }

    // Getters and Setters
    public int getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(int maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public String getRegistrationNum() {
        return registrationNum;
    }

    public void setRegistrationNum(String registrationNum) {
        this.registrationNum = registrationNum;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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
