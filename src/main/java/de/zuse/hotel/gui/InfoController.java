package de.zuse.hotel.gui;

import de.zuse.hotel.core.HotelCore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class InfoController
{
    @FXML
    private Text displayText;

    public enum LogLevel
    {
        Info, Warn, Error
    }

    public static void showMessage(LogLevel alertType, String title, String content)
    {
        try
        {
            InfoController infoController = new InfoController();
            FXMLLoader fxmlLoader = new FXMLLoader(infoController.getClass().getResource("info.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            //give information about what happened after adding a guest if it went well or wrong
            infoController = fxmlLoader.getController();
            infoController.setText(content);
            stage.show();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static boolean showConfirmMessage(Alert.AlertType type, String title, String content)
    {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
        Alert alert = new Alert(Alert.AlertType.WARNING, content, yes, no);

        alert.setTitle(title);
        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get().equals(yes);
    }

    @FXML
    void setText(String msg)
    {
        displayText.setText(msg);
    }

    @FXML
    void cancel(ActionEvent event)
    {
        ((Stage) displayText.getScene().getWindow()).close();
    }
}
