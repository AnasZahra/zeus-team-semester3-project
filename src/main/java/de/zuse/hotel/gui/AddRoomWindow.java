package de.zuse.hotel.gui;

import de.zuse.hotel.core.Floor;
import de.zuse.hotel.core.HotelCore;
import de.zuse.hotel.core.Room;
import de.zuse.hotel.core.RoomSpecification;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class AddRoomWindow implements ControllerApi{

    public TextField roomprice;

    @FXML
    TextField roomnumber;

    @FXML
    ChoiceBox<Integer> floorChoiceBox;

    @FXML
    ChoiceBox<RoomSpecification.Types> roomType;


    @Override
    public void onStart() {

        Arrays.stream(RoomSpecification.Types.values()).toList().forEach(new Consumer<RoomSpecification.Types>() {
            @Override
            public void accept(RoomSpecification.Types types) {
                roomType.getItems().add(types);
            }
        });

        roomnumber.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*"))
                    roomnumber.setText(newValue.replaceAll("[^\\d]", ""));

            }
        });

        roomprice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")){
                    roomprice.setText(newValue.replaceAll("[^\\d]", ""));
                }

            }
        });

        List<Floor> floorlist =  HotelCore.get().getFloors();
        floorlist.forEach(new Consumer<Floor>() {
            @Override
            public void accept(Floor floor) {
                floorChoiceBox.getItems().add(floor.getFloorNr());
            }
        });

        floorChoiceBox.setValue(floorlist.get(0).getFloorNr() );
    }

    @Override
    public void onUpdate()
    {

    }


    public void addingRoom (ActionEvent actionEvent) throws Exception //TODO hir the Indext is needet
    {
        String roomnum = roomnumber.getText();
        String price = roomprice.getText();
        Room room = new Room(HotelCore.get().getFloors().get(floorChoiceBox.getValue()-1), Integer.parseInt(roomnum) , Integer.parseInt(price));
        HotelCore.get().getHotelConfig().addNewRoom(room.getFloorNr() -1 ,room );  //TODO hir the Indext is needet
        closeWindow();

    }

    @FXML
    void closeWindow()
    {
        Stage stage = (Stage) roomnumber.getScene().getWindow();
        stage.close();
    }



}
