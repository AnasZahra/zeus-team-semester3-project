package de.zuse.hotel.gui;

import javafx.scene.control.Alert;

public class Message
{
    public static void show(Alert.AlertType alertType, String title, String content)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
