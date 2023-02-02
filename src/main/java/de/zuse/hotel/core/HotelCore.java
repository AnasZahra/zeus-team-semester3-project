package de.zuse.hotel.core;

import de.zuse.hotel.db.DBConnecterApi;
import de.zuse.hotel.util.PDFWriter;
import de.zuse.hotel.util.ZuseCore;

import java.time.LocalDate;

public class HotelCore implements HotelCoreApi
{
    private static HotelCore instance = null;
    private DBConnecterApi dbConnecter;

    public static void init()
    {
        instance = new HotelCore();
    }

    public static HotelCore get()
    {
        ZuseCore.coreAssert(instance != null, "init was not called before!!");
        return instance;
    }

    public HotelCore()
    {
        // dbConnecter = new DBConnecter();

        //Test Generate PDF file
        {
            LocalDate start = LocalDate.of(2023, 2, 2);
            LocalDate end = LocalDate.of(2023, 3, 1);
            LocalDate birthday = LocalDate.of(1999, 12, 2);

            Address adrAddress = new Address("Germany", "VK", "Stadion", 66333, 52);

            Guest guest = new Guest("basel", "saad", generatePersonID()
                    , birthday, "test@test.com", "123456789101", adrAddress);

            Booking booking = new Booking(1, start, end, guest);
            PDFWriter.writeStringAsPDF("test.pdf", booking.generatePdf());
        }

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
