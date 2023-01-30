package de.zuse.hotel.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;


public class LabelMessage
{
    @FXML
    private Label startMessage;

    @FXML
    private void test(ActionEvent event)
    {
        startMessage.setText("HI !");
    }

}
