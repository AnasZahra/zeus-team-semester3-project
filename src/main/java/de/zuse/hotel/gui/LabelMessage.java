package de.zuse.hotel.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;


public class LabelMessage
{
    public Label startMessage;

    public void test(ActionEvent event)
    {
        startMessage.setText("Hi ich bin JAn !");
    }

}
