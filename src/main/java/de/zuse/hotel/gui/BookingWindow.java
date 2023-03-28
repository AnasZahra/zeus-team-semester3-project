package de.zuse.hotel.gui;

import de.zuse.hotel.core.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class BookingWindow implements Initializable {
    @FXML
    TextField nameFill;
    @FXML
    private DatePicker ArrivalDate;
    @FXML
    private DatePicker DepatureDate; // small leter pls jan

    @FXML
    private ChoiceBox<Integer> RoomChoiceBox;

    @FXML
    private ChoiceBox<Payment.Type> payment_ChoiceBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    void handleBookRoomButtonAction(ActionEvent event) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bookingWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 720);
        Stage stage = new Stage();
        stage.setTitle("Book a room");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); //default, for closing th pop up window
        stage.show();
    }

    @FXML
    void addBooking (ActionEvent event)throws Exception{
        String gustname = nameFill.getText();

        LocalDate arrivalDate = ArrivalDate.getValue();
        LocalDate depatureDate = DepatureDate.getValue();

        Payment.Type pymentType = payment_ChoiceBox.getValue();
        int roomChoiceBox = RoomChoiceBox.getValue();

        List<Room> roomlist =  HotelCore.get().getRooms(0 );
        Integer[] arr = new Integer[roomlist.size()];
        for (int i = 0; i< roomlist.size();i++)
        {
            Room room = roomlist.get(i);
            if (room != null)
                arr[i] = room.getRoomNr();
        }

        RoomChoiceBox.getItems().addAll(arr);
        payment_ChoiceBox.getItems().addAll(Payment.Type.values());
        /*
        Booking booking = new Booking(roomChoiceBox, floor,  arrivalDate, depatureDate, );

        HotelCore.get().addBooking(booking);
        */
    }




}
