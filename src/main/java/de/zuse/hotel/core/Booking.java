package de.zuse.hotel.core;

import de.zuse.hotel.util.ZuseCore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Booking
{
    public int getFloorNumber()
    {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber)
    {
        this.floorNumber = floorNumber;
    }

    public static class Payment
    {
        public enum Status
        {
            PAID, NOT_PAID
        }

        public enum Type
        {
            CASH, CREDIT_CARD, DEBIT_CARD, MOBILE_PAYMENT
        }

        public LocalDate date;
        public Status status;
        public Type type;
        public float price;
        private static float TAX = 10.0f;//TODO

        public Payment(LocalDate date, Status status, Type type, float price)
        {
            ZuseCore.check(price >= 0.0f, "price must be >= 0");

            this.date = date;
            this.status = status;
            this.type = type;
            this.price = price;
        }

        public Payment()
        {
            this(LocalDate.now(), Status.NOT_PAID, Type.CASH, 0.0f);
        }

        @Override
        public String toString()
        {
            return "Payment{" +
                    "date=" + date +
                    ", status=" + status +
                    ", type=" + type +
                    '}';
        }

    }

    private int bookingID;
    private int roomNumber;
    private int floorNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private Guest guest;
    private Payment payment;
    private ArrayList<String> extraServices;
    private boolean canceled = false;


    public Booking(int roomNumber, int floorNumber, LocalDate startDate, LocalDate endDate, Guest guest)
    {
        ZuseCore.check(roomNumber >= 0, "Number of Room should be greater than zero!!");
        ZuseCore.check(floorNumber >= 0, "Number of Room should be greater than zero!!");
        ZuseCore.check(startDate != null, "StartDate is null!!");
        ZuseCore.check(endDate != null, "EndDate is null!!");
        ZuseCore.check(guest != null, "Guest is null!!");

        ZuseCore.isValidDate(startDate, "not Valid startDate!!");
        ZuseCore.isValidDate(endDate, "not Valid endDate!!");

        this.roomNumber = roomNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.guest = guest;
        this.floorNumber = floorNumber;
        payment = new Payment();

        //Service with size of available services in hotel
        extraServices = new ArrayList<>(HotelCore.get().getHotelConfig().getRoomServiceNum());
    }

    public int createInvoice()
    {
        return 0;
    }

    public String[] generatePdf()
    {
        //TODO(Basel): format text
        String[] text =
                {
                        "BookingID: " + bookingID,
                        "RoomNumber: " + roomNumber,
                        "StartDate: " + startDate,
                        "EndDate: " + endDate,
                        "Guest: " + guest.toString(),
                        payment.toString()
                };

        return text;
    }

    public void pay(LocalDate paymentDate, Payment.Type type, float price)
    {
        ZuseCore.coreAssert(paymentDate != null, "paymentDate is null!!");
        ZuseCore.isValidDate(paymentDate, "not Valid Date!!");

        payment = new Payment(paymentDate, Payment.Status.PAID, type, price);
    }

    public int getBookingID()
    {
        return bookingID;
    }

    public int getRoomNumber()
    {
        return roomNumber;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public LocalDate getEndDate()
    {
        return endDate;
    }

    public String getGuestName()
    {
        return guest.getFirstname() + " " + guest.getLastname();

    }

    public boolean isPaid()
    {
        return payment.status == Payment.Status.PAID;
    }

    public void setRoomNumber(int roomNumber)
    {
        ZuseCore.check(roomNumber >= 0, "Number of Room should be greater than zero!!");
        this.roomNumber = roomNumber;
    }

    public void setStartDate(LocalDate startDate)
    {
        ZuseCore.check(startDate != null, "StartDate is null!!");
        ZuseCore.isValidDate(startDate, "not Valid startDate!!");

        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate)
    {
        ZuseCore.check(endDate != null, "EndDate is null!!");
        ZuseCore.isValidDate(endDate, "not Valid startDate!!");

        this.endDate = endDate;
    }

    public Guest getGuest()
    {
        return guest;
    }

    public void addExtraService(String serviceName)
    {
        ZuseCore.check(HotelCore.get().getHotelConfig().hasServiceName(serviceName), "Service Name is not valid!");
        ZuseCore.check(extraServices.contains(serviceName) == false, "Room has already this service");

        extraServices.add(serviceName);
    }

    public List<String> getBookedServices()
    {
        return extraServices;
    }

    public void canceledBooking (){
        canceled = true;
    }

}
