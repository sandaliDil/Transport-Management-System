package com.vms.transportmanagementsystem.enitiy;

import java.time.LocalDate;

public class Vehicle {

    private int vehicleId;
    private String registrationNum;
    private String model;
    private String type;
    private String status;
    private LocalDate vehicleLicenseRenewalDate;
    private LocalDate vehicleEmissionTestingDate;
    private LocalDate vehicleInsuranceRenewalDate;
    private LocalDate vehicleServiceDate;

    public Vehicle(int vehicleId, String registrationNum) {
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId=" + vehicleId +
                ", registrationNum='" + registrationNum + '\'' +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", vehicleLicenseRenewalDate=" + vehicleLicenseRenewalDate +
                ", vehicleEmissionTestingDate=" + vehicleEmissionTestingDate +
                ", vehicleInsuranceRenewalDate=" + vehicleInsuranceRenewalDate +
                ", vehicleServiceDate=" + vehicleServiceDate +
                '}';
    }

    public Vehicle(int vehicleId, LocalDate vehicleServiceDate, LocalDate vehicleInsuranceRenewalDate, LocalDate
            vehicleEmissionTestingDate, LocalDate vehicleLicenseRenewalDate, String status, String type, String model, String registrationNum) {
        this.vehicleId = vehicleId;
        this.vehicleServiceDate = vehicleServiceDate;
        this.vehicleInsuranceRenewalDate = vehicleInsuranceRenewalDate;
        this.vehicleEmissionTestingDate = vehicleEmissionTestingDate;
        this.vehicleLicenseRenewalDate = vehicleLicenseRenewalDate;
        this.status = status;
        this.type = type;
        this.model = model;
        this.registrationNum = registrationNum;
    }

    public Vehicle() {
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getRegistrationNum() {
        return registrationNum;
    }

    public void setRegistrationNum(String registrationNum) {
        this.registrationNum = registrationNum;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getVehicleLicenseRenewalDate() {
        return vehicleLicenseRenewalDate;
    }

    public void setVehicleLicenseRenewalDate(LocalDate vehicleLicenseRenewalDate) {
        this.vehicleLicenseRenewalDate = vehicleLicenseRenewalDate;
    }

    public LocalDate getVehicleEmissionTestingDate() {
        return vehicleEmissionTestingDate;
    }

    public void setVehicleEmissionTestingDate(LocalDate vehicleEmissionTestingDate) {
        this.vehicleEmissionTestingDate = vehicleEmissionTestingDate;
    }

    public LocalDate getVehicleInsuranceRenewalDate() {
        return vehicleInsuranceRenewalDate;
    }

    public void setVehicleInsuranceRenewalDate(LocalDate vehicleInsuranceRenewalDate) {
        this.vehicleInsuranceRenewalDate = vehicleInsuranceRenewalDate;
    }

    public LocalDate getVehicleServiceDate() {
        return vehicleServiceDate;
    }

    public void setVehicleServiceDate(LocalDate vehicleServiceDate) {
        this.vehicleServiceDate = vehicleServiceDate;
    }
}
