package org.example.hotel;

import org.example.service.HotelManager;
import org.example.service.HouseKeepingService;
import org.example.service.RoomKeyService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MockBookingDatabase {
    
    private static int nextBookingId = 1;
    private static Map<Integer, BookingInfo> bookings = new HashMap<>();
    
    public static int addBooking(String guestName, int roomId, String roomName,
                                 LocalDate checkIn, LocalDate checkOut,
                                 double totalPrice, double roomPrice) {
        return addBooking(guestName, roomId, roomName, checkIn, checkOut, totalPrice, roomPrice, null);
    }
    
    public static int addBooking(String guestName, int roomId, String roomName,
                                 LocalDate checkIn, LocalDate checkOut,
                                 double totalPrice, double roomPrice, String username) {
        int bookingId = nextBookingId++;
        
        BookingInfo booking = new BookingInfo();
        booking.bookingId = bookingId;
        booking.guestName = guestName;
        booking.roomId = roomId;
        booking.roomName = roomName;
        booking.checkIn = checkIn;
        booking.checkOut = checkOut;
        booking.totalPrice = totalPrice;
        booking.username = username;
        
        bookings.put(bookingId, booking);
        
        System.out.println("Mock: Booking created - ID: " + bookingId + 
                         ", Guest: " + guestName + 
                         ", Room: " + roomName + 
                         ", Total: $" + totalPrice +
                         (username != null ? ", User: " + username : ""));
        
        // INTEGRATION: Use Asadbek's classes
        Room room = HotelManager.getRoomByNumber(roomId);
        if (room != null) {
            // Mark room as dirty (guest will use it)
            HouseKeepingService.markRoomDirty(room);
            
            // Issue room key to guest
            RoomKey key = RoomKeyService.issueKey(room);
            booking.roomKeyNumber = key.getRoom().getRoomNumber();
        }
        
        return bookingId;
    }
    
    public static List<BookingInfo> getAllBookings() {
        return new ArrayList<>(bookings.values());
    }
    
    public static List<BookingInfo> getBookingsByUsername(String username) {
        return bookings.values().stream()
                .filter(b -> username.equals(b.username))
                .collect(Collectors.toList());
    }
    
    public static class BookingInfo {
        public int bookingId;
        public String guestName;
        public int roomId;
        public String roomName;
        public LocalDate checkIn;
        public LocalDate checkOut;
        public double totalPrice;
        public String username;
        public int roomKeyNumber;
    }
}
