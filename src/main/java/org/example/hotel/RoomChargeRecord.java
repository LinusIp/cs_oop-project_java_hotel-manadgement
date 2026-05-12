package org.example.hotel;

public class RoomChargeRecord {
    private int chargeId;
    private String description;
    private double amount;

    public RoomChargeRecord(int chargeId, String description, double amount) {
        this.chargeId = chargeId;
        this.description = description;
        this.amount = amount;
    }

    public int getChargeId() { return chargeId; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
}
