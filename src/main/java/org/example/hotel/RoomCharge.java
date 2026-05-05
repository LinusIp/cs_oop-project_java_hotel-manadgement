package org.example.hotel;

import org.example.enums.RoomChargeStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class RoomCharge {
    private String chargeId;
    private String roomNumber;
    private String bookingId;
    private String name;
    private String description;
    private double amount;
    private LocalDateTime issuedAt;
    private RoomChargeStatus status;

    public RoomCharge(String chargeId, String roomNumber, String bookingId, String name, String description, double amount) {
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
        return UUID.randomUUID().toString();
    }

    public boolean addInvoiceItem() {
        status = RoomChargeStatus.ADDED;
        return true;
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
}
