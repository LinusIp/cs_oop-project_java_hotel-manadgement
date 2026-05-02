package com.hotel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private final String serverId;
    private final String name;
    private final Map<String, RoomCharge> roomCharges;
    public Server(String serverId, String name) {
        validateText(serverId, "Server ID is required");
        validateText(name, "Server name is required");

        this.serverId = serverId;
        this.name = name;
        this.roomCharges = new HashMap<>();
    }

    public RoomCharge addRoomCharge(String roomNumber, String bookingId, String chargeName, String description, double amount) {
        validateRoomAndBooking(roomNumber, bookingId);
        validateText(chargeName, "Charge name is required");
        validateText(description, "Charge description is required");
        validateAmount(amount);

        RoomCharge charge = new RoomCharge(RoomCharge.generateChargeId(), roomNumber, bookingId, chargeName, description, amount);
        roomCharges.put(charge.getChargeId(), charge);
        return charge;
    }

    public boolean addRoomCharge(RoomCharge charge) {
        if(charge == null) {
            return false;
        }

        roomCharges.put(charge.getChargeId(), charge);
        return true;
    }

    public RoomCharge addRoomServiceItem(String roomNumber, String bookingId, RoomService item) {
        validateRoomAndBooking(roomNumber, bookingId);
        validateServiceItem(item);
        RoomCharge charge = item.toRoomCharge(roomNumber, bookingId);
        roomCharges.put(charge.getChargeId(), charge);
        return charge;
    }

    public RoomCharge addKitchenServiceItem(String roomNumber, String bookingId, KitchenService item) {
        return addRoomServiceItem(roomNumber, bookingId, item);
    }

    public RoomCharge addAmenityRequest(String roomNumber, String bookingId, Amenity amenity) {
        validateRoomAndBooking(roomNumber, bookingId);
        if(amenity == null) {
            throw new IllegalArgumentException("Amenity is required");
        }

        RoomCharge charge = amenity.toRoomCharge(roomNumber, bookingId);
        roomCharges.put(charge.getChargeId(), charge);
        return charge;
    }

    public boolean modifyRoomCharge(String chargeId, String newName, String newDescription, double newAmount) {
        validateText(chargeId, "Charge ID is required");
        validateText(newName, "New charge name is required");
        validateText(newDescription, "New charge description is required");
        validateAmount(newAmount);

        RoomCharge charge = roomCharges.get(chargeId);
        if(charge == null) {
            return false;
        }

        charge.updateCharge(newName, newDescription, newAmount);
        return true;
    }

    public boolean cancelRoomCharge(String chargeId) {
        validateText(chargeId, "Charge ID is required");
        RoomCharge charge = roomCharges.get(chargeId);
        if(charge == null) {
            return false;
        }

        return charge.cancel();
    }

    public List<RoomCharge> getChargesByRoom(String roomNumber) {
        validateText(roomNumber, "Room number is required");

        List<RoomCharge> result = new ArrayList<>();

        for(RoomCharge charge : roomCharges.values()) {
            if(charge.getRoomNumber().equals(roomNumber)) {
                result.add(charge);
            }
        }

        return result;
    }

    public List<RoomCharge> getChargesByBooking(String bookingId) {
        validateText(bookingId, "Booking ID is required");

        List<RoomCharge> result = new ArrayList<>();

        for(RoomCharge charge : roomCharges.values()) {
            if(charge.getBookingId().equals(bookingId)) {
                result.add(charge);
            }
        }

        return result;
    }

    public boolean addChargeToInvoice(String chargeId) {
        validateText(chargeId, "Charge ID is required");

        RoomCharge charge = roomCharges.get(chargeId);

        if(charge == null) {
            return false;
        }

        return charge.addInvoiceItem();
    }

    public List<RoomCharge> getAllRoomCharges() {
        return new ArrayList<>(roomCharges.values());
    }
    private void validateRoomAndBooking(String roomNumber, String bookingId) {
        validateText(roomNumber, "Room number is required");
        validateText(bookingId, "Booking ID is required");
    }

    private void validateServiceItem(RoomService item) {
        if(item == null) {
            throw new IllegalArgumentException("Room service item is required");
        }
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

    public String getServerId() {
        return serverId;
    }

    public String getName() {
        return name;
    }
}