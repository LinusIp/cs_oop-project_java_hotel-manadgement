package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.registration.AccountAddToSQL;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    private final AccountAddToSQL accountService = new AccountAddToSQL();

    @FXML
    public void initialize() {
        signInButton.setOnAction(event -> handleLogin());
    }

    @FXML
    public void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please enter username and password.");
            return;
        }

        try {
            if (accountService.isValidLogin(username, password)) {
                Stage currentStage = (Stage) signInButton.getScene().getWindow();
                currentStage.close();
                
                openDashboard();
            } else {
                showAlert(Alert.AlertType.WARNING, "Login failed. Check username and password.");
            }
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database error: " + ex.getMessage());
        }
    }

    private void openDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Hotel Dashboard");
            stage.setScene(new Scene(root, 1000, 700));
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error loading dashboard: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
