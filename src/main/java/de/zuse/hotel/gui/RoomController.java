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
        int currentFloor = floorChoiceBox.getValue() - 1;
        List<Room> rooms = HotelCore.get().getRooms(currentFloor);

        if (rooms != null)
        {
            ObservableList<Room> observableRooms = FXCollections.observableArrayList(rooms);
            roomTable.setItems(observableRooms);
        }

    }

    @Override
    public void onUpdate()
    {
        viewRoomData();
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
                {
                    TableView.TableViewSelectionModel<Room> selectionModel = roomTable.getSelectionModel();
                    currentSelectedRoom = selectionModel.getSelectedItem();
                } else
                {
                    currentSelectedRoom = null;
                }
            }
        });

        // set a default Floor 1
        List<Floor> floorlist = HotelCore.get().getFloors();
        floorlist.forEach(new Consumer<Floor>()
        {
            @Override
            public void accept(Floor floor)
            {
                floorChoiceBox.getItems().add(floor.getFloorNr());
            }
        });

        floorChoiceBox.setOnAction(this::onFloorChoiceChanged);
        if (floorlist.size() > 0)
            floorChoiceBox.setValue(floorlist.get(0).getFloorNr());
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
            return;

        BookingSearchFilter bookingSearchFilter = new BookingSearchFilter();
        bookingSearchFilter.roomNumber = currentSelectedRoom.getRoomNr();

        List<Booking> bookings = HotelCore.get().getBookingByFilter(bookingSearchFilter);
        if (bookings.size() > 0)
        {
            // Show message to confirm deleting
            boolean state = InfoController.showConfirmMessage(Alert.AlertType.WARNING, "Removing Room Warning"
                    , "There is/are " + bookings.size() + " with the room " + currentSelectedRoom.getRoomNr() +
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
                        HotelCore.get().removeBooking(booking.getBookingID());//Cancel all the bookings
                    }
                });
            }

        } else
        {
            HotelCore.get().removeRoomFromHotel(currentSelectedRoom.getFloorNr(), currentSelectedRoom.getRoomNr());
        }
    }


}