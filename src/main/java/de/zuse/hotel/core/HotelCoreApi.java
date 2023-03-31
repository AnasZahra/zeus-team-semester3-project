package de.zuse.hotel.core;

import de.zuse.hotel.db.BookingSearchFilter;
import de.zuse.hotel.db.PersonSearchFilter;
import de.zuse.hotel.gui.ControllerApi;
import de.zuse.hotel.util.pdf.PdfFile;

import java.util.List;

public interface HotelCoreApi
{
    void setHotelName(String name);
    String getHotelName();

    boolean addGuest(Person guest);

    boolean removeGuest(int guestID);

    boolean addBooking(Booking booking);

    boolean removeBooking(int bookingID);


    Booking getBooking(int bookingID);

    Person getGuest(int personID);
    List<Person> getAllGuest();
    List<Booking> getAllBooking();
    List<Booking> getBookingByFilter(BookingSearchFilter bookingSearchFilter);

    List<Person> getPersonsByFilter(PersonSearchFilter personSearchFilter);

    PdfFile getBookingAsPdfFile(int bookingID);

    boolean updateGuest(Person guest);

    boolean updateBooking(Booking booking);

    List<Floor> getFloors();

    List<Room> getRooms(int floorNr);

    Room getRoom(int floorNr, int roomNr);

    void removeRoomFromHotel(int floorNr, int roomNr);

    /**
     * Room Services
     */
    void addNewRoomService(String serviceName, double price);
    double getRoomServicePrice(String serviceName);
    boolean hasRoomService(String serviceName);


    void setCurrentScene(ControllerApi currentScene);
}
