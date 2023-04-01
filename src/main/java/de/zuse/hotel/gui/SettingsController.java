package de.zuse.hotel.gui;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import de.zuse.hotel.util.ZuseCore;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SettingsController implements ControllerApi, Initializable
{
    @FXML
    AnchorPane anchor;
    @FXML
    ImageView imageD;
    @FXML
    ImageView imageL;

    public void changeToDarkmood()
    {
        imageD.setOnMouseClicked(e ->
        {
            anchor.getParent().setStyle("-fx-background-color: black; -fx-border-color: #27ae60; -fx-border-width: 2px; -fx-padding: 10px;");
            // anchor.setStyle("-fx-background-color: black; -fx-border-color: #27ae60; -fx-border-width: 2px; -fx-padding: 10px;");
            System.out.println("change to darkmood");
        });
    }

    public void changeToLightmood()
    {
        imageL.setOnMouseClicked(e ->
        {
            anchor.getParent().setStyle("-fx-background-color: white; -fx-border-color: #27ae60; -fx-border-width: 2px; -fx-padding: 10px;");
            // anchor.setStyle("-fx-background-color: white; -fx-border-color: #27ae60; -fx-border-width: 2px; -fx-padding: 10px;");
            System.out.println("change to lightmood");
        });
    }

    @Override
    public void onStart()
    {
    }

    @Override
    public void onUpdate()
    {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        changeToDarkmood();
        changeToLightmood();
    }
}
