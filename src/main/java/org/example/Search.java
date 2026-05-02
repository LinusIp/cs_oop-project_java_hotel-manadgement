package org.example;

import java.time.LocalDate;

public interface Search {
    public boolean searchRoom(RoomStyle roomStyle, LocalDate startDate, int durationInDays);
}
