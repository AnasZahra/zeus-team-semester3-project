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
            Stage loginScreen = new Stage();
            Parent root = null;

            try
            {
                root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            } catch (Exception e1)
            {
                e1.printStackTrace();
            }

            Stage current = (Stage) anchor.getScene().getWindow();
            Scene scene = new Scene(root, 1280, 720);

            loginScreen.setScene(scene);

            current.hide();
            loginScreen.show();


        });
        fadeTransition.play();

    }

}
