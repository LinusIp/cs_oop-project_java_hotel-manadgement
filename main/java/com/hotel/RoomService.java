package com.hotel;

import java.time.LocalDateTime;

public class RoomService {
    protected final String name;
    protected final String description;
    protected final double price;
    protected final boolean chargeable;
    protected final LocalDateTime requestTime;
    public RoomService(String name, String description, double price) {
        this(name, description, price, true, LocalDateTime.now());
    }

    public RoomService(String name, String description, double price, boolean chargeable, LocalDateTime requestTime) {
        validateText(name, "Room service name is required");
        validateText(description, "Room service description is required");
        validatePrice(price);
        if(requestTime == null) {
            throw new IllegalArgumentException("Request time is required");
        }

        this.name = name;
        this.description = description;
        this.price = price;
        this.chargeable = chargeable;
        this.requestTime = requestTime;
    }

    public RoomCharge toRoomCharge(String roomNumber, String bookingId) {
        return new RoomCharge(RoomCharge.generateChargeId(), roomNumber, bookingId, "Room Service: " + name, description, getChargeAmount());
    }

    public double getChargeAmount() {
        return chargeable ? price : 0.0;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public boolean isChargeable() {
        return chargeable;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    protected static void validateText(String value, String message) {
        if(value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    protected static void validatePrice(double price) {
        if(price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }
}