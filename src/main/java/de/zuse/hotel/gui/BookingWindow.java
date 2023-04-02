package de.zuse.hotel.gui;

import de.zuse.hotel.core.*;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BookingWindow implements ControllerApi
{
    public Button closeBtnId;
    public TextField guestsNumber;
    public TextField priceField;
    public ChoiceBox<Floor> floorChoiceBox;
    @FXML
    private ChoiceBox<Room> roomChoiceBox;
    public CheckBox paidCheckBox;
    @FXML
    private TextField guestID;
    @FXML
    private DatePicker arrivalDate;
    @FXML
    private DatePicker depatureDate; // small leter pls jan
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
        if (guestID.getText().strip().isEmpty() || paymentChoiceBox.getValue() == null)
        {
            InfoController.showMessage(InfoController.LogLevel.Warn, "Add Booking", "can not add Booking" +
                    ", please fill all information!");

            return;
        }

        int id = Integer.parseInt(guestID.getText());
        int floorNr = floorChoiceBox.getValue().getFloorNr();
        int roomNr = roomChoiceBox.getValue().getRoomNr();
        int guestNum = Integer.parseInt(guestsNumber.getText());
        Payment.Type paymentType = paymentChoiceBox.getValue();
        Person guest = HotelCore.get().getGuest(id);

        if (guest == null)
        {
            InfoController.showMessage(InfoController.LogLevel.Warn, "Add Booking", "could not find guest id!");
            return;
        }

        Booking booking = new Booking(roomNr, floorNr, arrivalDate.getValue(), depatureDate.getValue(), guest);
        booking.setGuestsNum(guestNum);

        if (paidCheckBox.isSelected())
            booking.pay(LocalDate.now(), paymentType, Float.parseFloat(priceField.getText()));

        boolean state = HotelCore.get().addBooking(booking);
        if (state)
            InfoController.showMessage(InfoController.LogLevel.Info, "Add Booking", "Booking added successfully");

        closeWindow();
    }

    @Override
    public void onStart()
    {
        // Set payment ChoiceBox values and default value
        List<Payment.Type> typeList = Arrays.stream(Payment.Type.values()).collect(Collectors.toList());
        paymentChoiceBox.getItems().addAll(typeList);
        paymentChoiceBox.setValue(Payment.Type.CASH);

        JavaFxUtil.makeFieldOnlyNumbers(guestsNumber); //guestsNumber Take only numbers
        JavaFxUtil.makeFieldOnlyNumbers(guestID);// for guest Name take only chars
        JavaFxUtil.makeFieldOnlyNumericWithDecimal(priceField);

        priceField.setEditable(false);

        setChoiceBoxStringConverter();
        floorChoiceBox.getItems().addAll(HotelCore.get().getFloors());

        //callbacks
        floorChoiceBox.setOnAction(this::onDateChangeListener);
        arrivalDate.setOnAction(this::onDateChangeListener);
        depatureDate.setOnAction(this::onDateChangeListener);
        roomChoiceBox.setOnAction(this::changePriceField);

        if (floorChoiceBox.getItems().size() > 0) //default value
            floorChoiceBox.setValue(floorChoiceBox.getItems().get(0));
    }

    @Override
    public void onUpdate()
    {
    }

    /**
     * This is a very expensive method (performance), we have to optimize it in feature, Maybe :)
     */
    public void showAvailableRooms(ObservableValue<? extends Floor> observable, Floor oldValue, Floor newValue)
    {
        roomChoiceBox.getItems().clear();

        // we do not show rooms or floor until the client set the start and end date of booking
        if (arrivalDate.getValue() == null || depatureDate.getValue() == null || floorChoiceBox.getValue() == null)
            return;

        List<Booking> bookingList = HotelCore.get().getAllBookingBetweenStartAndEnd(arrivalDate.getValue()
                , depatureDate.getValue());

        //Booked roomNr(s) between start and end
        List<Integer> bookedRooms = bookingList.stream()
                .filter(booking -> !booking.isCanceled())
                .map(Booking::getRoomNumber)
                .collect(Collectors.toList());

        roomChoiceBox.getItems().addAll(newValue.getRooms()
                .stream()
                .filter(room -> !bookedRooms.contains(room.getRoomNr()))
                .toList());

        if (roomChoiceBox.getItems().size() > 0) //default value
            roomChoiceBox.setValue(roomChoiceBox.getItems().get(0));
    }

    public void onDateChangeListener(ActionEvent event)
    {
        showAvailableRooms(floorChoiceBox.getSelectionModel().selectedItemProperty()
                , floorChoiceBox.getValue(), floorChoiceBox.getValue());
    }

    public void changePriceField(ActionEvent event)
    {
        if (roomChoiceBox.getValue() != null)
        {
            priceField.setText(String.valueOf(roomChoiceBox.getValue().getPrice()));
        }
    }

    private void setChoiceBoxStringConverter()
    {
        roomChoiceBox.setConverter(new StringConverter<Room>()
        {
            @Override
            public String toString(Room room)
            {
                String toString = "";
                if (room != null)
                    toString = String.valueOf(room.getRoomNr());

                return toString;
            }

            @Override
            public Room fromString(String string)
            {
                return null;
            }
        });

        floorChoiceBox.setConverter(new StringConverter<Floor>()
        {
            @Override
            public String toString(Floor floor)
            {
                String toString = "";
                if (floor != null)
                    toString = String.valueOf(floor.getFloorNr());

                return toString;
            }

            @Override
            public Floor fromString(String string)
            {
                return null;
            }
        });
    }

}
