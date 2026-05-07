package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.registration.AccountAddToSQL;

import java.sql.SQLException;

public class SignupController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button createButton;

    private final AccountAddToSQL accountService = new AccountAddToSQL();

    @FXML
    public void initialize() {
        createButton.setOnAction(event -> handleSignup());
    }

    @FXML
    public void handleSignup() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please enter username and password.");
            return;
        }

        try {
            accountService.insertAccount(username, password);
            showAlert(Alert.AlertType.INFORMATION, "Account created successfully.");
            
            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database error: " + ex.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
