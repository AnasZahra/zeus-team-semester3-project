package de.zuse.hotel.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class sceneController
{

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField nameField;

    @FXML
    private TextField roomField;

    @FXML
    private Button  roundButton;
    /**
    //handleBookRoomButtonAction is an booking button event
    public void initialize() {
        TextField roundButton = new TextField();
        roundButton.setOnAction(this::handleBookRoomButtonAction);
    }
*/

    //stage = (Stage)((Node)event.getSource()).getScene().getWindow();

    /**
     * meaning, we are going to get the source of this event and cast it to a node
     * cast the source to a node then cast it again to a Stage
     */



    public void switchToDashboard(ActionEvent event) throws IOException {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Dashboard.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }


    public void switchToRoom(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Rooms.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    public void switchToGuest(ActionEvent event) throws IOException
    {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Guest.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void handleBookRoomButtonAction(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bookingWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 720);
        Stage stage = new Stage();
        stage.setTitle("Book a room");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); //default, for closing th pop up window
        stage.show();
    }



    @FXML
    void handleBookRoomButtonAction(ActionEvent event) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bookingWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 720);
        Stage stage = new Stage();
        stage.setTitle("Book a room");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); //default, for closing th pop up window
        stage.show();

    }





}
