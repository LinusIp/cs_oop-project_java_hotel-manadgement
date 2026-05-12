package org.example.hotel;

import org.example.registration.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceChargeDatabase {
    
    // Get all room charges from SQLite database
    public static List<RoomChargeRecord> getAllCharges() throws SQLException {
        String sql = "SELECT id, charge_id, room_number, booking_id, name, description, amount, status " +
                     "FROM room_charges ORDER BY id DESC";
        List<RoomChargeRecord> charges = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                RoomChargeRecord charge = new RoomChargeRecord(
                    resultSet.getInt("id"),
                    resultSet.getString("charge_id"),
                    resultSet.getString("room_number"),
                    resultSet.getString("booking_id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getDouble("amount"),
                    resultSet.getString("status")
                );
                charges.add(charge);
            }
        }

        return charges;
    }

    // Add a room charge to SQLite database
    public static void addRoomCharge(RoomCharge charge) throws SQLException {
        String sql = "INSERT INTO room_charges (charge_id, room_number, booking_id, name, description, amount, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, charge.getChargeId());
            statement.setString(2, charge.getRoomNumber());
            statement.setString(3, charge.getBookingId());
            statement.setString(4, charge.getName());
            statement.setString(5, charge.getDescription());
            statement.setDouble(6, charge.getAmount());
            statement.setString(7, charge.getStatus().toString());
            
            statement.executeUpdate();
        }
    }

    // Get total of all charges from SQLite database
    public static double getTotalCharges() throws SQLException {
        String sql = "SELECT SUM(amount) as total FROM room_charges";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getDouble("total");
            }
        }

        return 0.0;
    }
}
