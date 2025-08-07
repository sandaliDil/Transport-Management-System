package com.vms.transportmanagementsystem.repository;

import com.vms.transportmanagementsystem.database.DatabaseConnection;
import com.vms.transportmanagementsystem.enitiy.Fuel;
import com.vms.transportmanagementsystem.enitiy.Vehicle;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class FuelRepository {


    public void saveFuel(Fuel fuel) {
        String sql = "INSERT INTO fuel (vehicle_id, fuel_type, date, per_mileage, post_mileage, quantity) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, fuel.getVehicleId());
            stmt.setString(2, fuel.getFuelType());
            stmt.setDate(3, Date.valueOf(fuel.getDate()));
            stmt.setFloat(4, fuel.getPerMileage());
            stmt.setFloat(5, fuel.getPostMileage());
            stmt.setFloat(6, fuel.getQuantity());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Fuel> getAllFuels() {
        List<Fuel> list = new ArrayList<>();
        String sql = """
            SELECT f.fuel_id, v.registration_num, f.fuel_type, f.date, f.per_mileage, f.post_mileage, f.quantity
            FROM fuel f
            JOIN vehicle v ON f.vehicle_id = v.vehicle_id
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Fuel fuel = new Fuel();

                Vehicle vehicle = new Vehicle();
                vehicle.setRegistrationNum(rs.getString("registration_num"));
                fuel.setVehicleId(vehicle.getVehicleId());

                fuel.setFuelId(rs.getInt("fuel_id"));
                fuel.setFuelType(rs.getString("fuel_type"));
                fuel.setDate(rs.getDate("date").toLocalDate());
                fuel.setPerMileage( rs.getFloat("per_mileage"));
                fuel.setPostMileage( rs.getFloat("post_mileage"));
                fuel.setQuantity( rs.getFloat("quantity"));

                fuel.setVehicle(vehicle); // ✅ Important!
                list.add(fuel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public Map<String, Map<LocalDate, Float>> getFuelQuantityGroupedByVehicleAndDate(LocalDate start, LocalDate end) {
        Map<String, Map<LocalDate, Float>> result = new HashMap<>();

        String query = "SELECT v.registration_num, f.date, SUM(f.quantity) as total_quantity " +
                "FROM fuel f JOIN vehicle v ON f.vehicle_id = v.vehicle_id " +
                "WHERE f.date BETWEEN ? AND ? " +
                "GROUP BY v.registration_num, f.date";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, Date.valueOf(start));
            stmt.setDate(2, Date.valueOf(end));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String regNum = rs.getString("registration_num");
                LocalDate date = rs.getDate("date").toLocalDate();
                float qty = roundToThreeDecimals(rs.getFloat("total_quantity"));

                result.computeIfAbsent(regNum, k -> new LinkedHashMap<>()).put(date, qty);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    private float roundToThreeDecimals(float value) {
        return (float) (Math.round(value * 1000.0) / 1000.0);
    }

}
