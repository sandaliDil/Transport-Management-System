package com.vms.transportmanagementsystem.enitiy;

import java.util.Date;

public class RootRecord {

    private int recordId;
    private int rootId;
    private int vehicleId;
    private int driverId;
    private Date date;
    private int startMileage;
    private int endMileage;

    public RootRecord() {
    }

    @Override
    public String toString() {
        return "RootRecord{" +
                "recordId=" + recordId +
                ", rootId=" + rootId +
                ", vehicleId=" + vehicleId +
                ", driverId=" + driverId +
                ", date=" + date +
                ", startMileage=" + startMileage +
                ", endMileage=" + endMileage +
                '}';
    }

    public RootRecord(int recordId, int endMileage, int startMileage, Date date, int driverId, int vehicleId, int rootId) {
        this.recordId = recordId;
        this.endMileage = endMileage;
        this.startMileage = startMileage;
        this.date = date;
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.rootId = rootId;
    }

    public int getRootId() {
        return rootId;
    }

    public void setRootId(int rootId) {
        this.rootId = rootId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStartMileage() {
        return startMileage;
    }

    public void setStartMileage(int startMileage) {
        this.startMileage = startMileage;
    }

    public int getEndMileage() {
        return endMileage;
    }

    public void setEndMileage(int endMileage) {
        this.endMileage = endMileage;
    }
}
