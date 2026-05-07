package org.example.service;

import org.example.hotel.HouseKeeping;
import org.example.hotel.Room;
import java.util.HashMap;
import java.util.Map;

public class HouseKeepingService {
    private static Map<Integer, HouseKeeping> housekeepingRecords = new HashMap<>();
    
    public static void markRoomDirty(Room room) {
        HouseKeeping hk = housekeepingRecords.get(room.getRoomNumber());
        if (hk == null) {
            hk = new HouseKeeping(room, "Dirty");
            housekeepingRecords.put(room.getRoomNumber(), hk);
        } else {
            hk.markDirty();
        }
        System.out.println("✓ Housekeeping: Room " + room.getRoomNumber() + " marked as Dirty (Guest checked in)");
    }
    
    public static void markRoomClean(Room room) {
        HouseKeeping hk = housekeepingRecords.get(room.getRoomNumber());
        if (hk == null) {
            hk = new HouseKeeping(room, "Clean");
            housekeepingRecords.put(room.getRoomNumber(), hk);
        } else {
            hk.markClean();
        }
        System.out.println("✓ Housekeeping: Room " + room.getRoomNumber() + " marked as Clean");
    }
    
    public static String getRoomStatus(int roomNumber) {
        HouseKeeping hk = housekeepingRecords.get(roomNumber);
        return hk != null ? hk.getStatus() : "Unknown";
    }
    
    public static Map<Integer, HouseKeeping> getAllRecords() {
        return new HashMap<>(housekeepingRecords);
    }
}
