package de.zuse.hotel.gui;


import de.zuse.hotel.core.*;
import de.zuse.hotel.db.BookingSearchFilter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class RoomController implements ControllerApi
{
    public TableView<Room> roomTable;
    @FXML
    ChoiceBox<Integer> floorChoiceBox;

    @FXML
    private TableColumn<Room, Integer> roomNrCln;
    @FXML
    private TableColumn<Room, RoomSpecification.Types> roomTypeCln;
    @FXML
    private TableColumn<Room, Double> priceCln;

    private Room currentSelectedRoom = null;

    public void viewRoomData()
    {
        if (floorChoiceBox.getValue() == null || floorChoiceBox.getItems().size() < 0)
            return;

        List<Room> rooms = HotelCore.get().getFloorByFloorNr(floorChoiceBox.getValue()).getRooms();

        if (rooms != null)
            roomTable.setItems(FXCollections.observableArrayList(rooms));
    }

    public void refreschFloorData()
    {
        if (HotelCore.get().getFloors().size() != floorChoiceBox.getItems().size())
        {
            floorChoiceBox.getItems().clear();
            floorChoiceBox.getItems().addAll(HotelCore.get().getFloors().stream().map(Floor::getFloorNr).toList());
            if (floorChoiceBox.getItems().size() > 0)
                floorChoiceBox.setValue(floorChoiceBox.getItems().get(0));
        }
    }

    @Override
    public void onUpdate()
    {
        viewRoomData();
        refreschFloorData();
    }

    public void onStart()
    {
        roomNrCln.setCellValueFactory(new PropertyValueFactory<>("roomNr"));
        priceCln.setCellValueFactory(new PropertyValueFactory<>("price"));
        roomTypeCln.setCellValueFactory(new PropertyValueFactory<>("roomType"));

        roomTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        roomTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
        {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue)
            {
                if (roomTable.getSelectionModel().getSelectedItem() != null)
                    currentSelectedRoom = roomTable.getSelectionModel().getSelectedItem();
                else
                    currentSelectedRoom = null;
            }
        });

        // set a default Floor 1
        refreschFloorData();
        viewRoomData();

        floorChoiceBox.setOnAction(this::onFloorChoiceChanged);
        if (floorChoiceBox.getItems().size() > 0)
            floorChoiceBox.setValue(floorChoiceBox.getItems().get(0));
    }


    public void onFloorChoiceChanged(ActionEvent actionEvent)
    {
        viewRoomData();
    }

    @FXML
    void handleAddRoomButtonAction(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addRoom.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 331, 409);
        ((ControllerApi) fxmlLoader.getController()).onStart();

        Stage stage = new Stage();
        stage.setTitle("Add a room");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); //default, for closing th pop up window
        stage.show();
        stage.resizableProperty().setValue(false);
    }

    @FXML
    void handelRemoveRoomButton(ActionEvent event)
    {
        if (currentSelectedRoom == null)
        {
            InfoController.showMessage(InfoController.LogLevel.Info, "Delete Room", "Select Room to be deleted");
            return;
        }

        BookingSearchFilter bookingSearchFilter = new BookingSearchFilter();
        bookingSearchFilter.roomNumber = currentSelectedRoom.getRoomNr();
        bookingSearchFilter.canceled = false;
        List<Booking> bookings = HotelCore.get().getBookingByFilter(bookingSearchFilter);
        if (bookings.size() > 0)
        {
            // Show message to confirm deleting
            boolean state = InfoController.showConfirmMessage(InfoController.LogLevel.Warn, "Removing Room Warning"
                    , "There is/are " + bookings.size() + " booking(s) with the room " + currentSelectedRoom.getRoomNr() +
                            " in Floor " + currentSelectedRoom.getFloorNr() +
                            " ,deleting the room will cancel all the bookings with it");

            if (state)
            {
                HotelCore.get().removeRoomFromHotel(currentSelectedRoom.getFloorNr(), currentSelectedRoom.getRoomNr());
                bookings.forEach(new Consumer<Booking>()
                {
                    @Override
                    public void accept(Booking booking)
                    {
                        HotelCore.get().removeBooking(booking.getBookingID());//cancel all the bookings
                    }
                });
            }

            return;
        }


        if (InfoController.showConfirmMessage(InfoController.LogLevel.Warn, "Delete Room", "Are you sure?"))
        {
            HotelCore.get().removeRoomFromHotel(currentSelectedRoom.getFloorNr(), currentSelectedRoom.getRoomNr());
        }
    }

    public void addFloor(ActionEvent actionEvent) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("floorCapacity.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 287, 164);
        ((ControllerApi) fxmlLoader.getController()).onStart();
        Stage stage = new Stage();
        System.out.println(stage);
        stage.setTitle("Add a floor");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); //default, for closing th pop up window
        stage.show();
        HotelCore.get().setCurrentStage(stage);
    }

}