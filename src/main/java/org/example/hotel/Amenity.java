package org.example.hotel;

import static org.example.hotel.RoomService.validatePrice;

public class Amenity {
    private final String name;
    private final String description;
    private final double price;
    public Amenity(String name, String description) {
        this(name, description, 0.0);
    }

    public Amenity(String name, String description, double price) {
        validateText(name, "Amenity name is required");
        validateText(description, "Amenity description is required");
        validatePrice(price);

        this.name = name;
        this.description = description;
        this.price = price;
    }

    public RoomCharge toRoomCharge(String roomNumber, String bookingId) {
        return new RoomCharge(RoomCharge.generateChargeId(), roomNumber, bookingId, "Amenity: " + name, description, price);
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

    private static void validateText(String value, String message) {
        if(value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }
}
