package org.example.registration;

import org.example.enums.AccountType;
import org.example.hotel.Address;
import org.example.hotel.RoomCharge;

public class Server extends Person {
    public Server(String name, Address address, String email, String phone, Account account) {
        super(name, address, email, phone, AccountType.SERVER, account);
    }

    public boolean addRoomCharge(RoomCharge roomCharge) {
        return roomCharge != null && roomCharge.addInvoiceItem();
    }
}
