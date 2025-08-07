package com.vms.transportmanagementsystem.repository;

import com.vms.transportmanagementsystem.database.DatabaseConnection;
import com.vms.transportmanagementsystem.enitiy.Vehicle;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VehicleRepository {

    public void saveVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicle (registration_num, model, type, status, " +
                "vehicle_license_renewal_date, vehicle_emission_testing_date, " +
                "vehicle_insurance_renewal_date, vehicle_service_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vehicle.getRegistrationNum());
            stmt.setString(2, vehicle.getModel());
            stmt.setString(3, vehicle.getType());
            stmt.setString(4, vehicle.getStatus());
            stmt.setDate(5, Date.valueOf(vehicle.getVehicleLicenseRenewalDate()));
            stmt.setDate(6, Date.valueOf(vehicle.getVehicleEmissionTestingDate()));
            stmt.setDate(7, Date.valueOf(vehicle.getVehicleInsuranceRenewalDate()));
            stmt.setDate(8, Date.valueOf(vehicle.getVehicleServiceDate()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

public List<Vehicle> getAllVehicles() {
    List<Vehicle> vehicles = new ArrayList<>();
    String sql = "SELECT * FROM vehicle";

    try (Connection conn = DatabaseConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleId(rs.getInt("vehicle_id"));
            vehicle.setRegistrationNum(rs.getString("registration_num"));
            vehicle.setModel(rs.getString("model"));
            vehicle.setType(rs.getString("type"));
            vehicle.setStatus(rs.getString("status"));

            vehicle.setVehicleLicenseRenewalDate(toLocalDateSafe(rs.getDate("vehicle_license_renewal_date")));
            vehicle.setVehicleEmissionTestingDate(toLocalDateSafe(rs.getDate("vehicle_emission_testing_date")));
            vehicle.setVehicleInsuranceRenewalDate(toLocalDateSafe(rs.getDate("vehicle_insurance_renewal_date")));
            vehicle.setVehicleServiceDate(toLocalDateSafe(rs.getDate("vehicle_service_date")));

            vehicles.add(vehicle);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return vehicles;
}

    private LocalDate toLocalDateSafe(Date date) {
        return (date != null) ? date.toLocalDate() : null;
    }


    public void updateVehicle(Vehicle vehicle) {
        String sql = "UPDATE vehicle SET registration_num=?, model=?, type=?, status=?, " +
                "vehicle_license_renewal_date=?, vehicle_emission_testing_date=?, " +
                "vehicle_insurance_renewal_date=?, vehicle_service_date=? WHERE vehicle_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vehicle.getRegistrationNum());
            stmt.setString(2, vehicle.getModel());
            stmt.setString(3, vehicle.getType());
            stmt.setString(4, vehicle.getStatus());
            stmt.setDate(5, Date.valueOf(vehicle.getVehicleLicenseRenewalDate()));
            stmt.setDate(6, Date.valueOf(vehicle.getVehicleEmissionTestingDate()));
            stmt.setDate(7, Date.valueOf(vehicle.getVehicleInsuranceRenewalDate()));
            stmt.setDate(8, Date.valueOf(vehicle.getVehicleServiceDate()));
            stmt.setInt(9, vehicle.getVehicleId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vehicle findByKeyword(String keyword) {
        String sql = "SELECT * FROM vehicle WHERE vehicle_id=? OR registration_num=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, keyword);
            stmt.setString(2, keyword);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Vehicle(
                        rs.getInt("vehicle_id"),
                        rs.getDate("vehicle_service_date").toLocalDate(),
                        rs.getDate("vehicle_insurance_renewal_date").toLocalDate(),
                        rs.getDate("vehicle_emission_testing_date").toLocalDate(),
                        rs.getDate("vehicle_license_renewal_date").toLocalDate(),
                        rs.getString("status"),
                        rs.getString("type"),
                        rs.getString("model"),
                        rs.getString("registration_num")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getAllVehicleNumbers() {
        List<String> vehicleNums = new ArrayList<>();
        String sql = "SELECT registration_num FROM vehicle";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                vehicleNums.add(rs.getString("registration_num"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicleNums;
    }



}
