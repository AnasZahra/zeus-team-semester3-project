package de.zuse.hotel.gui;

import java.net.URL;

import de.zuse.hotel.util.ZuseCore;
import javafx.util.Duration;

import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoadingPageController implements Initializable
{
    @FXML
    AnchorPane anchor;
    Thread loadingThread;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        loadingThread = new Thread(() ->
        {
            Gui.startLoading();
            //after finish loading now move to the main window
            loadMainScene();
        });

        loadingThread.start();
    }

    public void loadMainScene()
    {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), anchor);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(1.0);

        fadeTransition.setOnFinished(value ->
        {
            try
            {
                loadingThread.join();

                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));

                Parent parent = fxmlLoader.load();
                Scene scene = new Scene(parent, 1280, 720);
                scene.getStylesheets().add(SettingsController.getCorrectStylePath("background.css"));
                stage.setScene(scene);
                ((Stage) anchor.getScene().getWindow()).close();
                stage.show();
                ((ControllerApi) fxmlLoader.getController()).onStart();
            } catch (Exception e)
            {
                if (ZuseCore.DEBUG_MODE)
                    e.printStackTrace();
            }

        });

        fadeTransition.play();
    }


}