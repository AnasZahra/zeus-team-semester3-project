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
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HotelCore implements HotelCoreApi
{
    private static HotelCore instance = null;
    private HotelDatabaseApi hotelDatabaseApi;
    private HotelConfiguration hotelConfiguration;

    //TODO: for set CurrentScene and Stage maybe another solution at the end, with one function call for example or event system
    private ControllerApi currentScene; // used to update current scene when db updated
    private Stage currentStage;

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
        return hotelDatabaseApi.getAllBooking()
                .stream()
                .filter( booking -> !booking.isCanceled())
                .collect(Collectors.toList());
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

    public Floor getFloorByFloorNr(int floorNr)
    {
        return hotelConfiguration.getFloorByFloorNr(floorNr);
    }

    public Room getRoomByRoomNr(int floorNr, int roomNr)
    {
        return hotelConfiguration.getRoomByRoomNr(floorNr, roomNr);
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

    public boolean isFloorInHotel(int floorNr)
    {
        //TODO: Optimization
        for (Floor floor : hotelConfiguration.getHotelFloors())
            if (floor.getFloorNr() == floorNr)
                return true;

        return false;
    }

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

    public List<String> getAllRoomServices()
    {
        return new ArrayList<>(hotelConfiguration.getRoomServices().keySet());
    }

    @Override
    public void setCurrentScene(ControllerApi currentScene)
    {
        ZuseCore.coreAssert(currentScene != null, "Scene you try to add is null!!");
        this.currentScene = currentScene;
    }

    public Stage getCurrentStage()
    {
        return currentStage;
    }

    public void setCurrentStage(Stage stage)
    {
        currentStage = stage;
    }

    public void addNewRoomToHotel(int floorNr, Room room)
    {
        hotelConfiguration.addNewRoom(floorNr, room);
        // in case the app crash, we do not lose any changes
        serializeHotel();
    }

    public void addNewFloorToHotel(Floor floor)
    {
        hotelConfiguration.addNewFloor(floor);
        // in case the app crash, we do not lose any changes
        serializeHotel();
    }

    @Override
    public void removeRoomFromHotel(int floorNr, int roomNr)
    {
        hotelConfiguration.removeRoom(floorNr, roomNr);
        // in case the app crash, we do not lose any changes
        serializeHotel();
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

        if (currentScene != null)
            currentScene.onUpdate();
    }
}
