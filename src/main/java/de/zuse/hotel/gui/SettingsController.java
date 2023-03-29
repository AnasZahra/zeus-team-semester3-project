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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SettingsController implements ControllerApi
{
    // private static final String PREF_FONT_SIZE = "font_size";
    @FXML
    private Button saveButton;
    @FXML
    Slider sliderFont;
    @FXML
    Slider sliderColor;
    @FXML
    Label labelSetting;
    @FXML
    AnchorPane pane;

    public void changeFont()
    {
        // Load the font size from preferences
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        double fontSize = prefs.getDouble("font_size", 12.0);
        labelSetting.setFont(Font.font(fontSize));

        // Add a listener to the slider
        sliderFont.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            labelSetting.setFont(Font.font(newValue.doubleValue()));

            // Save the font size to preferences
            prefs.putDouble("font_size", newValue.doubleValue());
            sliderFont.setValue(newValue.doubleValue());
        });
    }

    @Override
    public void onStart()
    {
        changeFont();

        sliderColor.valueProperty().addListener((o, n1, n2) ->
        {
            if (n1.doubleValue() == 0)
            {
                pane.setStyle("-fx-background-color: grey;");
            }
            if (n2.doubleValue() == 0.5)
            {
                labelSetting.setTextFill(Color.YELLOW);
            }
            if (n2.doubleValue() == 1)
            {
                pane.setStyle("-fx-background-color: yellow;");
            }
        });


        saveButton.setOnAction(e ->
        {
            try{
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Dashboard.fxml")));
                Stage stage = (Stage) saveButton.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception exception) {
                ZuseCore.check(false, exception.getMessage());
            }
        });
    }

    @Override
    public void onUpdateDb()
    {
    }
}
