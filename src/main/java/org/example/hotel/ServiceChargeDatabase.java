package org.example.hotel;

import org.example.hotel.RoomCharge;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceChargeDatabase {
    
    public static List<RoomChargeRecord> getAllCharges() throws SQLException {
        return new ArrayList<>();
    }

    public static void addRoomCharge(RoomCharge charge) throws SQLException {
        // Stub method - add to database if needed
    }

    public static double getTotalCharges() throws SQLException {
        return 0.0;
    }
}
