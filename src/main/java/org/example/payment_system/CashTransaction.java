package org.example.payment_system;

import org.example.enums.PaymentStatus;

public class CashTransaction extends BillTransaction {

    private double cashTendered;

    public CashTransaction(double amount, double cashTendered) {
        super(amount);
        this.cashTendered = cashTendered;
    }

    public boolean initiateTransaction() {
        if (cashTendered >= amount) {
            setStatus(PaymentStatus.COMPLETED);
            return true;
        }
        setStatus(PaymentStatus.FAILED);
        return false;
    }

    public double getChange() {
        if (cashTendered >= amount) {
            return cashTendered - amount;
        }
        return 0;
    }
}