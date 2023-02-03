package de.zuse.hotel.gui;

import de.zuse.hotel.Layer;
import de.zuse.hotel.core.HotelCore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ExampleLayer extends Application implements Layer
{
    @Override
    public void onStart()
    {
        System.out.println("On Starting The Hotel App...");

        HotelCore.init();
    }

    @Override
    public void run(String[] args)
    {
        launch(args);
    }

    @Override
    public void onClose()
    {
        System.out.println("On Closing The Hotel App...");
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LabelMessage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hotel v1.0");
        stage.setScene(scene);
        stage.show();
    }

}
