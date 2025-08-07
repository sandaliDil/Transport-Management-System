package com.vms.transportmanagementsystem.service;

import com.vms.transportmanagementsystem.enitiy.Vehicle;
import com.vms.transportmanagementsystem.repository.VehicleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class VehicleService {

    private final VehicleRepository repository = new VehicleRepository();

    public void saveVehicle(Vehicle vehicle) {
        repository.saveVehicle(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return repository.getAllVehicles();
    }

    public void updateVehicle(Vehicle vehicle) {
        repository.updateVehicle(vehicle);
    }

    public Vehicle findByKeyword(String keyword) {
        return repository.findByKeyword(keyword);
    }

    // General method to filter vehicles with a date due in 7 days
    private List<Vehicle> filterVehiclesDueSoon(List<Vehicle> allVehicles, LocalDateExtractor extractor) {
        LocalDate today = LocalDate.now();
        return allVehicles.stream()
                .filter(vehicle -> {
                    LocalDate date = extractor.getDate(vehicle);
                    return date != null && !date.isBefore(today) && !date.isAfter(today.plusDays(7));
                })
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehiclesWithEmissionTestDueSoon() {
        return filterVehiclesDueSoon(repository.getAllVehicles(), Vehicle::getVehicleEmissionTestingDate);
    }

    public List<Vehicle> getVehiclesWithLicenseRenewalDueSoon() {
        return filterVehiclesDueSoon(repository.getAllVehicles(), Vehicle::getVehicleLicenseRenewalDate);
    }

    public List<Vehicle> getVehiclesWithInsuranceRenewalDueSoon() {
        return filterVehiclesDueSoon(repository.getAllVehicles(), Vehicle::getVehicleInsuranceRenewalDate);
    }

    public List<Vehicle> getVehiclesWithServiceDueSoon() {
        return filterVehiclesDueSoon(repository.getAllVehicles(), Vehicle::getVehicleServiceDate);
    }

    // Functional interface to extract LocalDate from Vehicle
    @FunctionalInterface
    private interface LocalDateExtractor {
        LocalDate getDate(Vehicle vehicle);
    }


    public List<String> getAllVehicleNumbers() {
        return repository.getAllVehicleNumbers();
    }
}
