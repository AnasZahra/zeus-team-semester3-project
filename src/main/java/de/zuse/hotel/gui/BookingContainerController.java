package de.zuse.hotel.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BookingContainerController {
	
	

	@FXML
    public AnchorPane bookingContainer;

    @FXML
    public Label arrivalDate;

    @FXML
    public Label departureDate;

    @FXML
    public Label guestName;

    @FXML
    public Label personNr;
    
   public BookingContainerController() {
	  
	}

    public BookingContainerController(AnchorPane bookingContainer, Label bookingDate, Label guestName, Label personNr) {

		
	}
    
    public AnchorPane getBookingContainer(String l1, String l2, String l3,String l4) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("BookingContainer.fxml"));
    	bookingContainer  = loader.load();
    
    	
    	guestName = (Label) bookingContainer.lookup("#guestName");
    	arrivalDate = (Label) bookingContainer.lookup("#arrivalDate");
    	departureDate = (Label) bookingContainer.lookup("#departureDate");
    	personNr = (Label) bookingContainer.lookup("#personNr");
    	guestName.setText(l1);
    	arrivalDate.setText(l2);
    	departureDate.setText(l3);
    	 personNr.setText(l4);

  	   
  	   return bookingContainer;
  	
    }

}


