package com.vms.transportmanagementsystem.service;

import com.vms.transportmanagementsystem.enitiy.Driver;
import com.vms.transportmanagementsystem.repository.DriverRepository;

import java.util.List;

public class DriverService {
    private final DriverRepository repository = new DriverRepository();

    public void saveDriver(Driver driver) {
        repository.saveDriver(driver);
    }


    public List<Driver> getAllDrivers() {
        return repository.getAllDrivers();
    }

    public Driver findByKeyword(String keyword) {
        return repository.findByKeyword(keyword);
    }

    public boolean updateDriver(Driver driver) {
        repository.update(driver);
        return false;
    }

}
