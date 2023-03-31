package de.zuse.hotel.gui;

import de.zuse.hotel.core.HotelCore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.hibernate.annotations.Parent;

public class SceneController
{
    private static final String BUTTON_SELECTED_STYLE_NAME = "on_button_selected";

    @FXML
    private Button guestBtnId;
    @FXML
    private Button roomsBtnId;
    @FXML
    private Button settingsBtnId;

    @FXML
    private Button dashboardBtnId;

    @FXML
    private BorderPane borderPane;


    public SceneController(){}

    public void onClickDashboardBtn(ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Node node = fxmlLoader.load();
        ((ControllerApi) fxmlLoader.getController()).onStart();
        HotelCore.get().setCurrentScene(fxmlLoader.getController());
        borderPane.setCenter(node);
        onSwitchPanel(dashboardBtnId);
    }

    public void onClickRoomBtn(ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Rooms.fxml"));
        Node node = fxmlLoader.load();
        ((ControllerApi) fxmlLoader.getController()).onStart();
        HotelCore.get().setCurrentScene(fxmlLoader.getController());

        borderPane.setCenter(node);
        onSwitchPanel(roomsBtnId);

    }

    public void onClickGuestBtn(ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Guest.fxml"));
        Node node = fxmlLoader.load();
        ((ControllerApi) fxmlLoader.getController()).onStart();
        HotelCore.get().setCurrentScene(fxmlLoader.getController());
        borderPane.setCenter(node);
        onSwitchPanel(guestBtnId);
    }

    public void onClickSettingsBtn(ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Settings.fxml"));
        Node node = fxmlLoader.load();
        HotelCore.get().setCurrentScene(fxmlLoader.getController());
        borderPane.setCenter(node);
        onSwitchPanel(settingsBtnId);
    }

    public void resetButtonsStyle()
    {
        //reset
        dashboardBtnId.getStyleClass().removeAll(BUTTON_SELECTED_STYLE_NAME);
        settingsBtnId.getStyleClass().removeAll(BUTTON_SELECTED_STYLE_NAME);
        roomsBtnId.getStyleClass().removeAll(BUTTON_SELECTED_STYLE_NAME);
        guestBtnId.getStyleClass().removeAll(BUTTON_SELECTED_STYLE_NAME);

        dashboardBtnId.setStyle("");
        settingsBtnId.setStyle("");
        roomsBtnId.setStyle("");
        guestBtnId.setStyle("");
    }

    private void onSwitchPanel(Button selectedBtn)
    {
        resetButtonsStyle();
        selectedBtn.getStyleClass().add(BUTTON_SELECTED_STYLE_NAME);
        selectedBtn.setStyle("-fx-cursor: hand; -fx-text-fill: #ffffff;"); //disable hovor effect
    }

    @FXML
    void handleBookRoomButtonAction(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BookingWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 720);
        ((ControllerApi)fxmlLoader.getController()).onStart();
        Stage stage = new Stage();
        stage.setTitle("Book a room");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); //default, for closing th pop up window
        stage.show();
        stage.resizableProperty().setValue(false);
    }
}
