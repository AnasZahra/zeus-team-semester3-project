package de.zuse.hotel.core;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.zuse.hotel.util.ZuseCore;

import java.util.ArrayList;

/**
 * Used for saving how many floors in hotel (floors contain how many rooms)
 */
@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT, use= JsonTypeInfo.Id.NAME)
public class HotelConfiguration
{
    private ArrayList<Floor> hotelFloors;

    public HotelConfiguration(){}

    public ArrayList<Floor> getHotelFloors()
    {
        return hotelFloors;
    }

    public void setHotelFloors(ArrayList<Floor> hotelFloors)
    {
        this.hotelFloors = hotelFloors;
    }

    public void setDefaultFloorsAndRooms()
    {
        if (hotelFloors == null)
            hotelFloors = new ArrayList<>(1);

        Floor floor = new Floor(1,10);
        floor.addRoom(new Room(floor,1,100.0));
        floor.addRoom(new Room(floor,2,100.0));
        floor.addRoom(new Room(floor,3,100.0));
        hotelFloors.add(floor);
    }

    public void addNewFloor(Floor floor)
    {
        ZuseCore.check(hotelFloors.contains(floor) == false, "Floor is already in Hotel");

        hotelFloors.add(floor);
    }

    /**
     * @param floorNr - index of floor in hotelFloors
     * @param room - room to add
     */
    public void addNewRoom(int floorNr, Room room)
    {
        ZuseCore.check(floorNr >= 0, "Floor number must be >= 0");

        hotelFloors.get(floorNr).addRoom(room);
    }


}
