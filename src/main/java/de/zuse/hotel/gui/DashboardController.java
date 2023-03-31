package de.zuse.hotel.gui;

import de.zuse.hotel.core.Booking;
import de.zuse.hotel.core.HotelCore;
import de.zuse.hotel.util.pdf.PdfFile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class DashboardController implements ControllerApi, Initializable
{
    private static final int UNSELECTED = -1;
    public ListView<AnchorPane> listView;

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
//        listView.getItems().clear();
//        List<Booking> bookingList = HotelCore.get().getAllBooking();
//       // bookingList.forEach(new Consumer<Booking>()
//        {
//            @Override
//            public void accept(Booking booking)
//            {
//                if (!listView.getItems().contains(booking))
//                    listView.getItems().add(booking);
//            }
//        });
//
//        listView.refresh();
    }

    public void onListViewItemClicked(MouseEvent event)
    {
//        Booking booking = listView.getSelectionModel().getSelectedItem();
//        if (booking != null)
//            selectedBookingId = booking.getBookingID();
    }

    public void onSaveClicked(ActionEvent event)
    {
        if (selectedBookingId == UNSELECTED)
        {
            Message.show(Alert.AlertType.WARNING, "", "Select Booking to save!");
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
            Message.show(Alert.AlertType.INFORMATION, "Successful", file.getName() + " saved in " + file.getPath());
        }
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		BookingContainerController booking = new BookingContainerController();
		//	booking.creatBookingContainer("mohamad", "1.2.2013", "34");
		             
			listView.getItems().add(booking.creatBookingContainer("mohamad", "1.2.2013", "34"));
	}

}
