package com.vms.transportmanagementsystem.service;

import com.vms.transportmanagementsystem.DTO.FuelEfficiencyDTO;
import com.vms.transportmanagementsystem.DTO.FuelPivotRow;
import com.vms.transportmanagementsystem.enitiy.Fuel;
import com.vms.transportmanagementsystem.repository.FuelRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FuelService {
    private FuelRepository fuelRepository = new FuelRepository();

    public void saveFuel(Fuel fuel) {
        fuelRepository.saveFuel(fuel);
    }

    public List<Fuel> getAllFuel() {
        return fuelRepository.getAllFuels();
    }

//    public List<FuelPivotRow> getPivotReport(LocalDate start, LocalDate end, List<LocalDate> dateList) {
//        Map<String, Map<LocalDate, Float>> rawData = fuelRepository.getFuelQuantityGroupedByVehicleAndDate(start, end);
//        List<FuelPivotRow> rows = new ArrayList<>();
//
//        for (Map.Entry<String, Map<LocalDate, Float>> entry : rawData.entrySet()) {
//            FuelPivotRow row = new FuelPivotRow(entry.getKey());
//            for (LocalDate date : dateList) {
//                float qty = entry.getValue().getOrDefault(date, 0f);
//                row.setQuantityForDate(date, qty);
//            }
//            rows.add(row);
//        }
//
//        return rows;
//    }

    public List<FuelPivotRow> getPivotReport(LocalDate start, LocalDate end, List<LocalDate> dates, String fuelStation, String vehicleRegNum) {
        Map<String, Map<LocalDate, Float>> data = fuelRepository.getFuelQuantityGroupedByVehicleAndDate(start, end, fuelStation, vehicleRegNum);

        List<FuelPivotRow> result = new ArrayList<>();
        for (Map.Entry<String, Map<LocalDate, Float>> entry : data.entrySet()) {
            FuelPivotRow row = new FuelPivotRow();
            row.setVehicleRegNum(entry.getKey());
            for (LocalDate date : dates) {
                Float value = entry.getValue().getOrDefault(date, 0f);
                row.setQuantityForDate(date, value);
            }
            result.add(row);
        }
        return result;
    }

    public List<String> getDistinctVehicleNumbers() {
        return fuelRepository.getDistinctVehicleNumbers();
    }

    public List<String> getDistinctFuelStations() {
        return fuelRepository.getDistinctFuelStations();
    }
    public List<FuelEfficiencyDTO> calculateMonthlyFuelEfficiency(LocalDate startDate, LocalDate endDate) {
        List<Fuel> allFuelRecords = fuelRepository.findByDateBetween(startDate, endDate);

        Map<Integer, List<Fuel>> groupedByVehicle = allFuelRecords.stream()
                .collect(Collectors.groupingBy(Fuel::getVehicleId));

        List<FuelEfficiencyDTO> efficiencyList = new ArrayList<>();

        for (Map.Entry<Integer, List<Fuel>> entry : groupedByVehicle.entrySet()) {
            Integer vehicleId = entry.getKey();
            List<Fuel> fuelList = entry.getValue();

            double totalDistance = 0;
            double totalQuantity = 0;

            for (Fuel fuel : fuelList) {
                if (fuel.getPostMileage() > fuel.getPerMileage()) {
                    totalDistance += (fuel.getPostMileage() - fuel.getPerMileage());
                }
                totalQuantity += fuel.getQuantity();
            }

            FuelEfficiencyDTO dto = new FuelEfficiencyDTO(String.valueOf(vehicleId), totalDistance, totalQuantity);
            efficiencyList.add(dto);
        }

        return efficiencyList;
    }



}
