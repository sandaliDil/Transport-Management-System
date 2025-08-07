package com.vms.transportmanagementsystem.service;

import com.vms.transportmanagementsystem.DTO.FuelPivotRow;
import com.vms.transportmanagementsystem.enitiy.Fuel;
import com.vms.transportmanagementsystem.repository.FuelRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FuelService {
    private FuelRepository fuelRepository = new FuelRepository();

    public void saveFuel(Fuel fuel) {
        fuelRepository.saveFuel(fuel);
    }

    public List<Fuel> getAllFuel() {
        return fuelRepository.getAllFuels();
    }

    public List<FuelPivotRow> getPivotReport(LocalDate start, LocalDate end, List<LocalDate> dateList) {
        Map<String, Map<LocalDate, Float>> rawData = fuelRepository.getFuelQuantityGroupedByVehicleAndDate(start, end);
        List<FuelPivotRow> rows = new ArrayList<>();

        for (Map.Entry<String, Map<LocalDate, Float>> entry : rawData.entrySet()) {
            FuelPivotRow row = new FuelPivotRow(entry.getKey());
            for (LocalDate date : dateList) {
                float qty = entry.getValue().getOrDefault(date, 0f);
                row.setQuantityForDate(date, qty);
            }
            rows.add(row);
        }

        return rows;
    }

}
