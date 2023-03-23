package de.zuse.hotel.core;

import java.util.List;

public interface HotelCoreApi
{
    boolean addGuest(Guest guest);

    boolean removeGuest(Guest guest);

    boolean addBooking(Booking booking);

    boolean removeBooking(Booking booking);


    Booking getBooking(int bookingID);

    Guest getGuest(int personID);

    void printBookingAsPDF(int bookingID);


    boolean updateGuest(Guest guest);

    boolean updateBooking(Booking booking);

    List<Floor> getFloors();

    List<Room> getRooms(int floorNr);
}
