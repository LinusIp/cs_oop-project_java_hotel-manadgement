package com.hotel;

import java.time.LocalDateTime;

public class HouseKeeper extends Person{
    public HouseKeeper(String name, Address address, String email, String phone, Account account){
        super(name, address, email, phone, AccountType.HOUSEKEEPER, account);
    }

    public boolean assignToRoom(RoomHouseKeeping houseKeeping){
        if(houseKeeping == null){
            return false;
        }
        if(houseKeeping.getDescription() == null ){ // <--|| houseKeeping.getDescription().isEmpty()
            return false;
        }
        if(houseKeeping.getStartDateTime() == null || houseKeeping.getDuratiion() <= 0){
            return false;
        }
        return true;
    }

    public boolean assignToRoom(String description, LocalDateTime start, int durationMinutes){
        RoomHouseKeeping roomHouseKeeping = new RoomHouseKeeping(description, start, durationMinutes);
        return assignToRoom(roomHouseKeeping);
    }
}
