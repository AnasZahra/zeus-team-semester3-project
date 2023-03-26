package de.zuse.hotel;

import de.zuse.hotel.core.Address;
import de.zuse.hotel.core.Booking;
import de.zuse.hotel.core.Guest;
import de.zuse.hotel.core.HotelCore;
import de.zuse.hotel.gui.Gui;
import de.zuse.hotel.util.pdf.InvoicePdf;
import de.zuse.hotel.util.pdf.PdfFile;

import java.time.LocalDate;

public class App
{
    public static void main(String[] args)
    {

        Layer layer = new Gui();
        layer.onStart();
        layer.run(args);
        layer.onClose();


        //Test


    }
}


