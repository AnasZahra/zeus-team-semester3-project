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
    public Label bookingDate;

    @FXML
    public Label guestName;

    @FXML
    public Label personNr;
    
   public BookingContainerController() {
	   bookingContainer = new AnchorPane();
	   bookingDate = new Label();
	   guestName = new Label();
	   personNr = new Label();
	   bookingContainer.getChildren().addAll(bookingDate,guestName,personNr);
	}

    public BookingContainerController(AnchorPane bookingContainer, Label bookingDate, Label guestName, Label personNr) {

		super();
		this.bookingContainer = bookingContainer;
		this.bookingDate = bookingDate;
		this.guestName = guestName;
		this.personNr = personNr;
	}
    
    public AnchorPane getBookingContainer(String l1, String l2, String l3) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("BookingContainer.fxml"));
    	bookingContainer  = loader.load();
    
    	
    	guestName = (Label) bookingContainer.lookup("#guestName");
    	bookingDate = (Label) bookingContainer.lookup("#date");
    	personNr = (Label) bookingContainer.lookup("#personNr");
    	guestName.setText(l1);
    	bookingDate.setText(l2);
    	 personNr.setText(l3);
//       bookingContainer = new AnchorPane();
//       bookingContainer.setPrefHeight(100);
//  	   bookingDate = new Label();
//  	   bookingDate.setText(l2);
//  	   bookingDate.setFont(Font.font(30));
//  	   guestName = new Label();
//  	   guestName.setText(l1);
//  	   guestName.setFont(Font.font(30));
//  	   personNr = new Label();
//  	   personNr.setText(l3);
//  	   personNr.setFont(Font.font(30));
//       AnchorPane.setLeftAnchor(guestName, 10.0);
//       AnchorPane.setLeftAnchor(bookingDate, 200.0);
//       AnchorPane.setLeftAnchor(personNr, 400.0);
//       bookingContainer.setStyle("-fx-background-color: orange;"
//               + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 10, 0.0, 0, 4);"
//               + "-fx-border-color: white;"
//               + "-fx-border-width: 2px;"
//               + "-fx-border-radius: 10px;");
//       
//  	   bookingContainer.getChildren().addAll(bookingDate,guestName,personNr);
  	   
  	   return bookingContainer;
  	
    }

}


