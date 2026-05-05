package org.example.hotel;

import java.time.LocalDateTime;

public class KitchenService extends RoomService {

    public KitchenService(String name, String description, double price) {
        super(name, description, price);
    }

    public KitchenService(String name, String description, double price, boolean chargeable, LocalDateTime requestTime) {
        super(name, description, price, chargeable, requestTime);
    }

    @Override
    public RoomCharge toRoomCharge(String roomNumber, String bookingId) {
        return new RoomCharge(RoomCharge.generateChargeId(), roomNumber, bookingId, "Kitchen Service: " + name, description, getChargeAmount());
    }
}
