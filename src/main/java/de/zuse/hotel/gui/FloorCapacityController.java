package de.zuse.hotel.gui;

import de.zuse.hotel.core.Floor;
import de.zuse.hotel.core.HotelCore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FloorCapacityController implements ControllerApi{

    @FXML
    private TextField capacityTextField;

    public void addCapacity(ActionEvent actionEvent) {

        if(capacityTextField.getText().isEmpty()){
            InfoController.showMessage(InfoController.LogLevel.Error,"Floor capacity","Please enter floor capacity!");
        }else{
            Floor newFloor = new Floor(HotelCore.get().getFloors().size() + 1, Integer.parseInt(capacityTextField.getText()));

            HotelCore.get().addNewFloorToHotel(newFloor);
            InfoController.showMessage(InfoController.LogLevel.Info, "Success", "Floor added Successfuly!");
            HotelCore.get().getCurrentStage().close();
        }
    }


    @Override
    public void onStart() {

    }

    @Override
    public void onUpdate() {

    }
}
