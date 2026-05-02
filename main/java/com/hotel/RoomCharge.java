package com.hotel;

import java.time.LocalDateTime;

public class RoomCharge {
    private static int nextChargeNumber = 1;
    private final String chargeId;
    private final String roomNumber;
    private final String bookingId;
    private String name;
    private String description;
    private double amount;
    private final LocalDateTime issuedAt;
    private RoomChargeStatus status;

    public RoomCharge(String chargeId, String roomNumber, String bookingId, String name, String description, double amount) {
        validateText(chargeId, "Charge ID is required");
        validateText(roomNumber, "Room number is required");
        validateText(bookingId, "Booking ID is required");
        validateText(name, "Charge name is required");
        validateText(description, "Charge description is required");
        validateAmount(amount);

        this.chargeId = chargeId;
        this.roomNumber = roomNumber;
        this.bookingId = bookingId;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.issuedAt = LocalDateTime.now();
        this.status = RoomChargeStatus.PENDING;
    }

    public static String generateChargeId() {
        String id = "CH-" + nextChargeNumber;
        nextChargeNumber++;
        return id;
    }

    public String getChargeId() {
        return chargeId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public RoomChargeStatus getStatus() {
        return status;
    }

    public void updateCharge(String name, String description, double amount) {
        if(status == RoomChargeStatus.CANCELLED) {
            throw new IllegalStateException("Cannot update cancelled room charge");
        }

        if(status == RoomChargeStatus.ADDED_TO_INVOICE) {
            throw new IllegalStateException("Cannot update room charge after it has been added to invoice");
        }

        validateText(name, "Charge name is required");
        validateText(description, "Charge description is required");
        validateAmount(amount);

        this.name = name;
        this.description = description;
        this.amount = amount;
    }

    public boolean addInvoiceItem() {
        if(status == RoomChargeStatus.CANCELLED) {
            return false;
        }

        status = RoomChargeStatus.ADDED_TO_INVOICE;
        return true;
    }

    public boolean markAsAddedToInvoice() {
        return addInvoiceItem();
    }

    public boolean cancel() {
        if (status == RoomChargeStatus.ADDED_TO_INVOICE) {
            return false;
        }

        status = RoomChargeStatus.CANCELLED;
        return true;
    }

    private static void validateText(String value, String message) {
        if(value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    private static void validateAmount(double amount) {
        if(amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

    @Override
    public String toString() {
        return "RoomCharge{" + "chargeId='" + chargeId + '\'' + ", roomNumber='" + roomNumber + '\'' + ", bookingId='" + bookingId + '\'' + ", name='" + name + '\'' + ", description='" + description + '\'' + ", amount=" + amount + ", issuedAt=" + issuedAt + ", status=" + status + '}';
    }
}
