package org.example.hotel;

public class RoomChargeRecord {
    private int id;
    private String chargeId;
    private String roomNumber;
    private String bookingId;
    private String name;
    private String description;
    private double amount;
    private String status;

    public RoomChargeRecord(int id, String chargeId, String roomNumber, String bookingId, 
                           String name, String description, double amount, String status) {
        this.id = id;
        this.chargeId = chargeId;
        this.roomNumber = roomNumber;
        this.bookingId = bookingId;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.status = status;
    }

    public int getId() { return id; }
    public String getChargeId() { return chargeId; }
    public String getRoomNumber() { return roomNumber; }
    public String getBookingId() { return bookingId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }
}
