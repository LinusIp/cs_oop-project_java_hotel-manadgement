package org.example.UI;

import org.example.hotel.InvoiceItem;


import org.example.enums.RoomStyle;

import java.time.LocalDate;

public interface Search {
    public boolean searchRoom(RoomStyle roomStyle, LocalDate startDate, int durationInDays);
}
