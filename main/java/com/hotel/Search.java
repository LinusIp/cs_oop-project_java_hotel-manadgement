package com.hotel;

import java.time.LocalDate;

public interface Search {
    boolean searchRoom(RoomStyle style, LocalDate date, int days);
}