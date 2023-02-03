package de.zuse.hotel.core;
import java.util.ArrayList;

import java.util.ArrayList;
import java.util.Date;

public class Invoice{

    private int invoiceId;
    private double price;
    private double tax;
    private ArrayList<Booking.Payment> Payments;


    public Invoice(int invoiceId, double price, double tax)
    {

        ZuseCore.check(price > 0.0 , "the price can not be 0");
        ZuseCore.check(tax >= 0.0 , "There should be a tax");


        this.invoiceId = invoiceId;
        this.price = price;
        this.tax = tax;

        this.Payments = new ArrayList<>();
    }





//public String toString()
//  {
//      return "Invoice" + "\n" +
//              ", Guest: " + Book +
//              ", price= " + price + "," +
//              ", tax= " + tax + "%" + "," +
//              ", Summe=" + (price * (tax/100)) +
//              ", Check-in: " + getStartDate() + "," +
//              ", Check-out: " + getEndDate() + "," +
//              ", address=" + address + "\n";
//  }




    public String[] generatePdf(int bookingId)
    {
        // Generate PDF-File
        //TODO(Anas): format text
        // while doing javaFX we will do the price
        Booking booking = HotelCore.get().getBooking(bookingId);

        String[] text =
                {
                        "Invoice" + "\n" +
                        "Guest: " + booking.getGuestName() ,
                        "price: " + price,
                        "tax: " + tax + "%",
                        "Summe=: " + (price * (tax/100)),
                        "Check-in: " + booking.getStartDate(),
                        "Check-out: " + booking.getEndDate() + ","
                        //payment.toString()
                };

        return text;
    }

    public double getPrice()
    {
        return price;
    }

    public double getTax()
    {
        return tax;
    }


    public void setPrice(double price)
    {
        ZuseCore.check(price > 0.0 , "the price can not be 0");
        this.price = price;
    }

    public void setTax(double tax)
    {
        ZuseCore.check(tax >= 0.0, "There should be a tax");
        this.tax = tax;
    }

}
