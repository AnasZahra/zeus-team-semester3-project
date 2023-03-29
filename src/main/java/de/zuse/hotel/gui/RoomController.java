package de.zuse.hotel.gui;


import de.zuse.hotel.core.Floor;
import de.zuse.hotel.core.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import de.zuse.hotel.core.HotelCore;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.Consumer;

public class RoomController implements ControllerApi {

    private int curentfloor;
    @FXML
    ChoiceBox<Integer> FloorChoiceBox;

    public ListView<Room> roomslistid;

    public void viewRoomData()
    {
        int floorChoiceBox = FloorChoiceBox.getValue() -1; //TODO hir the Indext is needet
        roomslistid.getItems().clear();
        List<Room> roomList = HotelCore.get().getRooms(floorChoiceBox);
        roomList.forEach(new Consumer<Room>()
        {
            @Override
            public void accept(Room room)
            {
                if (!roomslistid.getItems().contains(room))
                    roomslistid.getItems().add(room);
            }
        });

        roomslistid.refresh();
    }

    @Override
    public void onStart() { // set a defult Floor 1

        List<Floor> floorlist =  HotelCore.get().getFloors();
        floorlist.forEach(new Consumer<Floor>() {
            @Override
            public void accept(Floor floor) {
                FloorChoiceBox.getItems().add(floor.getFloorNr());
            }
        });

        FloorChoiceBox.setOnAction(this::onFloorChoiceChanged);
        FloorChoiceBox.setValue(floorlist.get(0).getFloorNr() );
    }


    public void onFloorChoiceChanged (ActionEvent actionEvent)
    {
        viewRoomData();

    }


    @Override
    public void onUpdateDb() {}


    @FXML
    void handleAddRoomButtonAction(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addRoom.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 331, 409);
        Stage stage = new Stage();
        stage.setTitle("Add a room");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); //default, for closing th pop up window
        stage.show();

    }
}
