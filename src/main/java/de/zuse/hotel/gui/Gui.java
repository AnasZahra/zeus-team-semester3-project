package de.zuse.hotel.gui;

import de.zuse.hotel.Layer;
import de.zuse.hotel.core.HotelCore;
import de.zuse.hotel.util.HotelSerializer;
import de.zuse.hotel.util.ZuseCore;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class Gui extends Application implements Layer
{
    private static Gui instance = null;
    private Image image;

    public static Gui getInstance()
    {
        if (instance == null)
            instance = new Gui();

        return instance;
    }

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
        System.out.println("Closing the App");
        HotelCore.shutdown();
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoadingPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        scene.getStylesheets().add(SettingsController.getCorrectStylePath("background.css"));
        stage.setTitle("Hotel v1.0");
        stage.setScene(scene);
        stage.show();
    }

    public void startLoading()
    {
        HotelCore.init();
        image = new Image(getClass().getResource("images/settingsBackground.gif").toExternalForm());
    }

    public void handleErrorMessages(String msg)
    {
        InfoController.showMessage(InfoController.LogLevel.Error, "Error", msg);
    }

    public void restartApp()
    {
        Platform.runLater(() ->
        {
            try
            {
                this.start(new Stage());
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        });
    }

    public Image getImage()
    {
        return image;
    }

}