package org.example;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class BookingDatabase {

    public static int addBooking(String guestName, int roomId, String roomName,
                                 LocalDate checkIn, LocalDate checkOut,
                                 double totalPrice, double roomPrice) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            try {
                ensureRoomExists(connection, roomId, roomName, roomPrice);
                int bookingId = insertBooking(connection, guestName, roomId, checkIn, checkOut, totalPrice);
                updateRoomStatus(connection, roomId);

                connection.commit();
                return bookingId;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    private static void ensureRoomExists(Connection connection, int roomId, String roomName, double roomPrice) throws SQLException {
        String sql = "INSERT INTO rooms (room_id, room_name, price, status) " +
                "VALUES (?, ?, ?, 'AVAILABLE') " +
                "ON DUPLICATE KEY UPDATE room_name = ?, price = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roomId);
            statement.setString(2, roomName);
            statement.setDouble(3, roomPrice);
            statement.setString(4, roomName);
            statement.setDouble(5, roomPrice);
            statement.executeUpdate();
        }
    }

    private static int insertBooking(Connection connection, String guestName, int roomId,
                                     LocalDate checkIn, LocalDate checkOut,
                                     double totalPrice) throws SQLException {
        String sql = "INSERT INTO bookings (guest_name, room_id, check_in, check_out, total_price) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, guestName);
            statement.setInt(2, roomId);
            statement.setDate(3, Date.valueOf(checkIn));
            statement.setDate(4, Date.valueOf(checkOut));
            statement.setDouble(5, totalPrice);
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }

        return 0;
    }

    private static void updateRoomStatus(Connection connection, int roomId) throws SQLException {
        String sql = "UPDATE rooms SET status = 'BOOKED' WHERE room_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roomId);
            statement.executeUpdate();
        }
    }
}
