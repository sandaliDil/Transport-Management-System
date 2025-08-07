package com.vms.transportmanagementsystem.enitiy;

public class Driver {
    private int driverId;
    private String name;
    private String licenseNum;
    private String contactNum;
    private String address;
    private String status;

    // Constructor, Getters, Setters


    public Driver(int driverId, String address, String contactNum, String licenseNum, String name, String status) {
        this.driverId = driverId;
        this.address = address;
        this.contactNum = contactNum;
        this.licenseNum = licenseNum;
        this.name = name;
        this.status = status;
    }

    public Driver() {

    }

    public Driver(String name, String licenseNum, String contactNum, String address, String status) {
        this.address = address;
        this.contactNum = contactNum;
        this.licenseNum = licenseNum;
        this.name = name;
        this.status = status;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getLicenseNum() {
        return licenseNum;
    }

    public void setLicenseNum(String licenseNum) {
        this.licenseNum = licenseNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }
}

