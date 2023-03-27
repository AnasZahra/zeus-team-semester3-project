package de.zuse.hotel.db;

import de.zuse.hotel.core.Booking;
import de.zuse.hotel.core.Person;
import java.util.List;

public interface HotelDatabaseApi
{
    //Guest
    public boolean addGuest(Person guest);
    public boolean removeGuest(int guestId);
    public boolean updateGuest( Person updatedGuest); //should we use the Id or not
    public Person getGuest(int guestID);
    public List<Person> getAllGuest();

    //Booking
    public boolean addBooking(Booking booking);
    public boolean removeBooking(int bookingID);
    public boolean updateBooking(Booking updatedBooking);
    public Booking getBooking(int bookingID);
    public List<Booking> getAllBooking();

    /**
     * Optional
     */
    public void shutdown();
}
