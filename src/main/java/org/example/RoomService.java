package org.example;

import java.time.LocalDateTime;

public abstract class RoomService {
    protected String name;
    protected String description;
    protected double price;
    protected boolean chargeable;
    protected LocalDateTime requestTime;

    public RoomService(String name, String description, double price) {
        this(name, description, price, true, LocalDateTime.now());
    }

    public RoomService(String name, String description, double price, boolean chargeable, LocalDateTime requestTime) {
        validateText(name, "Room service name is required");
        validateText(description, "Room service description is required");
        validatePrice(price);
        this.name = name;
        this.description = description;
        this.price = price;
        this.chargeable = chargeable;
        this.requestTime = requestTime;
    }

    public abstract RoomCharge toRoomCharge(String roomNumber, String bookingId);

    public double getChargeAmount() {
        return chargeable ? price : 0.0;
    }

    public static void validatePrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    private static void validateText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }
}
