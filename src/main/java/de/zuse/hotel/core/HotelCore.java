package de.zuse.hotel.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.zuse.hotel.db.HotelDatabaseApi;
import de.zuse.hotel.util.HotelSerializer;
import de.zuse.hotel.util.PDFWriter;
import de.zuse.hotel.util.ZuseCore;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HotelCore implements HotelCoreApi
{
    private static HotelCore instance = null;
    private HotelDatabaseApi dbConnector;
    private HotelConfiguration hotelConfiguration;

    public static void init()
    {
        instance = new HotelCore();
        HotelSerializer hotelSerializer = new HotelSerializer();

        try
        {
            instance.hotelConfiguration = hotelSerializer.deserializeHotel();
        } catch (Exception e)
        {
            ZuseCore.coreAssert(false, e.getMessage());
            if (ZuseCore.DEBUG_MODE)
                e.printStackTrace();
        }

    }

    public static void shutdown()
    {
        HotelSerializer hotelSerializer = new HotelSerializer();

        try
        {
            hotelSerializer.serializeHotel(instance.hotelConfiguration);
        } catch (Exception e)
        {
            ZuseCore.coreAssert(false, e.getMessage());
            if (ZuseCore.DEBUG_MODE)
                e.printStackTrace();
        }
    }

    public static HotelCore get()
    {
        ZuseCore.coreAssert(instance != null, "init was not called before!!");
        return instance;
    }

    private HotelCore()
    {
        //Test Generate PDF file
        {
            LocalDate start = LocalDate.of(2024, 2, 22);
            LocalDate end = LocalDate.of(2024, 4, 1);
            LocalDate birthday = LocalDate.of(1999, 12, 2);

            Address adrAddress = new Address("Germany", "VK", "Stadion", 66333, 52);

            Guest guest = new Guest("basel", "saad",
                    birthday, "test@test.com", "123456789101", adrAddress);

            Booking booking = new Booking(1, 1, start, end, guest);
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
    public List<Floor> getFloors()
    {
        return hotelConfiguration.getHotelFloors();
    }

    @Override
    public List<Room> getRooms(int floorNr)
    {
        ZuseCore.coreAssert(floorNr < hotelConfiguration.getHotelFloors().size(), "FloorNr > size, floorNr is the index!");

        return hotelConfiguration.getHotelFloors().get(floorNr).getRooms();
    }


}
