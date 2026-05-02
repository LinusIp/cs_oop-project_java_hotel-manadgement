package org.example;

import java.time.LocalDateTime;

public class RoomHouseKeeping {
    private String description;
    private LocalDateTime startDateTime;
    private int duration;

    public RoomHouseKeeping(String description, LocalDateTime startDateTime, int duration) {
        this.description = description;
        this.startDateTime = startDateTime;
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public int getDuratiion() {
        return duration;
    }
}
