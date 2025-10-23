package com.vms.transportmanagementsystem.DTO;

public class FuelEfficiencyDTO {
    private String vehicleNumber;
    private double totalDistance;
    private double totalQuantity;
    private double efficiency; // km per liter

    public FuelEfficiencyDTO(String vehicleNumber, double totalDistance, double totalQuantity) {
        this.vehicleNumber = vehicleNumber;
        this.totalDistance = totalDistance;
        this.totalQuantity = totalQuantity;
        this.efficiency = totalQuantity != 0 ? totalDistance / totalQuantity : 0;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(double efficiency) {
        this.efficiency = efficiency;
    }


}
