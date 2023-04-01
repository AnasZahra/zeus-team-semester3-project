package de.zuse.hotel.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InfoController
{
    public Button yesBtn;
    public Button noBtn;
    public Button okBtn;
    @FXML
    private Text displayText;

    private boolean answer;

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
            infoController.okBtn.setVisible(true);
            infoController.yesBtn.setVisible(false);
            infoController.noBtn.setVisible(false);
            stage.show();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static boolean showConfirmMessage(LogLevel type, String title, String content)
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
            infoController.okBtn.setVisible(false);
            infoController.yesBtn.setVisible(true);
            infoController.noBtn.setVisible(true);
            stage.showAndWait();

            return infoController.answer;
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
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

    @FXML
    void onYesBtnClicked(ActionEvent event)
    {
        answer = true;
        cancel(event);
    }

    @FXML
    void onNoBtnClicked(ActionEvent event)
    {
        answer = false;
        cancel(event);
    }


}
