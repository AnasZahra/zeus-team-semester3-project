package de.zuse.hotel.gui;

import java.net.URL;

import javafx.util.Duration;

import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoadingPageController implements Initializable
{
    @FXML
    AnchorPane anchor;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(5000), anchor);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0);

        fadeTransition.setOnFinished(e ->
        {
            try
            {
                Stage loginScreen = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
                Parent parent = fxmlLoader.load();
                Stage current = (Stage) anchor.getScene().getWindow();
                Scene scene = new Scene(parent, 1280, 720);
                loginScreen.setScene(scene);
                current.close();
                loginScreen.show();
                ((ControllerApi) fxmlLoader.getController()).onStart();
            } catch (Exception e1)
            {
                e1.printStackTrace();
            }

        });
        fadeTransition.play();

    }

}