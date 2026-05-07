package org.example.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

public class RoomCard {

    /**
     * Creates a room card with all standard components
     * 
     * @param price Room price per night
     * @param name Room name
     * @param description Room description
     * @param imagePath Path to room image (e.g., "/room1.png")
     * @param onBookClick Action to perform when "Book Now" is clicked (can be null)
     * @return VBox containing the complete room card UI
     */
    public static VBox createRoomCard(int price, String name, String description, String imagePath, Runnable onBookClick) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");
        card.setPrefWidth(280);

        // IMAGE AREA
        StackPane imageContainer = new StackPane();
        imageContainer.setPrefSize(250, 150);
        imageContainer.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10;");

        try {
            Image img = new Image(RoomCard.class.getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(img);
            
            Rectangle clip = new Rectangle(250, 150);
            clip.setArcWidth(20);
            clip.setArcHeight(20);
            
            imageView.setFitWidth(250);
            imageView.setFitHeight(150);
            imageView.setPreserveRatio(false);
            imageView.setClip(clip);
            
            imageContainer.getChildren().add(imageView);
        } catch (Exception e) {
            // Fallback if image not found
            Label placeholderText = new Label(description);
            placeholderText.setStyle("-fx-text-fill: #888; -fx-font-size: 12px;");
            imageContainer.getChildren().add(placeholderText);
        }

        // TEXT
        Label roomName = new Label(name);
        roomName.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label roomPrice = new Label("$" + price + "/night");
        roomPrice.setStyle("-fx-text-fill: #4ECCA3; -fx-font-weight: bold;");

        // BUTTON
        Button bookBtn = new Button("Book Now");
        bookBtn.setStyle("-fx-background-color: transparent; -fx-border-color: #ddd; -fx-border-radius: 5;");
        
        // Set click action if provided
        if (onBookClick != null) {
            bookBtn.setOnAction(e -> onBookClick.run());
        }

        // FOOTER
        HBox footer = new HBox(roomPrice, new Region(), bookBtn);
        HBox.setHgrow(footer.getChildren().get(1), Priority.ALWAYS);
        footer.setAlignment(Pos.CENTER);

        card.getChildren().addAll(imageContainer, roomName, footer);
        return card;
    }
}
