package com.hotel;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        Image logo = new Image(getClass().getResourceAsStream("/logo.png"));
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(120);
        logoView.setPreserveRatio(true);

        Button signin = new Button("Sign In");
        signin.setStyle("-fx-background-color: transparent; -fx-border-color: white; " +
                "-fx-border-radius: 20; -fx-text-fill: white; -fx-padding: 5 20;");

        Button signup = new Button("Join Now");
        signup.setStyle("-fx-background-color: #4ECCA3; -fx-background-radius: 20; " +
                "-fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5 20;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(15, logoView, spacer, signin, signup);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20, 50, 20, 50));
        header.setStyle("-fx-background-color: #1A1B2E;");

        Label title = new Label("Luxury Meets Technology");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 36px; -fx-font-weight: bold;");

        Label subtitle = new Label("The official smart-hotel of New Uzbekistan University");
        subtitle.setStyle("-fx-text-fill: #BDC3C7; -fx-font-size: 16px;");

        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER);
        searchBar.setPadding(new Insets(10));
        searchBar.setMaxWidth(600);
        searchBar.setStyle("-fx-background-color: #252A41; -fx-background-radius: 10;");

        TextField checkIn = new TextField("Check-in");
        TextField checkOut = new TextField("Check-out");
        Button searchBtn = new Button("Search Rooms");
        searchBtn.setStyle("-fx-background-color: #ecf0f1;");
        searchBar.getChildren().addAll(checkIn, checkOut, searchBtn);

        VBox hero = new VBox(20, title, subtitle, searchBar);
        hero.setAlignment(Pos.CENTER);
        hero.setPadding(new Insets(50));
        hero.setStyle("-fx-background-color: #1A1B2E;");


        HBox roomContainer = new HBox(20);
        roomContainer.setAlignment(Pos.CENTER);
        roomContainer.setPadding(new Insets(40));
        roomContainer.setStyle("-fx-background-color: white;");


        roomContainer.getChildren().addAll(
                createRoomCard("AI Smart Suite", "$120/night", "/room1.png"),
                createRoomCard("Robotics Lab View", "$95/night", "/room2.png"),
                createRoomCard("Freshman Dorm+", "$45/night", "/room3.png")
        );

        VBox mainLayout = new VBox(header, hero, roomContainer);
        VBox.setVgrow(roomContainer, Priority.ALWAYS);

        Scene scene = new Scene(mainLayout, 1000, 700);
        stage.setTitle("NewUU Hotel");
        stage.setScene(scene);
        stage.show();
    }

    private VBox createRoomCard(String name, String price, String imagePath) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");
        card.setPrefWidth(280);


        StackPane imgPlaceholder = new StackPane();
        imgPlaceholder.setPrefSize(250, 150);
        imgPlaceholder.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10;");

        try {
            Image roomImg = new Image(getClass().getResourceAsStream(imagePath));
            ImageView roomView = new ImageView(roomImg);


            roomView.setFitWidth(250);
            roomView.setFitHeight(150);
            roomView.setPreserveRatio(false);


            javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle(250, 150);
            clip.setArcWidth(20);
            clip.setArcHeight(20);
            roomView.setClip(clip);

            imgPlaceholder.getChildren().add(roomView);
        } catch (Exception e) {

            imgPlaceholder.getChildren().add(new Label("Image Not Found"));
        }

        Label roomName = new Label(name);
        roomName.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label roomPrice = new Label(price);
        roomPrice.setStyle("-fx-text-fill: #4ECCA3; -fx-font-weight: bold;");

        Button bookBtn = new Button("Book Now");
        bookBtn.setStyle("-fx-background-color: transparent; -fx-border-color: #ddd; -fx-border-radius: 5;");
        bookBtn.setOnAction(e -> {
            Stage stage1 =new Stage();
            Label book = new Label("Order");
            GridPane gridPane = new GridPane();
            gridPane.add(book, 0, 0);

            Scene scene = new Scene(gridPane, 600, 400);
            stage1.setScene(scene);
            stage1.show();

        });

        HBox footer = new HBox(roomPrice, new Region(), bookBtn);
        HBox.setHgrow(footer.getChildren().get(1), Priority.ALWAYS);
        footer.setAlignment(Pos.CENTER);

        card.getChildren().addAll(imgPlaceholder, roomName, footer);
        return card;
    }

    public static void main(String[] args) {
        launch(args);
    }
}