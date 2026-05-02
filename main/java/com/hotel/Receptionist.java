package com.hotel;
import java.time.LocalDate;

public class Receptionist extends Person implements Search {
    public Receptionist(String name, Address address, String email, String phone, Account account){
        super(name, address, email, phone, AccountType.RECEPTIONIST, account);
    }

    @Override
    public boolean searchRoom(RoomStyle roomStyle, LocalDate startDate, int durationInDays) {
        if(roomStyle == null || startDate == null || durationInDays <= 0){
            return false;
        }
        return true;
    }

    public boolean createBooking(RoomBooking booking){
        if(booking == null){
            return false;
        }
            if(booking.getReservationNumber() == null ){ // <--|| booking.getReservationNumber().isEmpty()
            return false;
        }
        if(booking.getStartDate() == null || booking.getDurationInDays() <= 0){
            return false;
        }
        return true;
    }
}
