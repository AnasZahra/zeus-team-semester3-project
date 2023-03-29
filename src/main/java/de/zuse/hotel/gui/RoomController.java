package de.zuse.hotel.gui;


import de.zuse.hotel.core.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class RoomController implements ControllerApi {

    @FXML
    TextField roomnumber;
    @FXML
    ChoiceBox<Integer> floorChoiceBox;

    private TableColumn<Room , Integer> roomNrCln;
    private TableColumn<Room , RoomSpecification.Types> roomTypeCln;
    private TableColumn<Room , Integer> priceCln;

    public TextField roomprice;
    @FXML
    ChoiceBox<RoomSpecification.Types> roomType;

    public void viewRoomData()
    {
        int floorcount = floorChoiceBox.getValue() -1; //TODO hir the Indext is needet
       /* roomslistid.getItems().clear();
        List<Room> roomList = HotelCore.get().getRooms(floorcount);
        roomList.forEach(new Consumer<Room>()
        {
            @Override
            public void accept(Room room)
            {
                if (!roomslistid.getItems().contains(room))
                    roomslistid.getItems().add(room);
            }
        });*/

        //roomslistid.refresh();
    }

    @Override
    public void onUpdate() {
        viewRoomData();
    }

    public void onStart() { // set a defult Floor 1

        List<Floor> floorlist =  HotelCore.get().getFloors();
        floorlist.forEach(new Consumer<Floor>() {
            @Override
            public void accept(Floor floor) {
                floorChoiceBox.getItems().add(floor.getFloorNr());
            }
        });

        floorChoiceBox.setOnAction(this::onFloorChoiceChanged);
        floorChoiceBox.setValue(floorlist.get(0).getFloorNr() );

    }


    public void onFloorChoiceChanged (ActionEvent actionEvent)
    {
        viewRoomData();
    }


    @FXML
    void handleAddRoomButtonAction(ActionEvent event) throws Exception
    {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addRoom.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 331, 409);
        ControllerApi dashboardController = (ControllerApi) fxmlLoader.getController();
        dashboardController.onStart();
        Stage stage = new Stage();
        stage.setTitle("Add a room");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); //default, for closing th pop up window
        stage.show();
    }



}