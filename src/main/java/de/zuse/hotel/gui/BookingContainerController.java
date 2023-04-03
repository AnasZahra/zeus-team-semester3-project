package de.zuse.hotel.gui;

import java.io.IOException;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
    public Label bookingId;

    public BookingContainerController()
    {

    }

    public int getBookingId()
    {
        return Integer.parseInt(bookingId.getText());
    }

    public Node getContent() {
        return bookingContainer;
    }

    public void setStyle()
    {
        bookingContainer.getStylesheets().add(SettingsController.getCorrectStylePath("background.css"));
    }
}


