package org.example;

public class InvoiceItem {
    private double amount;

    public InvoiceItem(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void updateAmount(double newAmount) {
        this.amount = newAmount;
    }
}
