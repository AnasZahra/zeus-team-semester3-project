package de.zuse.hotel.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.zuse.hotel.util.ZuseCore;

import java.util.ArrayList;

@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT, use= JsonTypeInfo.Id.NAME)
public class Floor
{
    private int floorNr;
    private int capacity;

    private ArrayList<Room> rooms;

    public Floor(int floorNr, int capacity)
    {
        ZuseCore.check(floorNr >= 0,"Floor Number should be >= than 0");
        ZuseCore.check(capacity > 0 , "capacity should be greater than 0");

        this.floorNr = floorNr;
        this.capacity = capacity;
        rooms = new ArrayList<Room>(capacity);
    }

    public Floor() {}

    public void addRoom(Room room)
    {
        ZuseCore.check(capacity >= rooms.size(), "Floor can not take any more rooms");
        ZuseCore.check(room.getFloorNr() == floorNr, "Floor Number must be the same!!");
        ZuseCore.check(!rooms.contains(room), "Floor has same Room!!" + room.getRoomNr());

        rooms.add(room);
    }

    public int getFloorNr()
    {
        return floorNr;
    }

    public int getCapacity()
    {
        return capacity;
    }

    public final ArrayList<Room> getRooms()
    {
        return rooms;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this)
            return true;

        if (!(obj instanceof Room))
            return false;

        return ((Floor) obj).getFloorNr() == this.floorNr;
    }

}
