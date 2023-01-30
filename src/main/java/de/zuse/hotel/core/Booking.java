package de.zuse.hotel.core;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;


public class Booking
{
    public static class Payment
    {
        enum Status
        {
            PAID, NOT_PAID
        }

        public enum Type
        {
            CASH, CREDIT_CARD, DEBIT_CARDS, MOBILE_PAYMENT
        }

        public Date date;
        public Status status;
        public Type type;
    }

    private int bookingID;
    private int roomNumber;

    private Date startDate;
    private Date endDate;

    private Guest guest;
    private Payment payment;

    public Booking(int roomNumber, Date startDate, Date endDate, Guest guest)
    {
        ZuseCore.checkFatal(roomNumber >= 0, "Number of Room should be greater than zero!!");
        ZuseCore.checkFatal(startDate != null, "StartDate is null!!");
        ZuseCore.checkFatal(endDate != null, "EndDate is null!!");
        ZuseCore.checkFatal(guest != null, "Guest is null!!");

        this.roomNumber = roomNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.guest = guest;
    }

    public int createInvoice()
    {
        return 0;
    }

    public void printBooking()
    {
        // Generate PDF-File
    }

    public void pay(Date paymentDate, Payment.Type type)
    {
        ZuseCore.checkFatal(paymentDate != null, "paymentDate is null!!");

        LocalDateTime now = LocalDateTime.now();

        payment.status = Payment.Status.PAID;
        payment.date = paymentDate;
        payment.type = type;
    }

    public int getBookingID()
    {
        return bookingID;
    }

    public int getRoomNumber()
    {
        return roomNumber;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public boolean isPaid()
    {
        return payment.status == Payment.Status.PAID;
    }

    public void setRoomNumber(int roomNumber)
    {
        ZuseCore.checkFatal(roomNumber >= 0, "Number of Room should be greater than zero!!");
        this.roomNumber = roomNumber;
    }

    public void setStartDate(Date startDate)
    {
        ZuseCore.checkFatal(startDate != null, "StartDate is null!!");
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate)
    {
        ZuseCore.checkFatal(endDate != null, "EndDate is null!!");
        this.endDate = endDate;
    }

}
