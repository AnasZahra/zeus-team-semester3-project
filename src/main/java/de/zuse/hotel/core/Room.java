package de.zuse.hotel.core;

import java.util.ArrayList;

public class Room
{
    private int roomNr;
    private int floorNr;
    private double price;

    private RoomSpecification.Types roomType;
    private RoomSpecification.Status status;
    private ArrayList<Booking> bookings;
    private static final int DEFAULT_BOOKING_COUNT = 5;

    public Room(int roomNr , int floorNr, double price)
    {
        ZuseCore.checkFatal(roomNr >= 0, "roomNr can not be null");
        ZuseCore.checkFatal(floorNr >= 0, "floorNr can not be null");
        ZuseCore.checkFatal(price > 0, "price can not be null");

        this.roomNr = roomNr;
        this.floorNr = floorNr;
        this.price = price;
    }

    public int getRoomNr()
    {
        return roomNr;
    }

    public void setRoomNr(int roomNr)
    {
        ZuseCore.checkFatal(roomNr >= 0, "roomNr can not be null");
        this.roomNr = roomNr;
    }

    public int getFloorNr()
    {
        return floorNr;
    }

    public void setFloorNr(int floorNr)
    {
        ZuseCore.checkFatal(floorNr >= 0, "floorNr can not be null");
        this.floorNr = floorNr;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public RoomSpecification.Types getRoomType()
    {
        return roomType;
    }

    public void setRoomType(RoomSpecification.Types roomType)
    {
        this.roomType = roomType;
    }

    public RoomSpecification.Status getStatus()
    {
        return status;
    }

    public void setStatus(RoomSpecification.Status status)
    {
        this.status = status;
    }

    public final ArrayList<Booking> getBookings()
    {
        return bookings;
    }

}
