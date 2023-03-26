package de.zuse.hotel;

import de.zuse.hotel.core.Address;
import de.zuse.hotel.core.Booking;
import de.zuse.hotel.core.Guest;
import de.zuse.hotel.core.HotelCore;
import de.zuse.hotel.util.pdf.InvoicePdf;
import de.zuse.hotel.util.pdf.PdfFile;

import java.time.LocalDate;

public class App
{
    public static void main(String[] args)
    {
        /*
        Layer layer = new ConsoleDialogLayer();
        layer.onStart();
        layer.run(args);
        layer.onClose();
        */

        //Test
        {
            LocalDate start = LocalDate.of(2024, 2, 22);
            LocalDate end = LocalDate.of(2024, 4, 1);
            LocalDate birthday = LocalDate.of(1999, 12, 2);

            Address adrAddress = new Address("Germany", "VK", "Stadion", 66333, 52);

            Guest guest = new Guest("basel", "saad",
                    birthday, "test@test.com", "123456789101", adrAddress);


            HotelCore.get().getHotelConfig().setDefaultFloorsAndRooms();

            Booking booking = new Booking(0, 0, start, end, guest);
            booking.pay(start, Booking.Payment.Type.CASH, 200.0f);
            booking.addExtraService("Wifi");
            booking.addExtraService("Dinner");

            PdfFile invoicePdf = new InvoicePdf(booking);
            invoicePdf.saveFile("test.pdf");

            HotelCore.shutdown();
        }

    }
}


