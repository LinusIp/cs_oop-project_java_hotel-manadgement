package org.example.service;

import org.example.UI.Search;
import org.example.enums.RoomStyle;
import org.example.enums.RoomStatus;
import org.example.hotel.Room;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SearchService implements Search {
    
    private List<Room> availableRooms;

    public SearchService() {
        this.availableRooms = new ArrayList<>();
        initializeSampleRooms();
    }

    private void initializeSampleRooms() {
        // Get rooms from HotelManager (uses Hotel, HotelLocation, Room classes)
        availableRooms = new ArrayList<>(HotelManager.getHotel().getAllRooms());
    }

    @Override
    public boolean searchRoom(RoomStyle roomStyle, LocalDate startDate, int durationInDays) {
        List<Room> matchingRooms = new ArrayList<>();
        
        for (Room room : availableRooms) {
            if (room.getStyle() == roomStyle && room.getRoomStatus() == RoomStatus.AVIABLE) {
                matchingRooms.add(room);
            }
        }
        
        availableRooms = matchingRooms;
        return !matchingRooms.isEmpty();
    }

    public List<Room> getAvailableRooms() {
        return new ArrayList<>(availableRooms);
    }
}
