package de.zuse.hotel.core;

public interface Hotel
{
    public int generateBookingID();
    public boolean addBooking(Booking booking);
    public boolean removeBooking(Booking booking);

}
