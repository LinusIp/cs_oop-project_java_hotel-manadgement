package org.example.service;

import org.example.hotel.RoomKey;
import org.example.hotel.Room;
import java.util.HashMap;
import java.util.Map;

public class RoomKeyService {
    private static Map<Integer, RoomKey> activeKeys = new HashMap<>();
    private static int nextKeyNumber = 1000;
    
    public static RoomKey issueKey(Room room) {
        int keyNumber = nextKeyNumber++;
        RoomKey key = new RoomKey(keyNumber, room);
        activeKeys.put(keyNumber, key);
        System.out.println("✓ Room Key: Key #" + keyNumber + " issued for Room " + room.getRoomNumber());
        return key;
    }
    
    public static void deactivateKey(int keyNumber) {
        RoomKey key = activeKeys.get(keyNumber);
        if (key != null) {
            key.deactivate();
            System.out.println("✓ Room Key: Key #" + keyNumber + " deactivated");
        }
    }
    
    public static void activateKey(int keyNumber) {
        RoomKey key = activeKeys.get(keyNumber);
        if (key != null) {
            key.activate();
            System.out.println("✓ Room Key: Key #" + keyNumber + " activated");
        }
    }
    
    public static boolean isKeyActive(int keyNumber) {
        RoomKey key = activeKeys.get(keyNumber);
        return key != null && key.isActive();
    }
    
    public static Map<Integer, RoomKey> getAllKeys() {
        return new HashMap<>(activeKeys);
    }
}
