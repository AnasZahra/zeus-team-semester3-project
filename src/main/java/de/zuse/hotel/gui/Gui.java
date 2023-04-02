package de.zuse.hotel.gui;

import de.zuse.hotel.Layer;
import de.zuse.hotel.core.HotelCore;
import de.zuse.hotel.util.ZuseCore;
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
        ZuseCore.setCallbackError(this::handleErrorMessages);
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
        HotelCore.shutdown();
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoadingPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        //CSS
        String cssStyle = this.getClass().getResource("Styling/background.css").toExternalForm();
        scene.getStylesheets().add(cssStyle);
        stage.setTitle("Hotel v1.0");
        stage.setScene(scene);
        stage.show();
    }

    public void handleErrorMessages(String msg)
    {
        InfoController.showMessage(InfoController.LogLevel.Error, "Error", msg);
    }

    public static void startLoading()
    {
        HotelCore.init();
    }
}