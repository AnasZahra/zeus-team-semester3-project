package de.zuse.hotel.core;

import de.zuse.hotel.util.ZuseCore;

import java.time.LocalDate;


public class Booking
{
    public static class Payment
    {
        enum Status
        {
            PAID, NOT_PAID
        }

        enum Type
        {
            CASH, CREDIT_CARD, DEBIT_CARD, MOBILE_PAYMENT
        }

        public LocalDate date;
        public Status status;
        public Type type;

        public Payment(LocalDate date, Status status, Type type)
        {
            this.date = date;
            this.status = status;
            this.type = type;
        }

        public Payment()
        {
            this(LocalDate.now(), Status.NOT_PAID, Type.CASH);
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
    private LocalDate startDate;
    private LocalDate endDate;
    private Guest guest;
    private Payment payment;

    public Booking(int roomNumber, LocalDate startDate, LocalDate endDate, Guest guest)
    {
        ZuseCore.check(roomNumber >= 0, "Number of Room should be greater than zero!!");
        ZuseCore.check(startDate != null, "StartDate is null!!");
        ZuseCore.check(endDate != null, "EndDate is null!!");
        ZuseCore.check(guest != null, "Guest is null!!");

        ZuseCore.isValidDate(startDate,"not Valid startDate!!");
        ZuseCore.isValidDate(endDate,"not Valid endDate!!");

        this.roomNumber = roomNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.guest = guest;
        payment = new Payment();
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

    public void pay(LocalDate paymentDate, Payment.Type type)
    {
        ZuseCore.check(paymentDate != null, "paymentDate is null!!");
        ZuseCore.isValidDate(paymentDate,"not Valid Date!!");

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

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public LocalDate getEndDate()
    {
        return endDate;
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
        ZuseCore.isValidDate(startDate,"not Valid startDate!!");

        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate)
    {
        ZuseCore.check(endDate != null, "EndDate is null!!");
        ZuseCore.isValidDate(endDate,"not Valid startDate!!");

        this.endDate = endDate;
    }

}
