package de.zuse.hotel.core;

import de.zuse.hotel.db.HotelDatabaseApi;
import de.zuse.hotel.db.HotelDatabaseApiImpl;
import de.zuse.hotel.util.HotelSerializer;
import de.zuse.hotel.util.ZuseCore;
import de.zuse.hotel.util.pdf.PdfFile;

import java.io.IOException;
import java.util.List;

public class HotelCore implements HotelCoreApi
{
    private static HotelCore instance = null;
    private HotelDatabaseApi hotelDatabaseApi;
    private HotelConfiguration hotelConfiguration;

    public static HotelCore get()
    {
        if (instance == null)
            instance = new HotelCore();

        return instance;
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

        instance = null;
    }

    private HotelCore()
    {
        hotelDatabaseApi = new HotelDatabaseApiImpl();

        HotelSerializer hotelSerializer = new HotelSerializer();
        try
        {
            hotelConfiguration = hotelSerializer.deserializeHotel();
        } catch (Exception e)
        {
            ZuseCore.coreAssert(false, e.getMessage());
            if (ZuseCore.DEBUG_MODE)
                e.printStackTrace();
        }
    }

    @Override
    public void setHotelName(String name)
    {
        ZuseCore.coreAssert(name != null && name.strip().isEmpty(), "Name is empty!!");

        hotelConfiguration.setHotelName(name);
    }

    @Override
    public String getHotelName()
    {
        return hotelConfiguration.getHotelName();
    }

    @Override
    public boolean addGuest(Guest guest)
    {
        return hotelDatabaseApi.addGuest(guest);
    }

    @Override
    public boolean removeGuest(int guestID)
    {
        return hotelDatabaseApi.removeGuest(guestID);
    }

    @Override
    public boolean addBooking(Booking booking)
    {
        return hotelDatabaseApi.addBooking(booking);
    }

    @Override
    public boolean removeBooking(int bookingID)
    {
        return hotelDatabaseApi.removeBooking(bookingID);
    }

    @Override
    public Booking getBooking(int bookingID)
    {
        return hotelDatabaseApi.getBooking(bookingID);
    }

    @Override
    public Guest getGuest(int personID)
    {
        return hotelDatabaseApi.getGuest(personID);
    }

    @Override
    public List<Guest> getAllGuest()
    {
        return hotelDatabaseApi.getAllGuest();
    }

    @Override
    public List<Booking> getAllBooking()
    {
        return hotelDatabaseApi.getAllBooking();
    }

    @Override
    public PdfFile getInvoiceAsPdf(int bookingID)
    {
        return null;
    }

    @Override
    public boolean updateGuest(Guest guest)
    {
        return hotelDatabaseApi.updateGuest(guest);
    }

    @Override
    public boolean updateBooking(Booking booking)
    {
        return hotelDatabaseApi.updateBooking(booking);
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

    @Override
    public HotelConfiguration getHotelConfig()
    {
        return hotelConfiguration;
    }

    public void addNewRoomToHotel(int floorNr, Room room)
    {
        hotelConfiguration.addNewRoom(floorNr,room);

        HotelSerializer hotelSerializer = new HotelSerializer();
        try
        {
            hotelSerializer.serializeHotel(hotelConfiguration); // in case the app crash, the data does get lost
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void addNewFloorToHotel(Floor floor)
    {
        hotelConfiguration.addNewFloor(floor);

        HotelSerializer hotelSerializer = new HotelSerializer();
        try
        {
            hotelSerializer.serializeHotel(hotelConfiguration); // in case the app crash, the data does get lost
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Room getRoom(int floorNr, int roomNr)
    {
        Floor floor = hotelConfiguration.getHotelFloors().get(floorNr);
        ZuseCore.check(floor != null, "Floor " + floorNr+ " is not in Hotel!");

        Room room = floor.getRooms().get(roomNr);
        ZuseCore.check(room != null, "Room " + roomNr+ " is not in Hotel!");

        return room;
    }
}
