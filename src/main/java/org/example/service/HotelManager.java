package org.example.service;

import org.example.hotel.Hotel;
import org.example.hotel.HotelLocation;
import org.example.hotel.Room;
import org.example.enums.RoomStyle;
import org.example.enums.RoomStatus;

public class HotelManager {
    private static Hotel hotel;
    
    static {
        // Initialize hotel with location
        HotelLocation location = new HotelLocation("Tashkent", "New Uzbekistan University Campus");
        hotel = new Hotel("NewUU Smart Hotel", location);
        
        // Add all rooms to hotel
        hotel.addRoom(new Room("NewUU Hotel", 1, RoomStyle.FAMILYSUITE, 120, RoomStatus.AVIABLE));
        hotel.addRoom(new Room("NewUU Hotel", 2, RoomStyle.DELUXE, 95, RoomStatus.AVIABLE));
        hotel.addRoom(new Room("NewUU Hotel", 3, RoomStyle.STANDARD, 45, RoomStatus.AVIABLE));
    }
    
    public static Hotel getHotel() {
        return hotel;
    }
    
    public static String getHotelInfo() {
        return hotel.getName() + " - " + hotel.getLocation().toString();
    }
    
    public static Room getRoomByNumber(int roomNumber) {
        for (Room room : hotel.getAllRooms()) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }
}
