package de.zuse.hotel.gui;

import de.zuse.hotel.core.Booking;
import de.zuse.hotel.core.HotelCore;
import de.zuse.hotel.util.pdf.PdfFile;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

public class DashboardController implements ControllerApi
{
    private static final int UNSELECTED = -1;
    public ListView<Booking> listView;

    private int selectedBookingId;

    @Override
    public void onStart()
    {
        //disable for now, waiting for design
        // viewBookingData();
        // //Bind
        // listView.setOnMouseClicked(this::onListViewItemClicked);
        // selectedBookingId = UNSELECTED;
        // listView.setEditable(true);
    }

    @Override
    public void onUpdate()
    {
        //viewBookingData();
    }

    public void viewBookingData()
    {
        listView.getItems().clear();
        List<Booking> bookingList = HotelCore.get().getAllBooking();
        bookingList.forEach(new Consumer<Booking>()
        {
            @Override
            public void accept(Booking booking)
            {
                if (!listView.getItems().contains(booking))
                    listView.getItems().add(booking);
            }
        });

        listView.refresh();
    }

    public void onListViewItemClicked(MouseEvent event)
    {
        Booking booking = listView.getSelectionModel().getSelectedItem();
        if (booking != null)
            selectedBookingId = booking.getBookingID();
    }

    public void onSaveClicked(ActionEvent event)
    {
        if (selectedBookingId == UNSELECTED)
        {
            InfoController.showMessage(InfoController.LogLevel.Warn, "", "Select Booking to save!");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Save PDf");
        File file = fileChooser.showSaveDialog(listView.getScene().getWindow());

        if (file != null)
        {
            PdfFile bookingFile = HotelCore.get().getBookingAsPdfFile(selectedBookingId);
            bookingFile.saveFile(file.getPath());
            InfoController.showMessage(InfoController.LogLevel.Info, "Successful", file.getName() + " saved in " + file.getPath());
        }
    }

}
