package de.zuse.hotel.core;

import de.zuse.hotel.util.pdf.PdfFile;

import java.util.List;

public interface HotelCoreApi
{
    void setHotelName(String name);
    String getHotelName();

    boolean addGuest(Guest guest);

    boolean removeGuest(int guestID);

    boolean addBooking(Booking booking);

    boolean removeBooking(int bookingID);


    Booking getBooking(int bookingID);

    Guest getGuest(int personID);
    List<Guest> getAllGuest();
    List<Booking> getAllBooking();

    PdfFile getInvoiceAsPdf(int bookingID);

    boolean updateGuest(Guest guest);

    boolean updateBooking(Booking booking);

    List<Floor> getFloors();

    List<Room> getRooms(int floorNr);

    Room getRoom(int floorNr, int roomNr);

    HotelConfiguration getHotelConfig();
}
