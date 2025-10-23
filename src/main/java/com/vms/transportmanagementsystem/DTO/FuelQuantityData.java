package com.vms.transportmanagementsystem.DTO;

public class FuelQuantityData {
    private float quantity;
    private String fuelStation;

    public FuelQuantityData(float quantity, String fuelStation) {
        this.quantity = quantity;
        this.fuelStation = fuelStation;
    }

    public float getQuantity() {
        return quantity;
    }

    public String getFuelStation() {
        return fuelStation;
    }
}
