package org.example;

import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private List<InvoiceItem> items;
    private double totalAmount;

    public Invoice() {
        this.items = new ArrayList<>();
        this.totalAmount = 0.0;
    }

    public void addItem(InvoiceItem item) {
        items.add(item);
        totalAmount += item.getAmount();
    }

    public void removeItem(InvoiceItem item) {
        if (items.remove(item)) {
            totalAmount -= item.getAmount();
        }
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public boolean createBill() {
        return !items.isEmpty();
    }
}
