package org.example.hotel;

import java.time.LocalDate;

public class BookingRecord {
    private int bookingId;
    private String guestName;
    private int roomId;
    private String roomName;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private double totalPrice;

    public BookingRecord(int bookingId, String guestName, int roomId, LocalDate checkIn, LocalDate checkOut, double totalPrice) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPrice = totalPrice;
    }

    public BookingRecord(int bookingId, String guestName, int roomId, String roomName, LocalDate checkIn, LocalDate checkOut, double totalPrice) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomId = roomId;
        this.roomName = roomName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPrice = totalPrice;
    }

    public int getBookingId() { return bookingId; }
    public String getGuestName() { return guestName; }
    public int getRoomId() { return roomId; }
    public String getRoomName() { return roomName; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public double getTotalPrice() { return totalPrice; }
}
