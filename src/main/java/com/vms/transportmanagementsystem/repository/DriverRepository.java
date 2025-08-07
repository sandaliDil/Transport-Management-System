package com.vms.transportmanagementsystem.repository;

import com.vms.transportmanagementsystem.database.DatabaseConnection;
import com.vms.transportmanagementsystem.enitiy.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverRepository {


    public void saveDriver(Driver driver) {
        String sql = "INSERT INTO driver (name, license_num, contact_num, address, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, driver.getName());
            stmt.setString(2, driver.getLicenseNum());
            stmt.setString(3, driver.getContactNum());
            stmt.setString(4, driver.getAddress());
            stmt.setString(5, driver.getStatus());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Driver findByKeyword(String keyword) {
        String sql = "SELECT * FROM driver WHERE name LIKE ? OR license_num LIKE ? OR driver_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");

            // Try to parse ID if possible
            try {
                int id = Integer.parseInt(keyword);
                stmt.setInt(3, id);
            } catch (NumberFormatException e) {
                stmt.setInt(3, -1); // Dummy value
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Driver(
                        rs.getInt("driver_id"),
                        rs.getString("name"),
                        rs.getString("license_num"),
                        rs.getString("contact_num"),
                        rs.getString("address"),
                        rs.getString("status")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Driver driver) {
        String sql = "UPDATE driver SET name=?, license_num=?, contact_num=?, address=?, status=? WHERE driver_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, driver.getName());
            stmt.setString(2, driver.getLicenseNum());
            stmt.setString(3, driver.getContactNum());
            stmt.setString(4, driver.getAddress());
            stmt.setString(5, driver.getStatus());
            stmt.setInt(6, driver.getDriverId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT * FROM driver";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Driver driver = new Driver(
                        rs.getInt("driver_id"),
                        rs.getString("address"),
                        rs.getString("contact_num"),
                        rs.getString("license_num"),
                        rs.getString("name"),
                        rs.getString("status")
                );
                drivers.add(driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return drivers;
    }

}
