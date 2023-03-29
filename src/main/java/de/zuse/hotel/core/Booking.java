package de.zuse.hotel.core;

import de.zuse.hotel.util.ZuseCore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Bookings")
public class Booking
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Booking_id")
    private int bookingID;
    @Column(name = "Room_Number", nullable = false)
    private int roomNumber;
    @Column(name = "Floor_Number", nullable = false)
    private int floorNumber;
    @Column(name = "Start_Date", nullable = false)
    private LocalDate startDate;
    @Column(name = "End_Date", nullable = false)
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PersonId", nullable = false)
    private Person guest;//to avoid EAGER loading maybe save person id and load it manually in db

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Payment_Id", nullable = false)
    private Payment payment;

    @Column(name = "Guests_Num", nullable = false)
    private int guestsNum;
    private boolean canceled = false;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> extraServices = new ArrayList<>();

    public Booking(int roomNumber, int floorNumber, LocalDate startDate, LocalDate endDate, Person guest)
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
    }

    public Booking()
    {
    }

    public int createInvoice()
    {
        return 0;
    }

    @Override
    public String toString()
    {
        return "roomNumber=" + roomNumber +
                ", floorNumber=" + floorNumber +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", guest=" + guest.getFirstname() + " " + guest.getLastname() +
                ", payment=" + payment +
                ", guestsNum=" + guestsNum +
                ", Services= " + extraServices.toString() +
                ", canceled=" + canceled;
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

    public Person getGuest()
    {
        return guest;
    }

    public void addExtraService(String serviceName)
    {
        //extraServices will not contain duplicate or not valid services
        ZuseCore.check(HotelCore.get().getHotelConfig().hasServiceName(serviceName), "Service Name is not valid!");
        ZuseCore.check(extraServices.contains(serviceName) == false, "Room has already this service");

        extraServices.add(serviceName);
    }

    public List<String> getBookedServices()
    {
        return extraServices;
    }

    public int getFloorNumber()
    {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber)
    {
        this.floorNumber = floorNumber;
    }

    public void canceledBooking()
    {
        canceled = true;
    }

    public int getGuestsNum()
    {
        return guestsNum;
    }

    public void setGuestsNum(int guestsNum)
    {
        this.guestsNum = guestsNum;
    }

    public Payment getPayment()
    {
        return payment;
    }

}
