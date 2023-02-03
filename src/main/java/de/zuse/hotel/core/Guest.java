package de.zuse.hotel.core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Guest extends Person
{
    private ArrayList<Integer> bookingID;

    public Guest(String firstname, String nachname, int id, LocalDate birthday, String email, String telefonnr, Address address)
    {
        super(firstname, nachname, birthday, email, telefonnr, address);
        this.bookingID = new ArrayList<>();
    }

    public void setId(int id)
    {
        super.setId(id);
    }

    @Override
    public String toString()
    {
        return "Guest{" +
                "bookingID=" + bookingID + "\n"+
                super.toString() +
                '}';
    }

}
