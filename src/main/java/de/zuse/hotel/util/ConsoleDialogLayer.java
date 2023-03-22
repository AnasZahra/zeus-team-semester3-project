package de.zuse.hotel.util;

import de.zuse.hotel.Layer;
import de.zuse.hotel.core.Address;
import de.zuse.hotel.core.Booking;
import de.zuse.hotel.core.Guest;
import de.zuse.hotel.db.HotelDatabaseApi;
import de.zuse.hotel.db.HotelDatabaseApiImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ConsoleDialogLayer implements Layer
{
    //-------------------Const fields--------------------------------//
    private static final int ADD_GUEST = 1;
    private static final int REMOVE_GUEST = 2;
    private static final int UPDATE_GUEST = 3;
    private static final int GET_GUEST = 4;
    private static final int GET_ALL_GUEST = 5;

    private static final int ADD_BOOKING = 6;
    private static final int REMOVE_BOOKING = 7;
    private static final int UPDATE_BOOKING = 8;
    private static final int GET_BOOKING = 9;
    private static final int GET_ALL_BOOKING = 10;
    private static final int END = 0;
    //--------------------------------------------------------------//

    private Scanner input;
    private HotelDatabaseApi hotelDatabaseApi;


    @Override
    public void onStart()
    {
        System.out.println("Start Loading Database..");
        hotelDatabaseApi = new HotelDatabaseApiImpl();
        input = new Scanner(System.in);
    }

    @Override
    public void run(String[] args)
    {
        int currentInput = -1;
        while (currentInput != END)
        {
            printInputInformation();
            currentInput = readInteger();
            handelInput(currentInput);
        }
    }

    @Override
    public void onClose()
    {
        System.out.println("Close Hotel App ...");
        hotelDatabaseApi.shutdown();
    }


    private void printInputInformation()
    {
        System.out.println("Add Guest: " + ADD_GUEST);
        System.out.println("Remove Guest: " + REMOVE_GUEST);
        System.out.println("Update Guest: " + UPDATE_GUEST);
        System.out.println("Get Guest: " + GET_GUEST);
        System.out.println("Get All Guests: " + GET_ALL_GUEST);

        System.out.println("Add Booking: " + ADD_BOOKING);
        System.out.println("Remove Booking: " + REMOVE_BOOKING);
        System.out.println("Update Booking: " + UPDATE_BOOKING);
        System.out.println("Get Booking: " + GET_BOOKING);
        System.out.println("Get All Booking: " + GET_ALL_BOOKING);
        System.out.println("Close App: " + END);
    }

    private void handelInput(int inputParam)
    {
        switch (inputParam)
        {
            case ADD_GUEST:         addGuest();         break;
            case REMOVE_GUEST:      removeGuest();      break;
            case UPDATE_GUEST:      updateGuest();      break;
            case GET_GUEST:         getGuest();         break;
            case GET_ALL_GUEST:     getAllGuests();     break;
            case ADD_BOOKING:       addBooking();       break;
            case REMOVE_BOOKING:    removeBooking();    break;
            case UPDATE_BOOKING:    updateBooking();    break;
            case GET_BOOKING:       getBooking();       break;
            case GET_ALL_BOOKING:   getAllBooking();    break;
            default:                return;
        }
    }

    private void addGuest()
    {
        Guest guest = readGuestInfo();

        hotelDatabaseApi.addGuest(guest);
    }

    private void removeGuest()
    {
        System.out.print("Enter Guest ID: ");
        int id = readInteger();

        hotelDatabaseApi.removeGuest(id);
    }

    private void updateGuest()
    {
        System.out.print("Enter Guest ID: ");
        int id = readInteger();

        Guest guest = readGuestInfo();

        hotelDatabaseApi.updateGuest(id, guest);
    }

    private void getGuest()
    {
        System.out.print("Enter Guest ID: ");
        int id = readInteger();
        Guest guest = hotelDatabaseApi.getGuest(id);

        System.out.println(guest.toString());
    }

    private void getAllGuests()
    {
        List<Guest> guests = hotelDatabaseApi.getAllGuest();
        //guests.forEach(System.out::println);

        if (guests != null)
        {
            for (Guest guest : guests)
                if (guest != null)
                    System.out.println(guest);
        }
    }

    private void addBooking()
    {
        Booking booking = readBookingInfo();
        hotelDatabaseApi.addBooking(booking);
    }

    private void removeBooking()
    {
        System.out.println("Booking ID: ");
        int bookID = readInteger();

        hotelDatabaseApi.removeBooking(bookID);
    }

    private void updateBooking()
    {
        System.out.println("Booking ID: ");
        int bookID = readInteger();

        System.out.println("\n\nIn ConsoleDialog you have to enter the information again!\n\n");
        Booking updatedBooking = readBookingInfo();

        hotelDatabaseApi.updateBooking(bookID, updatedBooking);
    }

    private void getBooking()
    {
        System.out.println("Booking ID: ");
        int bookID = readInteger();

        Booking booking = hotelDatabaseApi.getBooking(bookID);

        System.out.println(booking);
    }

    private void getAllBooking()
    {
        List<Booking> bookings = hotelDatabaseApi.getAllBooking();
        //bookings.forEach(System.out::println);
        if (bookings != null)
        {
            for (Booking booking : bookings)
                if (booking != null)
                    System.out.println(bookings);
        }

    }

    private int readInteger()
    {
        System.out.print("--> ");
        int in = input.nextInt();
        input.nextLine();
        return in;
    }

    private float readFloat()
    {
        System.out.print("--> ");
        float in = input.nextFloat();
        input.nextLine();
        return in;
    }

    private String readString()
    {
        System.out.print("--> ");
        return input.nextLine();
    }

    private Address readAddressInfo()
    {
        System.out.println("---(Address)----\n");
        System.out.print("Country: ");
        String country = readString();

        System.out.print("City: ");
        String city = readString();

        System.out.print("Street: ");
        String street = readString();

        System.out.print("plz(Integer): ");
        int plz = readInteger();

        System.out.print("houseNr(Integer): ");
        int houseNr = readInteger();

        return new Address(country, city, street, plz, houseNr);
    }

    private Guest readGuestInfo()
    {
        System.out.print("FirstName: ");
        String firstName = readString();

        System.out.print("LastName: ");
        String lastName = readString();

        LocalDate birthday = readDate("Birthday");

        System.out.print("Email: ");
        String email = readString();


        System.out.print("TelephoneNr: ");
        String telNr = readString();

        Address address = readAddressInfo();
        return new Guest(firstName, lastName, birthday, email, telNr, address);
    }

    public Booking readBookingInfo()
    {
        System.out.print("roomNumber: ");
        int roomNr = readInteger();

        System.out.print("floorNumber: ");
        int floorNr = readInteger();

        LocalDate startDate = readDate("StartDate_Booking");
        LocalDate endDate = readDate("EndDate_Booking");

        System.out.println("Enter Guest Id: ");
        int guestID = readInteger();

        Guest guest = hotelDatabaseApi.getGuest(guestID);
        Booking booking = new Booking(roomNr, floorNr, startDate, endDate, guest);

        Booking.Payment payment = null;
        System.out.println("is it Paid?: ");
        System.out.println("Paid: " + Booking.Payment.Status.PAID.ordinal());
        System.out.println("Not Paid: " + Booking.Payment.Status.NOT_PAID.ordinal());
        int paymentStatus = readInteger();

        if (paymentStatus == Booking.Payment.Status.PAID.ordinal())
        {
            LocalDate payDate = readDate("Payment_Date");
            System.out.println("\n----PaymentType---\n");
            System.out.println("CASH: " + Booking.Payment.Type.CASH.ordinal());
            System.out.println("CREDIT_CARD: " + Booking.Payment.Type.CREDIT_CARD.ordinal());
            System.out.println("DEBIT_CARD: " + Booking.Payment.Type.DEBIT_CARD.ordinal());
            System.out.println("MOBILE_PAYMENT: " + Booking.Payment.Type.MOBILE_PAYMENT.ordinal());
            int paymentTypeInput = readInteger();

            booking.pay(payDate, Booking.Payment.Type.values()[paymentTypeInput]);
        }

        return booking;
    }

    private LocalDate readDate(String date)
    {
        System.out.print(date + "(Year): ");
        int year = readInteger();

        System.out.print(date + "(Month): ");
        int month = readInteger();

        System.out.print(date + "(Day): ");
        int day = readInteger();

        return LocalDate.of(year, month, day);
    }

}
