package de.zuse.hotel.gui;

import de.zuse.hotel.core.Booking;
import de.zuse.hotel.core.HotelCore;
import de.zuse.hotel.util.pdf.PdfFile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    public  ListView<AnchorPane> listView;
    @FXML
    private Button deleteBookingBtn;
    
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
    public void addBookingToDashboard(String l1,String l2,String l3,String l4) throws IOException {
    	
		
    	BookingContainerController booking = new BookingContainerController();
		             
			
			listView.getItems().add(booking.getBookingContainer(l1, l2, l3,l4));
    	
    }
    
    public void deleteBooking(ActionEvent event) {
    	int selectedId = listView.getSelectionModel().getSelectedIndex();
    	listView.getItems().remove(selectedId);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			addBookingToDashboard("Mohamad","1.4.2014","2.4.2014","7");
			addBookingToDashboard("Mohamad","1.4.2014","2.4.2014","7");
			addBookingToDashboard("Mohamad","1.4.2014","2.4.2014","7");
			addBookingToDashboard("Mohamad","1.4.2014","2.4.2014","7");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
	}

}
