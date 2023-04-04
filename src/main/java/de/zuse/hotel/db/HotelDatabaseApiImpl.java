package de.zuse.hotel.db;

import de.zuse.hotel.core.Booking;
import de.zuse.hotel.core.Person;
import de.zuse.hotel.util.ZuseCore;

import java.time.LocalDate;
import java.util.List;

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
        PersonConnecter personConnecter = new PersonConnecter();
        personConnecter.dbCreate(guest);
        return true; // to do is that dose the Hibernate return folse and t
    }

    /**
     *  Make sure by deleting a Guest to delete/cancel all his Booking(s) first!!! other way it will fail
     * @param guestId
     * @return
     */
    @Override
    public boolean removeGuest(int guestId)
    {
        PersonConnecter personConnecter = new PersonConnecter();
        personConnecter.dbRemoveById(guestId);
        return true;
    }

    @Override
    public boolean updateGuest(Person updatedGuest)
    {
        PersonConnecter personConnecter = new PersonConnecter();
        personConnecter.dbUpdate(updatedGuest);
        // hir is the ID needed
        return true;
    }

    @Override
    public Person getGuest(int guestID)
    {
        PersonConnecter personConnecter = new PersonConnecter();
        return personConnecter.dbsearchById(guestID);
    }

    @Override
    public List<Person> getAllGuest()
    {
        PersonConnecter personConnecter = new PersonConnecter();
        personConnecter.dbsearchAll();
        return (List<Person>) personConnecter.dbsearchAll();
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
    public List<Booking> getBookingsByFilter(BookingSearchFilter bookingSearchFilter)
    {
        ZuseCore.coreAssert(bookingSearchFilter != null, "Not valid bookingSearchFilter Object");

        BookingConnector bookingConnector = new BookingConnector();
        return bookingConnector.dbSerschforanythinhg(bookingSearchFilter);
    }

    @Override
    public List<Booking> getAllBookingBetweenStartAndEnd(LocalDate start, LocalDate end)
    {
        BookingConnector bookingConnector = new BookingConnector();

        return bookingConnector.dbSearchBookingBetween(start,end);
    }

    @Override
    public List<Person> getPersonsByFilter(PersonSearchFilter personSearchFilter)
    {

        PersonConnecter personConnector = new PersonConnecter();
        return personConnector.dbSerschforanythinhg(personSearchFilter);
    }

    @Override
    public void shutdown() throws Exception
    {
        JDBCConnecter.shutdown();
    }
}
