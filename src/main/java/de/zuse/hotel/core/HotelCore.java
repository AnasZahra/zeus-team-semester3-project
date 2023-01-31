package de.zuse.hotel.core;

public class HotelCore implements HotelCoreApi
{
    private static HotelCore instance = null;

    public static HotelCore get()
    {
        if (instance == null)
        {
            instance = new HotelCore();
        }

        return instance;
    }

    @Override
    public boolean addGuest(Guest guest)
    {
        return false;
    }

    @Override
    public boolean removeGuest(Guest guest)
    {
        return false;
    }

    @Override
    public boolean addBooking(Booking booking)
    {
        return false;
    }

    @Override
    public boolean removeBooking(Booking booking)
    {
        return false;
    }

    @Override
    public Booking getBooking(int bookingID)
    {
        return null;
    }

    @Override
    public Guest getGuest(int personID)
    {
        return null;
    }

    @Override
    public void printBookingAsPDF(int bookingID)
    {

    }

    @Override
    public boolean updateGuest(Guest guest)
    {
        return false;
    }

    @Override
    public boolean updateBooking(Booking booking)
    {
        return false;
    }

    @Override
    public int generateBookingID()
    {
        return 0;
    }

    @Override
    public int generatePersonID()
    {
        return 0;
    }
}
