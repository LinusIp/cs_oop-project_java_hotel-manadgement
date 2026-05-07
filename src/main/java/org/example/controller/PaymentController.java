package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.hotel.BookingDatabase;
import org.example.payment_system.Invoice;
import org.example.payment_system.CashTransaction;
import org.example.payment_system.CreditCardTransaction;
import org.example.payment_system.BillTransaction;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PaymentController {

    @FXML
    private Label invoiceLabel;

    @FXML
    private RadioButton cashRadio;

    @FXML
    private RadioButton creditCardRadio;

    @FXML
    private TextField cashTenderedField;

    @FXML
    private TextField cardNameField;

    @FXML
    private TextField zipCodeField;

    @FXML
    private Button payButton;

    private ToggleGroup paymentGroup;
    private String guestName;
    private int roomId;
    private String roomName;
    private Invoice invoice;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private double totalPrice;

    @FXML
    public void initialize() {
        paymentGroup = new ToggleGroup();
        cashRadio.setToggleGroup(paymentGroup);
        creditCardRadio.setToggleGroup(paymentGroup);
        
        cashRadio.setSelected(true);
        
        paymentGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == cashRadio) {
                cashTenderedField.setDisable(false);
                cardNameField.setDisable(true);
                zipCodeField.setDisable(true);
            } else {
                cashTenderedField.setDisable(true);
                cardNameField.setDisable(false);
                zipCodeField.setDisable(false);
            }
        });
        
        payButton.setOnAction(event -> handlePayment());
    }

    public void setPaymentDetails(String guestName, int roomId, String roomName, Invoice invoice, 
                                   LocalDate checkIn, LocalDate checkOut, double totalPrice) {
        this.guestName = guestName;
        this.roomId = roomId;
        this.roomName = roomName;
        this.invoice = invoice;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPrice = totalPrice;
        
        invoiceLabel.setText("Total Amount: $" + invoice.getTotalAmount());
    }

    @FXML
    public void handlePayment() {
        BillTransaction transaction = null;
        
        if (cashRadio.isSelected()) {
            String cashText = cashTenderedField.getText().trim();
            if (cashText.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Please enter cash amount.");
                return;
            }
            
            try {
                double cashTendered = Double.parseDouble(cashText);
                transaction = new CashTransaction(totalPrice, cashTendered);
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid cash amount.");
                return;
            }
            
        } else if (creditCardRadio.isSelected()) {
            String cardName = cardNameField.getText().trim();
            String zipCode = zipCodeField.getText().trim();
            
            if (cardName.isEmpty() || zipCode.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Please enter card name and zip code.");
                return;
            }
            
            transaction = new CreditCardTransaction(totalPrice, cardName, zipCode);
        }

        if (transaction != null) {
            boolean paymentSuccess = transaction.initiateTransaction();
            
            if (paymentSuccess) {
                try {
                    int bookingId = BookingDatabase.addBooking(guestName, roomId, roomName, 
                                                               checkIn, checkOut, totalPrice, totalPrice / ChronoUnit.DAYS.between(checkIn, checkOut));
                    
                    showAlert(Alert.AlertType.INFORMATION, 
                             "Payment successful! Booking confirmed.\nBooking ID: " + bookingId);
                    
                    Stage stage = (Stage) payButton.getScene().getWindow();
                    stage.close();
                    
                } catch (SQLException ex) {
                    showAlert(Alert.AlertType.ERROR, "Payment successful but booking failed: " + ex.getMessage());
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Payment failed. Please check your payment details.");
            }
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
