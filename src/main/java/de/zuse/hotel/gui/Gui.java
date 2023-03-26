package de.zuse.hotel.gui;

import de.zuse.hotel.Layer;
import de.zuse.hotel.core.HotelCore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Gui extends Application implements Layer
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        //CSS
        //scene.getStylesheets().add(getClass().getResource("A.css").toExternalForm());
        String cssStyle = this.getClass().getResource("Styling/background.css").toExternalForm();
        scene.getStylesheets().add(cssStyle);
        //scene2.getStylesheets().add(cssStyle);


        stage.setTitle("Hotel v1.0");
        stage.setScene(scene);
        stage.show();

    }



}
