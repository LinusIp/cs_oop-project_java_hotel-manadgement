package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Main extends Application {

    private final AccountAddToSQL accountService = new AccountAddToSQL();
    private Scene homeScene;

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
        signin.setOnAction(event -> openLoginWindow(stage));
        signup.setOnAction(event -> openSignupWindow());

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
                createRoomCard("AI Smart Suite", "$120/night", "/room1.png", 1, 120.00),
                createRoomCard("Robotics Lab View", "$95/night", "/room2.png", 2, 95.00),
                createRoomCard("Freshman Dorm+", "$45/night", "/room3.png", 3, 45.00)
        );

        VBox mainLayout = new VBox(header, hero, roomContainer);
        VBox.setVgrow(roomContainer, Priority.ALWAYS);

        Scene scene = new Scene(mainLayout, 1000, 700);
        homeScene = scene;
        stage.setTitle("NewUU Hotel");
        stage.setScene(scene);
        stage.show();
    }

    private VBox createRoomCard(String name, String price, String imagePath, int roomId, double nightlyPrice) {
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
        bookBtn.setOnAction(e -> openBookingWindow(name, roomId, nightlyPrice));

        HBox footer = new HBox(roomPrice, new Region(), bookBtn);
        HBox.setHgrow(footer.getChildren().get(1), Priority.ALWAYS);
        footer.setAlignment(Pos.CENTER);

        card.getChildren().addAll(imgPlaceholder, roomName, footer);
        return card;
    }


    private void openBookingWindow(String roomName, int roomId, double nightlyPrice) {
        Stage stage1 = new Stage();
        stage1.setTitle("Order");

        Label book = new Label("Order");
        book.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label roomLabel = new Label(roomName + " - $" + nightlyPrice + "/night");

        TextField guestName = new TextField();
        guestName.setPromptText("Guest name");

        DatePicker checkInPicker = new DatePicker(LocalDate.now());
        DatePicker checkOutPicker = new DatePicker(LocalDate.now().plusDays(1));

        Label totalLabel = new Label("Total: $" + nightlyPrice);
        totalLabel.setStyle("-fx-text-fill: #4ECCA3; -fx-font-weight: bold;");

        Button saveButton = new Button("Save Booking");
        saveButton.setStyle("-fx-background-color: #4ECCA3; -fx-text-fill: white; -fx-font-weight: bold;");

        Runnable updateTotal = () -> {
            LocalDate checkIn = checkInPicker.getValue();
            LocalDate checkOut = checkOutPicker.getValue();

            if (checkIn != null && checkOut != null && checkOut.isAfter(checkIn)) {
                long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
                totalLabel.setText("Total: $" + (nights * nightlyPrice));
            } else {
                totalLabel.setText("Total: $0.0");
            }
        };

        checkInPicker.valueProperty().addListener((observable, oldValue, newValue) -> updateTotal.run());
        checkOutPicker.valueProperty().addListener((observable, oldValue, newValue) -> updateTotal.run());

        saveButton.setOnAction(event -> {
            String name = guestName.getText().trim();
            LocalDate checkIn = checkInPicker.getValue();
            LocalDate checkOut = checkOutPicker.getValue();

            if (name.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Please enter guest name.");
                return;
            }

            if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {
                showAlert(Alert.AlertType.WARNING, "Check-out date must be after check-in date.");
                return;
            }

            long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
            double totalPrice = nights * nightlyPrice;

            try {
                int bookingId = BookingDatabase.addBooking(name, roomId, roomName, checkIn, checkOut, totalPrice, nightlyPrice);
                showAlert(Alert.AlertType.INFORMATION, "Booking saved successfully! Booking ID: " + bookingId);
                stage1.close();
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Database error: " + ex.getMessage());
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(30));
        gridPane.setHgap(10);
        gridPane.setVgap(15);
        gridPane.add(book, 0, 0, 2, 1);
        gridPane.add(new Label("Room:"), 0, 1);
        gridPane.add(roomLabel, 1, 1);
        gridPane.add(new Label("Guest:"), 0, 2);
        gridPane.add(guestName, 1, 2);
        gridPane.add(new Label("Check-in:"), 0, 3);
        gridPane.add(checkInPicker, 1, 3);
        gridPane.add(new Label("Check-out:"), 0, 4);
        gridPane.add(checkOutPicker, 1, 4);
        gridPane.add(totalLabel, 1, 5);
        gridPane.add(saveButton, 1, 6);

        Scene scene = new Scene(gridPane, 600, 400);
        stage1.setScene(scene);
        stage1.show();
    }


    private void openLoginWindow(Stage mainStage) {
        Stage loginStage = new Stage();
        loginStage.setTitle("Sign In");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button signInButton = new Button("Sign In");
        signInButton.setStyle("-fx-background-color: #4ECCA3; -fx-text-fill: white; -fx-font-weight: bold;");

        signInButton.setOnAction(event -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Please enter username and password.");
                return;
            }

            try {
                if (accountService.isValidLogin(username, password)) {
                    loginStage.close();
                    mainStage.setScene(createDashboardScene(mainStage));
                    mainStage.setTitle("Hotel Dashboard");
                } else {
                    showAlert(Alert.AlertType.WARNING, "Login failed. Check username and password.");
                }
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Database error: " + ex.getMessage());
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(25));
        gridPane.setHgap(10);
        gridPane.setVgap(12);
        gridPane.add(new Label("Username:"), 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(new Label("Password:"), 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(signInButton, 1, 2);

        loginStage.setScene(new Scene(gridPane, 350, 180));
        loginStage.show();
    }

    private void openSignupWindow() {
        Stage signupStage = new Stage();
        signupStage.setTitle("Join Now");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button createButton = new Button("Create Account");
        createButton.setStyle("-fx-background-color: #4ECCA3; -fx-text-fill: white; -fx-font-weight: bold;");

        createButton.setOnAction(event -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Please enter username and password.");
                return;
            }

            try {
                accountService.insertAccount(username, password);
                showAlert(Alert.AlertType.INFORMATION, "Account created successfully.");
                signupStage.close();
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Database error: " + ex.getMessage());
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(25));
        gridPane.setHgap(10);
        gridPane.setVgap(12);
        gridPane.add(new Label("Username:"), 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(new Label("Password:"), 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(createButton, 1, 2);

        signupStage.setScene(new Scene(gridPane, 380, 180));
        signupStage.show();
    }

    private Scene createDashboardScene(Stage mainStage) {
        BorderPane root = new BorderPane();

        Label title = new Label("Hotel");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold; -fx-border-color: white; -fx-border-width: 0 0 2 0; -fx-padding: 5;");

        Button roomsButton = new Button("Rooms");
        Button reservationsButton = new Button("Reservations");
        Button usersButton = new Button("Users");
        Button financialsButton = new Button("Financials");
        Button logoutButton = new Button("Log Out");

        for (Button button : new Button[]{roomsButton, reservationsButton, usersButton, financialsButton, logoutButton}) {
            button.setMaxWidth(Double.MAX_VALUE);
        }

        VBox sidebar = new VBox(10, title, roomsButton, reservationsButton, usersButton, financialsButton, logoutButton);
        sidebar.setStyle("-fx-background-color: #2c3e50; -fx-padding: 15;");
        sidebar.setPrefWidth(180);
        root.setLeft(sidebar);

        Label welcome = new Label("Welcome to the hotel dashboard");
        welcome.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        StackPane centerPane = new StackPane(welcome);
        centerPane.setPadding(new Insets(30));
        root.setCenter(centerPane);

        usersButton.setOnAction(event -> root.setCenter(createUsersTable()));
        roomsButton.setOnAction(event -> root.setCenter(new StackPane(new Label("Rooms section is connected to the main booking page."))));
        reservationsButton.setOnAction(event -> root.setCenter(new StackPane(new Label("Reservations are saved in the bookings table."))));
        financialsButton.setOnAction(event -> root.setCenter(new StackPane(new Label("Financials section can be extended later."))));
        logoutButton.setOnAction(event -> {
            mainStage.setScene(homeScene);
            mainStage.setTitle("NewUU Hotel");
        });

        return new Scene(root, 1000, 700);
    }

    private TableView<Account> createUsersTable() {
        TableView<Account> tableView = new TableView<>();

        TableColumn<Account, Integer> idColumn = new TableColumn<>("ACCOUNT_ID");
        idColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("ID"));

        TableColumn<Account, String> usernameColumn = new TableColumn<>("USERNAME");
        usernameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("username"));

        TableColumn<Account, String> passwordColumn = new TableColumn<>("PASSWORD");
        passwordColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("password"));

        tableView.getColumns().addAll(idColumn, usernameColumn, passwordColumn);

        try {
            tableView.getItems().setAll(accountService.getAllAccounts());
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database error: " + ex.getMessage());
        }

        return tableView;
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }
}



