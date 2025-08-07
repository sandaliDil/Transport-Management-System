package com.vms.transportmanagementsystem.service;

import com.vms.transportmanagementsystem.enitiy.Maintenance;
import com.vms.transportmanagementsystem.repository.MaintenanceRepository;

import java.util.List;

public class MaintenanceService {
    private final MaintenanceRepository maintenanceRepository = new MaintenanceRepository();

    public boolean saveMaintenance(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    public List<Maintenance> getAllMaintenance() {
        return maintenanceRepository.getAllMaintenance();
    }
}
