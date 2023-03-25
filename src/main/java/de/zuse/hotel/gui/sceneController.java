package de.zuse.hotel.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class sceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

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

    public void switchToGuest(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Guest.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


}
