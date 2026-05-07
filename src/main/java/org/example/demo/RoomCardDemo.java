package org.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.UI.RoomCard;

/**
 * Demo application showing how to use the RoomCard component
 * 
 * Run this class to see the RoomCard component in action
 */
public class RoomCardDemo extends Application {

    @Override
    public void start(Stage stage) {
        // Create container for cards
        HBox roomContainer = new HBox(20);
        roomContainer.setAlignment(Pos.CENTER);
        roomContainer.setPadding(new Insets(40));
        roomContainer.setStyle("-fx-background-color: #f5f5f5;");

        // Example 1: AI Smart Suite
        VBox card1 = RoomCard.createRoomCard(120, "AI Smart Suite", "High-tech amenities", "/room1.png", () -> {
            showAlert("Booking", "You clicked Book Now for AI Smart Suite!");
        });

        // Example 2: Robotics Lab View
        VBox card2 = RoomCard.createRoomCard(95, "Robotics Lab View", "Modern design", "/room2.png", () -> {
            showAlert("Booking", "You clicked Book Now for Robotics Lab View!");
        });

        // Example 3: Freshman Dorm+
        VBox card3 = RoomCard.createRoomCard(45, "Freshman Dorm+", "Budget friendly", "/room3.png", () -> {
            showAlert("Booking", "You clicked Book Now for Freshman Dorm+!");
        });

        // Add all cards to container
        roomContainer.getChildren().addAll(card1, card2, card3);

        // Create scene and show
        Scene scene = new Scene(roomContainer, 1000, 400);
        stage.setTitle("RoomCard Component Demo");
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
