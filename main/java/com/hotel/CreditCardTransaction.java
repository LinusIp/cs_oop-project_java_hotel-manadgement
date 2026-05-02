package com.hotel;
public class CreditCardTransaction extends BillTransaction {

    private String nameOnCard;
    private String zipCode;

    public CreditCardTransaction(double amount, String nameOnCard, String zipCode) {
        super(amount);
        this.nameOnCard = nameOnCard;
        this.zipCode = zipCode;
    }


    public boolean initiateTransaction() {
        // Simulate payment processing
        if (nameOnCard != null && zipCode != null) {
            setStatus(PaymentStatus.COMPLETED);
            return true;
        }
        setStatus(PaymentStatus.FAILED);
        return false;
    }
}
