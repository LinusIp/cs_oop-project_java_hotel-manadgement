package org.example.UI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.hotel.BookingDatabase;
import org.example.hotel.BookingRecord;
import org.example.hotel.RoomRecord;
import org.example.hotel.RoomCharge;
import org.example.hotel.RoomChargeRecord;
import org.example.hotel.ServiceChargeDatabase;
import org.example.hotel.Amenity;
import org.example.hotel.KitchenService;
import org.example.registration.Server;
import org.example.hotel.Address;
import org.example.registration.Account;
import org.example.registration.AccountAddToSQL;
import org.example.enums.AccountStatus;
import org.example.UI.RoomCard;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Main extends Application {

    private final AccountAddToSQL accountService = new AccountAddToSQL();
    private Scene homeScene;
    private Stage primaryStage;  // Store stage reference
    private HBox roomContainer;  // Store room container reference

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;  // Store stage reference
        
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
        this.roomContainer = roomContainer;  // Store reference

        // Load rooms from RoomManager dynamically
        refreshRoomContainer();

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

        checkInPicker.valueProperty().addListener((obs, o, n) -> updateTotal.run());
        checkOutPicker.valueProperty().addListener((obs, o, n) -> updateTotal.run());

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

        stage1.setScene(new Scene(gridPane, 600, 400));
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
            try {
                String username = usernameField.getText().trim();
                String password = passwordField.getText();

                // Validation
                if (username.isEmpty() || password.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, "Please enter both username and password.");
                    return;
                }
                
                if (username.length() > 50) {
                    showAlert(Alert.AlertType.ERROR, "Username is too long.");
                    return;
                }

                // Admin login (hardcoded)
                if (username.equals("admin") && password.equals("18552007")) {
                    loginStage.close();
                    mainStage.setScene(createDashboardSceneForAdmin(mainStage));
                    mainStage.setTitle("Admin Dashboard");
                } else {
                    // Regular user login (database)
                    try {
                        if (accountService.isValidLogin(username, password)) {
                            Account loggedInAccount = accountService.getAccountByUsername(username);
                            
                            if (loggedInAccount == null) {
                                showAlert(Alert.AlertType.ERROR, "Account not found.");
                                return;
                            }
                            
                            loginStage.close();
                            mainStage.setScene(createDashboardScene(mainStage, loggedInAccount));
                            mainStage.setTitle("Hotel Dashboard - " + username);
                        } else {
                            showAlert(Alert.AlertType.WARNING, "Invalid username or password.\n\nPlease try again.");
                        }
                    } catch (SQLException ex) {
                        showAlert(Alert.AlertType.ERROR, 
                                 "Database Connection Error\n\n" +
                                 "Could not connect to database: " + ex.getMessage() + "\n\n" +
                                 "Please check your database connection.");
                    }
                }
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, 
                         "Login Error\n\n" +
                         "An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();
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

    // Regular user dashboard — "Account Settings" instead of "Users"
    private Scene createDashboardScene(Stage mainStage, Account loggedInAccount) {
        BorderPane root = new BorderPane();

        Label title = new Label("Hotel");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold; " +
                "-fx-border-color: white; -fx-border-width: 0 0 2 0; -fx-padding: 5;");

        Button roomsButton = new Button("Rooms");
        Button reservationsButton = new Button("Reservations");
        Button accountSettingsButton = new Button("Account Settings");
        Button financialsButton = new Button("Financials");
        Button logoutButton = new Button("Log Out");

        for (Button button : new Button[]{roomsButton, reservationsButton, accountSettingsButton, financialsButton, logoutButton}) {
            button.setMaxWidth(Double.MAX_VALUE);
        }

        VBox sidebar = new VBox(10, title, roomsButton, reservationsButton, accountSettingsButton, financialsButton, logoutButton);
        sidebar.setStyle("-fx-background-color: #2c3e50; -fx-padding: 15;");
        sidebar.setPrefWidth(180);
        root.setLeft(sidebar);

        Label welcome = new Label("Welcome, " + loggedInAccount.getUsername() + "!");
        welcome.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        StackPane centerPane = new StackPane(welcome);
        centerPane.setPadding(new Insets(30));
        root.setCenter(centerPane);

        roomsButton.setOnAction(event -> root.setCenter(createRoomsPane()));
        reservationsButton.setOnAction(event -> root.setCenter(createReservationsPane()));
        accountSettingsButton.setOnAction(event -> root.setCenter(createAccountSettingsPane(loggedInAccount)));
        financialsButton.setOnAction(event -> root.setCenter(createFinancialsPane()));
        logoutButton.setOnAction(event -> {
            mainStage.setScene(homeScene);
            mainStage.setTitle("NewUU Hotel");
        });

        return new Scene(root, 1000, 700);
    }

    // Admin dashboard — full access with "Users" table
    private Scene createDashboardSceneForAdmin(Stage mainStage) {
        BorderPane root = new BorderPane();

        Label title = new Label("Admin");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold; " +
                "-fx-border-color: white; -fx-border-width: 0 0 2 0; -fx-padding: 5;");

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

        Label welcome = new Label("Welcome to the admin dashboard");
        welcome.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        StackPane centerPane = new StackPane(welcome);
        centerPane.setPadding(new Insets(30));
        root.setCenter(centerPane);

        usersButton.setOnAction(event -> root.setCenter(createUsersTable()));
        roomsButton.setOnAction(event -> root.setCenter(createRoomsPane()));
        reservationsButton.setOnAction(event -> root.setCenter(createReservationsPane()));
        financialsButton.setOnAction(event -> root.setCenter(createFinancialsPane()));
        logoutButton.setOnAction(event -> {
            mainStage.setScene(homeScene);
            mainStage.setTitle("NewUU Hotel");
        });

        return new Scene(root, 1000, 700);
    }

    // Account settings pane — change username and/or password for the logged-in user
    private VBox createAccountSettingsPane(Account account) {
        Label heading = new Label("Account Settings");
        heading.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label infoLabel = new Label("Logged in as: " + account.getUsername());
        infoLabel.setStyle("-fx-text-fill: #555; -fx-font-size: 13px;");

        TextField newUsernameField = new TextField(account.getUsername());
        newUsernameField.setPromptText("New username");

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("New password (leave blank to keep current)");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm new password");

        Button saveButton = new Button("Save Changes");
        saveButton.setStyle("-fx-background-color: #4ECCA3; -fx-text-fill: white; -fx-font-weight: bold;");

        saveButton.setOnAction(event -> {
            String newUsername = newUsernameField.getText().trim();
            String newPassword = newPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (newUsername.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Username cannot be empty.");
                return;
            }

            if (!newPassword.isEmpty() && !newPassword.equals(confirmPassword)) {
                showAlert(Alert.AlertType.WARNING, "Passwords do not match.");
                return;
            }

            // Keep current password if the user left password fields blank
            String passwordToSave = newPassword.isEmpty() ? account.getPassword() : newPassword;

            try {
                accountService.updateAccount(account.getID(), newUsername, passwordToSave);

                // Update the in-memory Account so the welcome label and future saves stay correct
                account.setUsername(newUsername);
                account.setPassword(passwordToSave);

                infoLabel.setText("Logged in as: " + newUsername);
                newPasswordField.clear();
                confirmPasswordField.clear();

                showAlert(Alert.AlertType.INFORMATION, "Account updated successfully.");
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Database error: " + ex.getMessage());
            }
        });

        GridPane form = new GridPane();
        form.setHgap(12);
        form.setVgap(14);
        form.add(new Label("Username:"), 0, 0);
        form.add(newUsernameField, 1, 0);
        form.add(new Label("New Password:"), 0, 1);
        form.add(newPasswordField, 1, 1);
        form.add(new Label("Confirm Password:"), 0, 2);
        form.add(confirmPasswordField, 1, 2);
        form.add(saveButton, 1, 3);

        VBox box = new VBox(14, heading, infoLabel, form);
        box.setPadding(new Insets(30));
        return box;
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

    private VBox createRoomsPane() {
        Label heading = new Label("Rooms Management");
        heading.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Create Room button for admin
        Button createRoomButton = new Button("+ Create Room");
        createRoomButton.setStyle("-fx-background-color: #4ECCA3; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        createRoomButton.setOnAction(event -> openCreateRoomDialog());

        TableView<RoomRecord> tableView = new TableView<>();
        tableView.getColumns().addAll(
                createColumn("ROOM_ID", "roomId", 90),
                createColumn("ROOM_NAME", "roomName", 220),
                createColumn("PRICE", "price", 90),
                createColumn("STATUS", "status", 120)
        );
        loadRooms(tableView);

        Button refreshButton = new Button("Refresh rooms");
        refreshButton.setOnAction(event -> loadRooms(tableView));

        HBox buttonBar = new HBox(10, createRoomButton, refreshButton);
        buttonBar.setAlignment(Pos.CENTER_LEFT);

        VBox box = new VBox(12, heading, buttonBar, tableView);
        box.setPadding(new Insets(25));
        VBox.setVgrow(tableView, Priority.ALWAYS);
        return box;
    }

    private void loadRooms(TableView<RoomRecord> tableView) {
        try {
            tableView.getItems().setAll(BookingDatabase.getAllRooms());
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database error: " + ex.getMessage());
        }
    }

    private VBox createReservationsPane() {
        Label heading = new Label("Reservations from SQL");
        heading.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TableView<BookingRecord> tableView = new TableView<>();
        tableView.getColumns().addAll(
                createColumn("BOOKING_ID", "bookingId", 100),
                createColumn("GUEST", "guestName", 160),
                createColumn("ROOM_ID", "roomId", 90),
                createColumn("ROOM", "roomName", 180),
                createColumn("CHECK_IN", "checkIn", 120),
                createColumn("CHECK_OUT", "checkOut", 120),
                createColumn("TOTAL", "totalPrice", 100)
        );
        loadReservations(tableView);

        Button refreshButton = new Button("Refresh reservations");
        refreshButton.setOnAction(event -> loadReservations(tableView));

        VBox box = new VBox(12, heading, refreshButton, tableView);
        box.setPadding(new Insets(25));
        VBox.setVgrow(tableView, Priority.ALWAYS);
        return box;
    }

    private void loadReservations(TableView<BookingRecord> tableView) {
        try {
            tableView.getItems().setAll(BookingDatabase.getAllBookings());
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database error: " + ex.getMessage());
        }
    }

    private VBox createFinancialsPane() {
        Label heading = new Label("Room Service, Amenities and Charges");
        heading.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField bookingIdField = new TextField();
        bookingIdField.setPromptText("Booking ID");

        TextField roomNumberField = new TextField();
        roomNumberField.setPromptText("Room number / room id");

        ComboBox<String> serviceTypeBox = new ComboBox<>();
        serviceTypeBox.getItems().addAll("Kitchen Service", "Amenity");
        serviceTypeBox.setValue("Kitchen Service");

        TextField serviceNameField = new TextField("Breakfast");
        serviceNameField.setPromptText("Service name");

        TextField descriptionField = new TextField("Food delivery to the room");
        descriptionField.setPromptText("Description");

        TextField priceField = new TextField("15.00");
        priceField.setPromptText("Price");

        Button breakfastButton = new Button("Breakfast $15");
        breakfastButton.setOnAction(event -> {
            serviceTypeBox.setValue("Kitchen Service");
            serviceNameField.setText("Breakfast");
            descriptionField.setText("Food delivery to the room");
            priceField.setText("15.00");
        });

        Button dinnerButton = new Button("Dinner $30");
        dinnerButton.setOnAction(event -> {
            serviceTypeBox.setValue("Kitchen Service");
            serviceNameField.setText("Dinner");
            descriptionField.setText("Evening meal service");
            priceField.setText("30.00");
        });

        Button spaButton = new Button("Spa $45");
        spaButton.setOnAction(event -> {
            serviceTypeBox.setValue("Amenity");
            serviceNameField.setText("Spa Access");
            descriptionField.setText("Premium spa amenity");
            priceField.setText("45.00");
        });

        HBox presets = new HBox(8, breakfastButton, dinnerButton, spaButton);

        TableView<RoomChargeRecord> chargeTable = createRoomChargeTable();
        Label totalChargesLabel = new Label();

        Button addChargeButton = new Button("Add charge through Server");
        addChargeButton.setStyle("-fx-background-color: #4ECCA3; -fx-text-fill: white; -fx-font-weight: bold;");
        addChargeButton.setOnAction(event -> {
            try {
                String bookingId = bookingIdField.getText().trim();
                String roomNumber = roomNumberField.getText().trim();
                String serviceName = serviceNameField.getText().trim();
                String description = descriptionField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());

                if (bookingId.isEmpty() || roomNumber.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, "Please enter booking ID and room number.");
                    return;
                }

                RoomCharge charge;
                if ("Amenity".equals(serviceTypeBox.getValue())) {
                    Amenity amenity = new Amenity(serviceName, description, price);
                    charge = amenity.toRoomCharge(roomNumber, bookingId);
                } else {
                    KitchenService kitchenService = new KitchenService(serviceName, description, price);
                    charge = kitchenService.toRoomCharge(roomNumber, bookingId);
                }

                Server server = new Server(
                        "Dashboard Server",
                        new Address("NewUU", "Tashkent", "Tashkent", "100000", "Uzbekistan"),
                        "server@newuu.hotel",
                        "+998000000000",
                        new Account("server", "server", AccountStatus.ACTIVE)
                );

                if (server.addRoomCharge(charge)) {
                    ServiceChargeDatabase.addRoomCharge(charge);
                    showAlert(Alert.AlertType.INFORMATION, "Charge added: " + charge.getChargeId());
                    loadRoomCharges(chargeTable, totalChargesLabel);
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.WARNING, "Price must be a valid number.");
            } catch (IllegalArgumentException ex) {
                showAlert(Alert.AlertType.WARNING, ex.getMessage());
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Database error: " + ex.getMessage());
            }
        });

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.add(new Label("Booking ID:"), 0, 0);
        form.add(bookingIdField, 1, 0);
        form.add(new Label("Room:"), 2, 0);
        form.add(roomNumberField, 3, 0);
        form.add(new Label("Type:"), 0, 1);
        form.add(serviceTypeBox, 1, 1);
        form.add(new Label("Name:"), 2, 1);
        form.add(serviceNameField, 3, 1);
        form.add(new Label("Description:"), 0, 2);
        form.add(descriptionField, 1, 2, 3, 1);
        form.add(new Label("Price:"), 0, 3);
        form.add(priceField, 1, 3);
        form.add(addChargeButton, 3, 3);

        Button refreshButton = new Button("Refresh charges");
        refreshButton.setOnAction(event -> loadRoomCharges(chargeTable, totalChargesLabel));

        HBox actions = new HBox(12, presets, refreshButton, totalChargesLabel);
        actions.setAlignment(Pos.CENTER_LEFT);

        VBox box = new VBox(12, heading, form, actions, chargeTable);
        box.setPadding(new Insets(25));
        VBox.setVgrow(chargeTable, Priority.ALWAYS);

        loadRoomCharges(chargeTable, totalChargesLabel);
        return box;
    }

    private TableView<RoomChargeRecord> createRoomChargeTable() {
        TableView<RoomChargeRecord> tableView = new TableView<>();
        tableView.getColumns().addAll(
                createColumn("ID", "id", 60),
                createColumn("CHARGE_CODE", "chargeId", 110),
                createColumn("ROOM", "roomNumber", 80),
                createColumn("BOOKING", "bookingId", 90),
                createColumn("NAME", "name", 190),
                createColumn("DESCRIPTION", "description", 240),
                createColumn("AMOUNT", "amount", 90),
                createColumn("STATUS", "status", 90)
        );
        return tableView;
    }

    private void loadRoomCharges(TableView<RoomChargeRecord> tableView, Label totalChargesLabel) {
        try {
            tableView.getItems().setAll(ServiceChargeDatabase.getAllCharges());
            totalChargesLabel.setText("Total service charges: $" + ServiceChargeDatabase.getTotalCharges());
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database error: " + ex.getMessage());
        }
    }

    private <T, C> TableColumn<T, C> createColumn(String title, String property, double width) {
        TableColumn<T, C> column = new TableColumn<>(title);
        column.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>(property));
        column.setPrefWidth(width);
        return column;
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Refresh room container with rooms from SQL database
    private void refreshRoomContainer() {
        try {
            roomContainer.getChildren().clear();
            
            java.util.List<RoomRecord> rooms = BookingDatabase.getAllRooms();
            
            if (rooms.isEmpty()) {
                Label noRooms = new Label("No rooms available.\n\nAdmin can create rooms from the dashboard.");
                noRooms.setStyle("-fx-font-size: 16px; -fx-text-fill: #888;");
                roomContainer.getChildren().add(noRooms);
                return;
            }
            
            for (RoomRecord room : rooms) {
                try {
                    VBox card = RoomCard.createRoomCard(
                        (int) room.getPrice(),
                        room.getRoomName(),
                        "Room #" + room.getRoomId(),
                        "/room" + ((room.getRoomId() % 3) + 1) + ".png",
                        () -> openBookingWindow(room.getRoomName(), room.getRoomId(), room.getPrice())
                    );
                    roomContainer.getChildren().add(card);
                } catch (Exception e) {
                    System.err.println("Failed to create room card for room " + room.getRoomId() + ": " + e.getMessage());
                    // Continue with other rooms even if one fails
                }
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, 
                     "Database Error\n\n" +
                     "Failed to load rooms: " + e.getMessage() + "\n\n" +
                     "Please check your database connection.");
            
            // Show error message in room container
            Label errorLabel = new Label("Failed to load rooms from database.\n\nPlease check your connection.");
            errorLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: red;");
            roomContainer.getChildren().add(errorLabel);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, 
                     "Unexpected Error\n\n" +
                     "An error occurred while loading rooms: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Open dialog for admin to create a new room
    private void openCreateRoomDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Create New Room");

        Label heading = new Label("Create New Room");
        heading.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Input fields
        TextField nameField = new TextField();
        nameField.setPromptText("Room Name (e.g., Deluxe Suite)");

        TextField priceField = new TextField();
        priceField.setPromptText("Price per night (e.g., 120)");

        TextField descField = new TextField();
        descField.setPromptText("Description (e.g., Luxury amenities)");

        TextField imageField = new TextField();
        imageField.setPromptText("Image path (e.g., /room1.png)");
        imageField.setText("/room1.png");  // Default value

        Button createButton = new Button("Create Room");
        createButton.setStyle("-fx-background-color: #4ECCA3; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 30;");

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> dialog.close());

        createButton.setOnAction(event -> {
            try {
                String name = nameField.getText().trim();
                String priceText = priceField.getText().trim();
                String description = descField.getText().trim();
                String imagePath = imageField.getText().trim();

                // Validation 1: Room name
                if (name.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Room name cannot be empty.");
                    nameField.requestFocus();
                    return;
                }
                
                if (name.length() > 100) {
                    showAlert(Alert.AlertType.ERROR, "Room name is too long (max 100 characters).");
                    nameField.requestFocus();
                    return;
                }

                // Validation 2: Price
                if (priceText.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Price cannot be empty.");
                    priceField.requestFocus();
                    return;
                }

                int price;
                try {
                    price = Integer.parseInt(priceText);
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Invalid price format.\n\nPlease enter a valid number (e.g., 120)");
                    priceField.requestFocus();
                    return;
                }
                
                if (price <= 0) {
                    showAlert(Alert.AlertType.ERROR, "Price must be greater than zero.");
                    priceField.requestFocus();
                    return;
                }
                
                if (price > 100000) {
                    showAlert(Alert.AlertType.ERROR, "Price is too high (max $100,000).");
                    priceField.requestFocus();
                    return;
                }

                // Validation 3: Description (optional, set default)
                if (description.isEmpty()) {
                    description = "No description provided";
                }
                
                if (description.length() > 500) {
                    showAlert(Alert.AlertType.ERROR, "Description is too long (max 500 characters).");
                    descField.requestFocus();
                    return;
                }

                // Validation 4: Image path (optional, set default)
                if (imagePath.isEmpty()) {
                    imagePath = "/room1.png";
                }

                // Add room to SQL database
                try {
                    BookingDatabase.addRoom(price, name, price);
                    
                    // Refresh the main page room container
                    refreshRoomContainer();

                    showAlert(Alert.AlertType.INFORMATION, 
                             "✓ Room created successfully!\n\n" +
                             "Name: " + name + "\n" +
                             "Price: $" + price + "/night\n" +
                             "Description: " + description + "\n\n" +
                             "The room is now visible on the main page.");

                    dialog.close();
                    
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, 
                             "Database Error\n\n" +
                             "Failed to create room: " + e.getMessage() + "\n\n" +
                             "Please check your database connection.");
                }
                
            } catch (Exception e) {
                // Catch any unexpected errors
                showAlert(Alert.AlertType.ERROR, 
                         "Unexpected Error\n\n" +
                         "An error occurred: " + e.getMessage() + "\n\n" +
                         "Please try again or contact support.");
                e.printStackTrace();
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setPadding(new Insets(30));

        grid.add(heading, 0, 0, 2, 1);
        grid.add(new Label("Room Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Price/Night:"), 0, 2);
        grid.add(priceField, 1, 2);
        grid.add(new Label("Description:"), 0, 3);
        grid.add(descField, 1, 3);
        grid.add(new Label("Image Path:"), 0, 4);
        grid.add(imageField, 1, 4);

        HBox buttonBar = new HBox(10, createButton, cancelButton);
        buttonBar.setAlignment(Pos.CENTER);
        grid.add(buttonBar, 0, 5, 2, 1);

        Scene scene = new Scene(grid, 500, 400);
        dialog.setScene(scene);
        dialog.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
