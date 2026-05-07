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

public class MainController {

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField searchCheckIn;

    @FXML
    private TextField searchCheckOut;

    @FXML
    private Button searchButton;

    private final AccountAddToSQL accountService = new AccountAddToSQL();

    @FXML
    public void initialize() {
        signInButton.setOnAction(event -> openLoginWindow());
        signUpButton.setOnAction(event -> openSignupWindow());
        searchButton.setOnAction(event -> openSearchWindow());
    }

    @FXML
    public void handleRoom1Booking() {
        openBookingWindow("AI Smart Suite", 1, 120.00);
    }

    @FXML
    public void handleRoom2Booking() {
        openBookingWindow("Robotics Lab View", 2, 95.00);
    }

    @FXML
    public void handleRoom3Booking() {
        openBookingWindow("Freshman Dorm+", 3, 45.00);
    }

    private void openLoginWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Sign In");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error loading login window: " + e.getMessage());
        }
    }

    private void openSignupWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Join Now");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error loading signup window: " + e.getMessage());
        }
    }

    private void openSearchWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/search.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Search Rooms");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error loading search window: " + e.getMessage());
        }
    }

    private void openBookingWindow(String roomName, int roomId, double nightlyPrice) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/booking.fxml"));
            Parent root = loader.load();
            
            BookingController controller = loader.getController();
            controller.setRoomDetails(roomName, roomId, nightlyPrice);
            
            Stage stage = new Stage();
            stage.setTitle("Book Room");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error loading booking window: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
