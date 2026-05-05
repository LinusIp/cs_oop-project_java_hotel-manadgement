package org.example.hotel;

import org.example.enums.BookingStatus;

import java.util.Date;

public class RoomBooking {
    private String reservationNumber;
    private Date startDate;
    private int durationInDays;
    private BookingStatus status;
    private Date checkin;
    private Date checkout;


    public static RoomBooking fetchDetails(String reservationNumber) {

        return new RoomBooking();
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public Date getCheckin() {
        return checkin;
    }

    public Date getCheckout() {
        return checkout;
    }
}
