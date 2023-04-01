package de.zuse.hotel.gui;

import de.zuse.hotel.core.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.function.Consumer;

public class BookingWindow implements ControllerApi
{
    public Button closeBtnId;
    public TextField guestsNumber;
    @FXML
    TextField guestName;
    @FXML
    private DatePicker arrivalDate;
    @FXML
    private DatePicker depatureDate; // small leter pls jan

    @FXML
    private ChoiceBox<Integer> roomChoiceBox;

    @FXML
    private ChoiceBox<Payment.Type> paymentChoiceBox;

    @FXML
    void closeWindow()
    {
        Stage stage = (Stage) closeBtnId.getScene().getWindow();
        stage.close();
    }

    @FXML
    void addBooking(ActionEvent event) throws Exception
    {
        String gustname = guestName.getText();
        Person person = HotelCore.get().getGuest(1);
        if (person == null)//Delete Later
        {
            person = new Person("basel", "saad", LocalDate.of(1999, 5, 22), "email@gmail"
                    , "123456789111", new Address("de", "vk", "st", 66333, 24));

            HotelCore.get().addGuest(person);
        }

        Booking booking = new Booking(0, 0, arrivalDate.getValue(), depatureDate.getValue(), person);
        booking.getPayment().type = paymentChoiceBox.getValue();
        booking.addExtraService("Dinner");
        booking.addExtraService("Wifi");
        HotelCore.get().addBooking(booking);
   
        closeWindow();
    }

    @Override
    public void onStart()
    {
        // Set payment ChoiceBox values
        Arrays.stream(Payment.Type.values()).toList().forEach(new Consumer<Payment.Type>()
        {
            @Override
            public void accept(Payment.Type type)
            {
                paymentChoiceBox.getItems().add(type);
            }
        });
        paymentChoiceBox.setValue(Payment.Type.CASH);

        JavaFxUtil.makeFieldOnlyNumbers(guestsNumber); //guestsNumber Take only numbers
        JavaFxUtil.makeFieldOnlyChars(guestName);// for guest Name take only chars
    }

    @Override
    public void onUpdate()
    {
    }
}
