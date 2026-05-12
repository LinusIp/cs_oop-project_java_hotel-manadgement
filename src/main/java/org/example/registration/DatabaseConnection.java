package org.example.registration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:hotel.db";  // SQLite embedded database
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL);
            initializeTables();
        }
        return connection;
    }

    private static void initializeTables() {
        try (Statement stmt = connection.createStatement()) {
            // Create users table
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS users (" +
                "USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USERNAME TEXT NOT NULL UNIQUE, " +
                "PASSWORD TEXT NOT NULL)"
            );

            // Create rooms table
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS rooms (" +
                "room_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "room_name TEXT NOT NULL, " +
                "price REAL NOT NULL, " +
                "status TEXT DEFAULT 'AVAILABLE')"
            );

            // Create bookings table
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS bookings (" +
                "booking_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "guest_name TEXT NOT NULL, " +
                "room_id INTEGER NOT NULL, " +
                "check_in DATE NOT NULL, " +
                "check_out DATE NOT NULL, " +
                "total_price REAL NOT NULL)"
            );

            // Create room_charges table
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS room_charges (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "charge_id TEXT NOT NULL, " +
                "room_number TEXT NOT NULL, " +
                "booking_id TEXT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "description TEXT, " +
                "amount REAL NOT NULL, " +
                "status TEXT DEFAULT 'PENDING', " +
                "issued_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"
            );

            // Insert default admin account if not exists
            stmt.execute(
                "INSERT OR IGNORE INTO users (USER_ID, USERNAME, PASSWORD) " +
                "VALUES (1, 'admin', '18552007')"
            );

            System.out.println("✓ SQLite database initialized successfully!");
            System.out.println("✓ Database file: hotel.db");
            System.out.println("✓ Tables created: users, rooms, bookings, room_charges");
            System.out.println("✓ Default admin account created (admin/18552007)");
        } catch (SQLException e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
        }
    }
}

