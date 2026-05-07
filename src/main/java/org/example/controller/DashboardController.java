package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.registration.Account;
import org.example.registration.AccountAddToSQL;

import java.sql.SQLException;

public class DashboardController {

    @FXML
    private Button roomsButton;

    @FXML
    private Button reservationsButton;

    @FXML
    private Button usersButton;

    @FXML
    private Button financialsButton;

    @FXML
    private Button logoutButton;

    @FXML
    private TableView<Account> usersTable;

    @FXML
    private TableColumn<Account, Integer> idColumn;

    @FXML
    private TableColumn<Account, String> usernameColumn;

    @FXML
    private TableColumn<Account, String> passwordColumn;

    private final AccountAddToSQL accountService = new AccountAddToSQL();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        usersButton.setOnAction(event -> loadUsers());
        roomsButton.setOnAction(event -> showInfo("Rooms section - connected to main booking page"));
        reservationsButton.setOnAction(event -> showInfo("Reservations are saved in the bookings table"));
        financialsButton.setOnAction(event -> showInfo("Financials section can be extended later"));
        logoutButton.setOnAction(event -> handleLogout());
    }

    @FXML
    public void loadUsers() {
        try {
            ObservableList<Account> accounts = FXCollections.observableArrayList(accountService.getAllAccounts());
            usersTable.setItems(accounts);
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database error: " + ex.getMessage());
        }
    }

    @FXML
    public void handleLogout() {
        javafx.stage.Stage stage = (javafx.stage.Stage) logoutButton.getScene().getWindow();
        stage.close();
    }

    private void showInfo(String message) {
        showAlert(Alert.AlertType.INFORMATION, message);
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
