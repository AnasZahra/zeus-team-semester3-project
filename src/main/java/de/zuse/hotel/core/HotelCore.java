package de.zuse.hotel.core;

import de.zuse.hotel.db.BookingSearchFilter;
import de.zuse.hotel.db.HotelDatabaseApi;
import de.zuse.hotel.db.HotelDatabaseApiImpl;
import de.zuse.hotel.db.PersonSearchFilter;
import de.zuse.hotel.util.HotelSerializer;
import de.zuse.hotel.util.ZuseCore;
import de.zuse.hotel.util.pdf.InvoicePdf;
import de.zuse.hotel.util.pdf.PdfFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HotelCore implements HotelCoreApi
{
    private static HotelCore instance = null;
    private HotelDatabaseApi hotelDatabaseApi;
    private HotelConfiguration hotelConfiguration;
    private Runnable updateCallback;

    public static HotelCore get()
    {
        ZuseCore.coreAssert(instance != null, "you should call HotelCore.init() on start of app!");

        return instance;
    }

    public static void init()
    {
        if (instance != null)
            return;

        instance = new HotelCore();
    }

    public static void shutdown()
    {
        HotelSerializer hotelSerializer = new HotelSerializer();

        try
        {
            hotelSerializer.serializeHotel(instance.hotelConfiguration);
            hotelSerializer.serializeSettings();
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
            hotelSerializer.deserializeSettings();
        } catch (Exception e)
        {
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
        if (updateCallback != null)
            updateCallback.run();

        return state;
    }

    @Override
    public boolean removeGuest(int guestID)
    {
        boolean state = hotelDatabaseApi.removeGuest(guestID);
        if (updateCallback != null)
            updateCallback.run();

        return state;
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
    public List<Person> getPersonsByFilter(PersonSearchFilter personSearchFilter)
    {
        return hotelDatabaseApi.getPersonsByFilter(personSearchFilter);
    }

    @Override
    public boolean updateGuest(Person guest)
    {
        boolean state = hotelDatabaseApi.updateGuest(guest);
        if (updateCallback != null)
            updateCallback.run();

        return state;
    }

    @Override
    public boolean addBooking(Booking booking)
    {
        boolean state = hotelDatabaseApi.addBooking(booking);
        if (updateCallback != null)
            updateCallback.run();

        return state;
    }

    @Override
    public boolean removeBooking(int bookingID)
    {
        boolean state = hotelDatabaseApi.removeBooking(bookingID);
        if (updateCallback != null)
            updateCallback.run();

        return state;
    }

    @Override
    public boolean removeBooking(Booking booking)
    {
        return removeBooking(booking.getBookingID());
    }

    @Override
    public Booking getBooking(int bookingID)
    {
        return hotelDatabaseApi.getBooking(bookingID);
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
    public List<Booking> getAllBookingBetweenStartAndEnd(LocalDate start, LocalDate end)
    {
        return hotelDatabaseApi.getAllBookingBetweenStartAndEnd(start, end);
    }

    @Override
    public boolean updateBooking(Booking booking)
    {
        boolean state = hotelDatabaseApi.updateBooking(booking);
        if (updateCallback != null)
            updateCallback.run();

        return state;
    }

    @Override
    public PdfFile getBookingAsPdfFile(int bookingID)
    {
        Booking booking = hotelDatabaseApi.getBooking(bookingID);
        if (booking == null || booking.isCanceled())
        {
            ZuseCore.check(false, "You can not save canceled Booking as Pdf");
        }

        return new InvoicePdf(booking);
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
    public Room getRoom(int floorNr, int roomNr)
    {
        Floor floor = hotelConfiguration.getHotelFloors().get(floorNr);
        ZuseCore.check(floor != null, "Floor " + floorNr + " is not in Hotel!");

        Room room = floor.getRooms().get(roomNr);
        ZuseCore.check(room != null, "Room " + roomNr + " is not in Hotel!");

        return room;
    }

    @Override
    public boolean isFloorInHotel(int floorNr)
    {
        //TODO: Optimization
        for (Floor floor : hotelConfiguration.getHotelFloors())
            if (floor.getFloorNr() == floorNr)
                return true;

        return false;
    }

    @Override
    public boolean isRoomInHotel(int floorNr, int roomNr)
    {
        //TODO: Optimization
        ZuseCore.check(isFloorInHotel(floorNr), "Floor" + floorNr + " is not in Hotel!!");

        for (Room room : getFloorByFloorNr(floorNr).getRooms())
        {
            if (room.getRoomNr() == roomNr)
                return true;
        }

        return false;
    }

    @Override
    public void removeRoomFromHotel(int floorNr, int roomNr)
    {
        hotelConfiguration.removeRoom(floorNr, roomNr);
        // in case the app crash, we do not lose any changes
        serializeHotel();
    }

    @Override
    public void addNewFloorToHotel(Floor floor)
    {
        hotelConfiguration.addNewFloor(floor);
        // in case the app crash, we do not lose any changes
        serializeHotel();
    }

    @Override
    public void addNewRoomToHotel(int floorNr, Room room)
    {
        hotelConfiguration.addNewRoom(floorNr, room);
        // in case the app crash, we do not lose any changes
        serializeHotel();
    }

    @Override
    public Floor getFloorByFloorNr(int floorNr)
    {
        return hotelConfiguration.getFloorByFloorNr(floorNr);
    }

    @Override
    public Room getRoomByRoomNr(int floorNr, int roomNr)
    {
        return hotelConfiguration.getRoomByRoomNr(floorNr, roomNr);
    }

    @Override
    public void addNewRoomService(String serviceName, double price)
    {
        hotelConfiguration.addNewRoomService(serviceName, price);
    }

    @Override
    public double getRoomServicePrice(String serviceName)
    {
        return hotelConfiguration.getRoomServicePrice(serviceName);
    }

    @Override
    public boolean hasRoomService(String serviceName)
    {
        return hotelConfiguration.hasServiceName(serviceName);
    }

    @Override
    public List<String> getAllRoomServices()
    {
        return new ArrayList<>(hotelConfiguration.getRoomServices().keySet());
    }

    @Override
    public void serializeHotel()
    {
        HotelSerializer hotelSerializer = new HotelSerializer();
        try
        {
            hotelSerializer.serializeHotel(hotelConfiguration);
            hotelSerializer.serializeSettings();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        if (updateCallback != null)
            updateCallback.run();
    }

    @Override
    public void bindOnUpdateAction(Runnable action)
    {
        updateCallback = action;
    }
}
