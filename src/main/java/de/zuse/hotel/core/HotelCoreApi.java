package de.zuse.hotel.core;

public interface HotelCoreApi
{
    public boolean addGuest(Guest guest);
    public boolean removeGuest(Guest guest);
    public boolean addBooking(Booking booking);
    public boolean removeBooking(Booking booking);


    public Booking getBooking(int bookingID);
    public Guest getGuest(int personID);
    public void printBookingAsPDF(int bookingID);


    public boolean updateGuest(Guest guest);
    public boolean updateBooking(Booking booking);

    public int generateBookingID();
    public int generatePersonID();
}
