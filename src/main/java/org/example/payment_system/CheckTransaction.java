package org.example.payment_system;

import org.example.enums.PaymentStatus;

public class CheckTransaction extends BillTransaction {

    private String bankName;
    private String checkNumber;

    public CheckTransaction(double amount, String bankName, String checkNumber) {
        super(amount);
        this.bankName = bankName;
        this.checkNumber = checkNumber;
    }


    public boolean initiateTransaction() {
        if (bankName != null && checkNumber != null) {
            setStatus(PaymentStatus.COMPLETED);
            return true;
        }
        setStatus(PaymentStatus.FAILED);
        return false;
    }
}
