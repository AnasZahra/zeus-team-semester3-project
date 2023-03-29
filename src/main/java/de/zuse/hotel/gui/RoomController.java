package de.zuse.hotel.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class RoomController implements ControllerApi
{
    @Override
    public void onStart()
    {

    }

    @Override
    public void onUpdate()
    {

    }

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
