package org.example.payment_system;

import org.example.enums.PaymentStatus;

import java.util.Date;

public abstract class BillTransaction {
    protected Date creationDate;
    protected double amount;
    protected PaymentStatus status;

    public BillTransaction(double amount) {
        this.creationDate = new Date();
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
    }

    public abstract boolean initiateTransaction();

    public double getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    protected void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
