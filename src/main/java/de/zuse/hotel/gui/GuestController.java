package de.zuse.hotel.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GuestController implements ControllerApi
{



    @Override
    public void onStart()
    {

    }

    @Override
    public void onUpdate()
    {

    }


    public void addGuest(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addGuest.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 331, 720);
        Stage stage = new Stage();
        stage.setTitle("Add a Guest");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); //default, for closing th pop up window
        stage.show();
    }

    public void deleteGuest(ActionEvent event) throws Exception
    {

    }

    public void updateGuest(ActionEvent event) throws Exception
    {

    }

}
