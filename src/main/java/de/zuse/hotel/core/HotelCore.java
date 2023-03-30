package de.zuse.hotel.core;

import de.zuse.hotel.db.BookingSearchFilter;
import de.zuse.hotel.db.HotelDatabaseApi;
import de.zuse.hotel.db.HotelDatabaseApiImpl;
import de.zuse.hotel.db.PersonSearchFilter;
import de.zuse.hotel.gui.ControllerApi;
import de.zuse.hotel.util.HotelSerializer;
import de.zuse.hotel.util.ZuseCore;
import de.zuse.hotel.util.pdf.InvoicePdf;
import de.zuse.hotel.util.pdf.PdfFile;

import java.io.IOException;
import java.util.List;

public class HotelCore implements HotelCoreApi
{
    private static HotelCore instance = null;
    private HotelDatabaseApi hotelDatabaseApi;
    private HotelConfiguration hotelConfiguration;

    private ControllerApi currentScene; // used to update current scene when db updated

    public static HotelCore get()
    {
        ZuseCore.coreAssert(instance != null, "you should call HotelCore.init() on start of app!");

        return instance;
    }

    public static void init()
    {
        ZuseCore.coreAssert(instance == null, "call init only once!!");

        instance = new HotelCore();
    }

    public static void shutdown()
    {
        HotelSerializer hotelSerializer = new HotelSerializer();

        try
        {
            hotelSerializer.serializeHotel(instance.hotelConfiguration);
            instance.hotelDatabaseApi.shutdown();
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
    public boolean addGuest(Person guest)
    {
        boolean state = hotelDatabaseApi.addGuest(guest);
        if (currentScene != null)
            currentScene.onUpdate();
        return state;
    }

    @Override
    public boolean removeGuest(int guestID)
    {
        boolean state = hotelDatabaseApi.removeGuest(guestID);
        if (currentScene != null)
            currentScene.onUpdate();

        return state;
    }

    @Override
    public boolean addBooking(Booking booking)
    {
        boolean state = hotelDatabaseApi.addBooking(booking);
        if (currentScene != null)
            currentScene.onUpdate();
        return state;
    }

    @Override
    public boolean removeBooking(int bookingID)
    {
        boolean state = hotelDatabaseApi.removeBooking(bookingID);
        if (currentScene != null)
            currentScene.onUpdate();
        return state;
    }

    @Override
    public Booking getBooking(int bookingID)
    {
        return hotelDatabaseApi.getBooking(bookingID);
    }

    @Override
    public Person getGuest(int personID)
    {
        return hotelDatabaseApi.getGuest(personID);
    }

    @Override
    public List<Person> getAllGuest()
    {
        return hotelDatabaseApi.getAllGuest();
    }

    @Override
    public List<Booking> getAllBooking()
    {
        return hotelDatabaseApi.getAllBooking();
    }

    @Override
    public List<Booking> getBookingByFilter(BookingSearchFilter bookingSearchFilter)
    {
        return hotelDatabaseApi.getBookingsByFilter(bookingSearchFilter);
    }

    @Override
    public List<Person> getPersonsByFilter(PersonSearchFilter personSearchFilter)
    {
        return hotelDatabaseApi.getPersonsByFilter(personSearchFilter);
    }

    @Override
    public PdfFile getBookingAsPdfFile(int bookingID)
    {
        Booking booking = hotelDatabaseApi.getBooking(bookingID);
        return new InvoicePdf(booking);
    }

    @Override
    public boolean updateGuest(Person guest)
    {
        boolean state = hotelDatabaseApi.updateGuest(guest);
        if (currentScene != null)
            currentScene.onUpdate();
        return state;
    }

    @Override
    public boolean updateBooking(Booking booking)
    {
        boolean state = hotelDatabaseApi.updateBooking(booking);
        if (currentScene != null)
            currentScene.onUpdate();
        return state;
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

    @Override
    public Room getRoom(int floorNr, int roomNr)
    {
        Floor floor = hotelConfiguration.getHotelFloors().get(floorNr);
        ZuseCore.check(floor != null, "Floor " + floorNr + " is not in Hotel!");

        Room room = floor.getRooms().get(roomNr);
        ZuseCore.check(room != null, "Room " + roomNr + " is not in Hotel!");

        return room;
    }

    @Override
    public void setCurrentScene(ControllerApi currentScene)
    {
        ZuseCore.coreAssert(currentScene != null, "Scene you try to add is null!!");
        this.currentScene = currentScene;
    }

    public void addNewRoomToHotel(int floorNr, Room room)
    {
        hotelConfiguration.addNewRoom(floorNr, room);

        HotelSerializer hotelSerializer = new HotelSerializer();
        try
        {
            hotelSerializer.serializeHotel(hotelConfiguration); // in case the app crash, the data does get lost
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        if (currentScene != null)
            currentScene.onUpdate();
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
        if (currentScene != null)
            currentScene.onUpdate();
    }
}
