package de.zuse.hotel.util.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import de.zuse.hotel.core.Booking;
import de.zuse.hotel.core.HotelCore;
import de.zuse.hotel.util.ZuseCore;

import javafx.geometry.Insets;
import javafx.scene.control.Cell;

import java.io.FileOutputStream;
import java.util.Iterator;

public class InvoicePdf implements PdfFile
{
    private Booking booking;
    private static final String EURO_SYMOBL = "€";

    public InvoicePdf(Booking booking)
    {
        ZuseCore.check(booking != null, "can not generate PDF file because Booking is null!");

        this.booking = booking;
    }

    @Override
    public void saveFile(String filePath)
    {
        Document document = new Document();

        try
        {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.addTitle(HotelCore.get().getHotelName());

            //Set Title
            {
                Paragraph title = PDFWriter.createCustomParagraph(24, BaseFont.HELVETICA_BOLD,
                        BaseColor.BLACK, HotelCore.get().getHotelName());

                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                Chunk linebreak = new Chunk(new LineSeparator());
                document.add(linebreak);
                document.add(new Paragraph("\n\n"));
            }

            //Booking details
            {
                Paragraph title = PDFWriter.createCustomParagraph(20, BaseFont.TIMES_BOLDITALIC,
                        BaseColor.BLACK, "Booking-Details");

                document.add(title);
                document.add(new Paragraph("Check-in: " + booking.getStartDate()));
                document.add(new Paragraph("Check-out: " + booking.getStartDate()));

                //TODO (Basel): maybe add many Guests
                document.add(new Paragraph("Geust: " + booking.getGuestName()));
                document.add(new Paragraph("Room: " + booking.getRoomNumber()));
                Chunk linebreak = new Chunk(new DottedLineSeparator());
                document.add(linebreak);
                document.add(new Paragraph("\n\n"));
            }

            // Booked By
            {
                Paragraph title = PDFWriter.createCustomParagraph(20, BaseFont.TIMES_BOLDITALIC,
                        BaseColor.BLACK, "Booked By");

                document.add(title);
                document.add(new Paragraph(booking.getGuestName()));
                document.add(new Paragraph(booking.getGuest().getEmail()));
                document.add(new Paragraph(booking.getGuest().getTelNumber()));
                Chunk linebreak = new Chunk(new DottedLineSeparator());
                document.add(linebreak);
            }

            // Price Description
            {
                float[] pointColumnWidths = {150.0f, 150.0f};
                PdfPTable table = new PdfPTable(pointColumnWidths);
                table.addCell("Description");
                table.addCell("Amount");

                double roomPrice = HotelCore.get().getRoom(booking.getFloorNumber(), booking.getRoomNumber()).getPrice();
                table.addCell("1 x Room Price(" + roomPrice + ")");
                table.addCell(roomPrice + EURO_SYMOBL);

                StringBuffer services = new StringBuffer();
                double totalPrice = 0.0f;
                Iterator<String> it = booking.getBookedServices().iterator();

                while (it.hasNext())
                {
                    String next = it.next();
                    services.append("1 x ").append(next);
                    totalPrice += HotelCore.get().getHotelConfig().getRoomServicePrice(next);
                }

                table.addCell(services.toString());
                table.addCell(totalPrice + EURO_SYMOBL);
            }

            document.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
