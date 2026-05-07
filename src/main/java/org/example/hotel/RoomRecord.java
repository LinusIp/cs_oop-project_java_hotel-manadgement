package org.example.hotel;

/**
 * Simple class to hold room data from database
 */
public class RoomRecord {
    private int roomId;
    private String roomName;
    private double price;
    private String status;

    public RoomRecord(int roomId, String roomName, double price, String status) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.price = price;
        this.status = status;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
