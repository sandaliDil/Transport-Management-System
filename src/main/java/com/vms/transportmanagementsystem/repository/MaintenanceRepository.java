package com.vms.transportmanagementsystem.repository;

import com.vms.transportmanagementsystem.database.DatabaseConnection;
import com.vms.transportmanagementsystem.enitiy.Maintenance;
import com.vms.transportmanagementsystem.enitiy.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceRepository {

    public boolean save(Maintenance maintenance) {
        String sql = "INSERT INTO maintenance (vehicle_id, service_type, date, cost, image, description, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maintenance.getVehicleId());
            stmt.setString(2, maintenance.getServiceType());
            stmt.setDate(3, Date.valueOf(maintenance.getDate()));
            stmt.setDouble(4, maintenance.getCost());
            stmt.setBytes(5, maintenance.getImage());
            stmt.setString(6, maintenance.getDescription());
            stmt.setString(7, maintenance.getStatus());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Maintenance> getAllMaintenance() {
        List<Maintenance> list = new ArrayList<>();
        String query = """
        SELECT m.*, v.registration_num 
        FROM maintenance m
        JOIN vehicle v ON m.vehicle_id = v.vehicle_id
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleId(rs.getInt("vehicle_id"));
                vehicle.setRegistrationNum(rs.getString("registration_num"));

                Maintenance maintenance = new Maintenance();
                maintenance.setMaintenanceId(rs.getInt("maintenance_id"));
                maintenance.setVehicle(vehicle);
                maintenance.setServiceType(rs.getString("service_type"));
                maintenance.setDate(rs.getDate("date").toLocalDate());
                maintenance.setCost(rs.getDouble("cost"));

                maintenance.setDescription(rs.getString("description"));
                maintenance.setStatus(rs.getString("status"));

                list.add(maintenance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }



}
