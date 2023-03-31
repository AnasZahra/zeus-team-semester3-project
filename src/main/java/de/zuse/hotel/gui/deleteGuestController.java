package de.zuse.hotel.gui;

import de.zuse.hotel.core.Booking;
import de.zuse.hotel.core.HotelCore;
import de.zuse.hotel.db.BookingSearchFilter;
import de.zuse.hotel.db.JDBCConnecter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

public class deleteGuestController implements ControllerApi
{
    @FXML
    private TextField guestId;

    @FXML
    void deleteGuest(ActionEvent actionEvent) throws Exception
    {
        if (guestId.getText().strip().isEmpty())
        {
            InfoController.showMessage(InfoController.LogLevel.Error,"Delete Guest","No Guest-ID was entered!");
        }

        int id = Integer.parseInt(guestId.getText());

        //Cancel Booking with guest
        {
            BookingSearchFilter bookingSearchFilter = new BookingSearchFilter();
            bookingSearchFilter.guest = HotelCore.get().getGuest(id);

            if (bookingSearchFilter.guest == null)
            {
                InfoController.showMessage(InfoController.LogLevel.Error,"Delete Guest"
                        ,"The Guest id you is not valid");
            }

            List<Booking> bookings = HotelCore.get().getBookingByFilter(bookingSearchFilter);
            if (bookings.size() > 0)
            {
                boolean state = InfoController.showConfirmMessage(Alert.AlertType.WARNING,"Cancel Booking"
                        , "The guest you try to delete has booking" +
                        ",delete the guest will cancel all his booking/s ?");

                if (state)
                {
                    bookings.forEach(new Consumer<Booking>()
                    {
                        @Override
                        public void accept(Booking booking)
                        {
                            HotelCore.get().removeBooking(booking.getBookingID());
                        }
                    });
                }

            }
        }

        boolean state = HotelCore.get().removeGuest(id);
        if (state)
            InfoController.showMessage(InfoController.LogLevel.Info,"Delete Guest","Guest deleted Successfully");

        HotelCore.get().getCurrentStage().close();
    }

    @Override
    public void onStart()
    {
        JavaFxUtil.makeFieldOnlyNumbers(guestId);
    }

    @Override
    public void onUpdate(){}

}
