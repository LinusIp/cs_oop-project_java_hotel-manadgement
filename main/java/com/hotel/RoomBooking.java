package com.hotel;

import java.util.Date;

public class RoomBooking {
    String reservationNumber;
    Date startDate;
    int durationInDays;
    BookingStatus status;

    public Object getStartDate() {
        return startDate;
    }

    public Object getReservationNumber() {
        return reservationNumber;

    }

    public int getDurationInDays() {
        return durationInDays;
    }

    }


