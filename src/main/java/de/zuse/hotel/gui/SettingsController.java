package de.zuse.hotel.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SettingsController implements Initializable
{
    public enum SystemMode
    {
        LIGHT, DARK;
    }

    @FXML
    AnchorPane anchor;
    @FXML
    Button imageD;
    @FXML
    Button imageL;

    public static SystemMode currentMode = SystemMode.DARK;
    //relative path to project.dir

    private static class Wrapper{}

    public void changeToDarkmood()
    {
        imageD.setOnMouseClicked(e ->
        {
            if (currentMode == SystemMode.DARK)
                return;

            currentMode = SystemMode.DARK;
            Gui.getInstance().restartApp();
            ((Stage) anchor.getScene().getWindow()).close();
        });
    }

    public void changeToLightmood()
    {
        imageL.setOnMouseClicked(e ->
        {
            if (currentMode == SystemMode.LIGHT)
                return;

            currentMode = SystemMode.LIGHT;
            Gui.getInstance().restartApp();
            ((Stage) anchor.getScene().getWindow()).close();
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        changeToDarkmood();
        changeToLightmood();
    }

    public static String getCorrectStylePath(String fileName)
    {
        Wrapper wrapper = new Wrapper();

        switch (currentMode)
        {
            case DARK:
                return wrapper.getClass().getResource("styling/darkMode/" + fileName).toExternalForm();
            case LIGHT:
                return wrapper.getClass().getResource("styling/lightMode/" + fileName).toExternalForm();
        }

        return wrapper.getClass().getResource("styling/darkMode/" + fileName).toExternalForm();
    }

    public static SystemMode getMode()
    {
        return currentMode;
    }

    public static void setMode(SystemMode mode)
    {
        currentMode = mode;
    }

}
