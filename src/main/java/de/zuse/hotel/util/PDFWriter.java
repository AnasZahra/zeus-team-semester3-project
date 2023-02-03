package de.zuse.hotel.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class PDFWriter
{

    // TODO(Basel) text Format
    public static void writeStringAsPDF(String outputPath, String[] text)
    {
        Document document = new Document();
        try
        {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();

            // Title
            {
                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                Font font = new Font(bf, 24);
                Paragraph title = new Paragraph("ZuseHotel", font);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                document.add(new Paragraph("\n\n"));
            }

            // Text
            for (String line : text)
            {
                document.add(new Paragraph(line));
            }

            document.close();
        } catch (DocumentException | IOException e)
        {
            e.printStackTrace();
        }
    }

}
