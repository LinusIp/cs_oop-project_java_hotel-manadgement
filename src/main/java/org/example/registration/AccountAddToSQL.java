package org.example.registration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountAddToSQL {

    public List<Account> getAllAccounts() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT USER_ID, USERNAME, PASSWORD FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                accounts.add(new Account(
                        rs.getInt("USER_ID"),
                        rs.getString("USERNAME"),
                        rs.getString("PASSWORD")
                ));
            }
        }
        return accounts;
    }

    public void insertAccount(String userName, String password) throws SQLException {
        String query = "INSERT INTO users (USERNAME, PASSWORD) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userName);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        }
    }

    public void deleteAccount(int accountID) throws SQLException {
        String query = "DELETE FROM users WHERE USER_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, accountID);
            pstmt.executeUpdate();
        }
    }

    public void updateAccount(int accountID, String username, String password) throws SQLException {
        String query = "UPDATE users SET USERNAME = ?, PASSWORD = ? WHERE USER_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setInt(3, accountID);
            pstmt.executeUpdate();
        }
    }

    public boolean isValidLogin(String username, String password) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE USERNAME = ? AND PASSWORD = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}
