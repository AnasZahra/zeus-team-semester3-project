package de.zuse.hotel.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class BookingContainerController
{
    @FXML
    public AnchorPane bookingContainer;
    @FXML
    public Label arrivalDate;
    @FXML
    public Label departureDate;
    @FXML
    public Label guestName;
    @FXML
    public Label roomAndFloor;
    public Button canceledBooking;

    private int bookingID;

    public BookingContainerController()
    {

    }

    public void setBookingID(int id)
    {
        bookingID = id;
    }

    public int getBookingID()
    {
        return bookingID;
    }

    public Node getContent()
    {
        return bookingContainer;
    }

    public void setStyle(boolean canceld)
    {
        bookingContainer.getStylesheets().add(SettingsController.getCorrectStylePath("background.css"));
        canceledBooking.setVisible(canceld);
    }
}


