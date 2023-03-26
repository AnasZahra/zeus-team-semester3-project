package de.zuse.hotel.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.itextpdf.text.RomanList;
import de.zuse.hotel.util.ZuseCore;

import java.util.ArrayList;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class Room
{
    private int roomNr;
    private int floorNr;
    private double price;

    private RoomSpecification.Types roomType;

    @JsonIgnore
    private transient RoomSpecification.Status status; // from database
    @JsonIgnore
    private transient ArrayList<Booking> bookings;// from database

    private static final int DEFAULT_BOOKING_COUNT = 5;

    // Without a default constructor, Jackson will throw an exception
    public Room(){}

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

        Room c = (Room) obj;

        return ((Room) obj).getRoomNr() == this.getRoomNr();
    }

    public Room(Floor floor, int roomNr, double price)
    {
        ZuseCore.check(roomNr >= 0, "roomNr can not be null");
        ZuseCore.check(price > 0, "price can not be null");

        this.floorNr = floor.getFloorNr();
        this.roomNr = roomNr;
        this.price = price;
    }

    public int getRoomNr()
    {
        return roomNr;
    }

    public void setRoomNr(int roomNr)
    {
        ZuseCore.check(roomNr >= 0, "roomNr can not be null");
        this.roomNr = roomNr;
    }

    public int getFloorNr()
    {
        return floorNr;
    }

    public void setFloorNr(int floorNr)
    {
        ZuseCore.check(floorNr >= 0, "floorNr can not be null");
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

    public void setBookings(ArrayList<Booking> bookings)
    {
        this.bookings = bookings;
    }

    @Override
    public String toString()
    {
        return "Room{" +
                "roomNr=" + roomNr +
                ", floorNr=" + floorNr +
                ", price=" + price +
                ", roomType=" + roomType +
                ", status=" + status +
                ", bookings=" + bookings +
                '}';
    }



}
