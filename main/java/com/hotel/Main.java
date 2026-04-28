package com.hotel;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Image logo = new Image(getClass().getResourceAsStream("/logo.png"));
        ImageView imageView = new ImageView(logo);
        imageView.setFitHeight(250);
        imageView.setFitWidth(500);
        Button signin = new Button("signin");
        signin.setLayoutY(10);
        signin.setLayoutX(800);
        signin.scaleShapeProperty();
        Button signup = new Button("signup");
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #32f323");
        gridPane.add(imageView, 0, 0);
        gridPane.add(signin, 1, 1);
        gridPane.add(signup, 2, 2);


        Scene scene = new Scene(gridPane);
        stage.setTitle("NewUU Hotel");
        stage.setScene(scene);
        stage.show();

    }
}
