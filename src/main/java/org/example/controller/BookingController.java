package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.hotel.BookingDatabase;
import org.example.hotel.Guest;
import org.example.payment_system.Invoice;
import org.example.payment_system.CashTransaction;
import org.example.payment_system.CreditCardTransaction;
import org.example.hotel.InvoiceItem;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookingController {

    @FXML
    private Label roomLabel;

    @FXML
    private TextField guestNameField;

    @FXML
    private DatePicker checkInPicker;

    @FXML
    private DatePicker checkOutPicker;

    @FXML
    private Label totalLabel;

    @FXML
    private Button proceedToPaymentButton;

    private String roomName;
    private int roomId;
    private double nightlyPrice;
    private Guest guest;

    @FXML
    public void initialize() {
        checkInPicker.setValue(LocalDate.now());
        checkOutPicker.setValue(LocalDate.now().plusDays(1));
        
        checkInPicker.valueProperty().addListener((obs, oldVal, newVal) -> updateTotal());
        checkOutPicker.valueProperty().addListener((obs, oldVal, newVal) -> updateTotal());
        
        proceedToPaymentButton.setOnAction(event -> handleProceedToPayment());
    }

    public void setRoomDetails(String roomName, int roomId, double nightlyPrice) {
        this.roomName = roomName;
        this.roomId = roomId;
        this.nightlyPrice = nightlyPrice;
        
        roomLabel.setText(roomName + " - $" + nightlyPrice + "/night");
        updateTotal();
    }

    private void updateTotal() {
        LocalDate checkIn = checkInPicker.getValue();
        LocalDate checkOut = checkOutPicker.getValue();

        if (checkIn != null && checkOut != null && checkOut.isAfter(checkIn)) {
            long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
            totalLabel.setText("Total: $" + (nights * nightlyPrice));
        } else {
            totalLabel.setText("Total: $0.0");
        }
    }

    @FXML
    public void handleProceedToPayment() {
        String guestName = guestNameField.getText().trim();
        LocalDate checkIn = checkInPicker.getValue();
        LocalDate checkOut = checkOutPicker.getValue();

        if (guestName.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please enter guest name.");
            return;
        }

        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {
            showAlert(Alert.AlertType.WARNING, "Check-out date must be after check-in date.");
            return;
        }

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        double totalPrice = nights * nightlyPrice;

        // Create Guest object
        guest = new Guest();
        
        // Create Invoice with room charges
        Invoice invoice = new Invoice();
        InvoiceItem roomCharge = new InvoiceItem("Room Booking - " + roomName, totalPrice);
        invoice.addItem(roomCharge);

        // Open payment window
        openPaymentWindow(guestName, invoice, checkIn, checkOut, totalPrice);
    }

    private void openPaymentWindow(String guestName, Invoice invoice, LocalDate checkIn, LocalDate checkOut, double totalPrice) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/payment.fxml"));
            Parent root = loader.load();
            
            PaymentController controller = loader.getController();
            controller.setPaymentDetails(guestName, roomId, roomName, invoice, checkIn, checkOut, totalPrice);
            
            Stage stage = new Stage();
            stage.setTitle("Payment");
            stage.setScene(new Scene(root));
            stage.show();
            
            // Close booking window
            Stage currentStage = (Stage) proceedToPaymentButton.getScene().getWindow();
            currentStage.close();
            
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error loading payment window: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
