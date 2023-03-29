package de.zuse.hotel.gui;

import de.zuse.hotel.core.HotelCore;
import de.zuse.hotel.util.ZuseCore;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public class JavaFxUtil
{
    public static Parent loadFxml(URL url, ControllerApi outObject) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent node = fxmlLoader.load();

        if (!(fxmlLoader.getController() instanceof ControllerApi))
        {
            ZuseCore.coreAssert(false, "Make sure your class implements ControllerApi interface!");
        }

        ((ControllerApi) fxmlLoader.getController()).onStart();
        return node;
    }

    public static Parent loadFxml(URL url) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent node = fxmlLoader.load();

        if (!(fxmlLoader.getController() instanceof ControllerApi))
        {
            ZuseCore.coreAssert(false, "Make sure your class implements ControllerApi interface!");
        }

        ((ControllerApi) fxmlLoader.getController()).onStart();
        return node;
    }

}
