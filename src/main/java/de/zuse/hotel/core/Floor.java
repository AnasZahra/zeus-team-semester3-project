package de.zuse.hotel.core;

import java.util.ArrayList;


public class Floor
{
    private int floorNr;
    private int capacity;
    private ArrayList<Room> rooms;

    public Floor(int floorNr, int capacity)
    {
        ZuseCore.checkFatal(floorNr >= 0,"Floor Number should be >= than 0");
        ZuseCore.checkFatal(capacity > 0 , "capacity should be greater than 0");

        this.floorNr = floorNr;
        this.capacity = capacity;
        rooms = new ArrayList<Room>(capacity);
    }

    public final ArrayList<Room> getRooms()
    {
        return rooms;
    }
}
