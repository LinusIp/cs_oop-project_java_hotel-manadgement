package org.example.hotel;

import org.example.enums.RoomStatus;
import org.example.enums.RoomStyle;

import java.util.List;

public class Room {

    private String hotel;
    private int roomNumber;
    private String roomStyle;
    private RoomStyle style;
    private boolean available;
    private List<String> amenities;
    private int bookingPrice;
    private RoomStatus roomStatus;

    public Room(int roomNumber, String roomStyle, List<String> amenities) {
        this.roomNumber = roomNumber;
        this.roomStyle = roomStyle;
        this.amenities = amenities;
        this.available = true;
    }

    public Room(String hotel, int roomNumber, RoomStyle roomStyle, int bookingPrice, RoomStatus roomStatus) {
        this.hotel = hotel;
        this.roomNumber = roomNumber;
        this.style = roomStyle;
        this.roomStyle = roomStyle.toString();
        this.bookingPrice = bookingPrice;
        this.roomStatus = roomStatus;
        this.available = true;
    }

    public String getHotel() {
        return hotel;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomStyle() {
        return roomStyle;
    }

    public RoomStyle getStyle() {
        return style;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailability(boolean available) {
        this.available = available;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public int getBookingPrice() {
        return bookingPrice;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String toString() {
        return "Room " + roomNumber + " (" + roomStyle + ")";
    }
}
