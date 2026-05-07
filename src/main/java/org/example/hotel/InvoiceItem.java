package org.example.hotel;

public class InvoiceItem {
    private String description;
    private double amount;

    public InvoiceItem(double amount) {
        this.amount = amount;
    }

    public InvoiceItem(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public void updateAmount(double newAmount) {
        this.amount = newAmount;
    }
}
