package de.zuse.hotel.db;

import de.zuse.hotel.core.Booking;
import de.zuse.hotel.core.Guest;

import java.util.List;

public class HotelDatabaseApiImpl implements HotelDatabaseApi
{
    @Override
    public boolean addGuest(Guest guest)
    {
        return false;
    }

    @Override
    public boolean removeGuest(int guestId)
    {
        return false;
    }

    @Override
    public boolean updateGuest(int guestID, Guest updatedGuest)
    {
        return false;
    }

    @Override
    public Guest getGuest(int guestID)
    {
        return null;
    }

    @Override
    public List<Guest> getAllGuest()
    {
        return null;
    }

    @Override
    public boolean addBooking(Booking booking)
    {
        return false;
    }

    @Override
    public boolean removeBooking(int bookingID)
    {
        return false;
    }

    @Override
    public boolean updateBooking(int bookingID, Booking updatedBooking)
    {
        return false;
    }

    @Override
    public Booking getBooking(int bookingID)
    {
        return null;
    }

    @Override
    public List<Booking> getAllBooking()
    {
        return null;
    }

    @Override
    public void shutdown()
    {

    }
}
