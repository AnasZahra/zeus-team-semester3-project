package de.zuse.hotel.db;

import de.zuse.hotel.core.Booking;
import de.zuse.hotel.core.Person;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class HotelDatabaseApiImpl implements HotelDatabaseApi
{
    public HotelDatabaseApiImpl()
    {
        try
        {
            JDBCConnecter.getConnection();
            JDBCConnecter.getEntityManagerFactory();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public boolean addGuest(Person guest)
    {
        PresonConnecter presonConnecter = new PresonConnecter();
        presonConnecter.dbCreate(guest);
        return true; // to do is that dose the Hibernate return folse and t
    }

    @Override
    public boolean removeGuest(int guestId)
    {
        PresonConnecter presonConnecter = new PresonConnecter();
        presonConnecter.dbRemoveById(guestId);
        return true;
    }

    @Override
    public boolean updateGuest(Person updatedGuest)
    {
        PresonConnecter presonConnecter = new PresonConnecter();
        presonConnecter.dbUpdate(updatedGuest);
        // hir is the ID needed
        return true;
    }

    @Override
    public Person getGuest(int guestID)
    {
        PresonConnecter presonConnecter = new PresonConnecter();
        return presonConnecter.dbsearchById(guestID);
    }

    @Override
    public List<Person> getAllGuest()
    {
        PresonConnecter presonConnecter = new PresonConnecter();
        presonConnecter.dbsearchAll();
        return (List<Person>) presonConnecter.dbsearchAll();
    }

    @Override
    public boolean addBooking(Booking booking)
    {
        BookingConnector bookingConnector = new BookingConnector();
        bookingConnector.dbCreate(booking);
        return true;
    }

    @Override
    public boolean removeBooking(int bookingID)
    {
        BookingConnector bookingConnector = new BookingConnector();
        bookingConnector.dbRemoveById(bookingID);
        return true;
    }

    @Override
    public boolean updateBooking(Booking updatedBooking)
    {
        BookingConnector bookingConnector = new BookingConnector();
        bookingConnector.dbUpdate(updatedBooking);
        return true;
    }

    @Override
    public Booking getBooking(int bookingID)
    {
        BookingConnector bookingConnector = new BookingConnector();
        return bookingConnector.dbsearchById(bookingID);
    }

    @Override
    public List<Booking> getAllBooking()
    {
        BookingConnector bookingConnector = new BookingConnector();
        bookingConnector.dbsearchAll();
        return (List<Booking>) bookingConnector.dbsearchAll();
    }

    @Override
    public void shutdown() throws Exception
    {
        JDBCConnecter.shutdown();
    }
}
