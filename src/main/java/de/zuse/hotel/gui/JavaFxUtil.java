package de.zuse.hotel.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class JavaFxUtil
{
    public static void makeFieldOnlyNumbers(TextField textField)
    {
        if (textField == null)
            return;

        textField.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if (!newValue.matches("\\d*"))
                {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public static void makeFieldOnlyChars(TextField textField)
    {
        if (textField == null)
            return;

        textField.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if (!newValue.matches("[A-Za-z]+"))
                {
                    textField.setText(newValue.replaceAll("[^A-Za-z]", ""));
                }
            }
        });
    }

}
