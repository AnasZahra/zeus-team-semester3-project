package de.zuse.hotel;

import de.zuse.hotel.gui.*;
import de.zuse.hotel.util.ConsoleDialogLayer;

public class App
{
    public static void main(String[] args)
    {
        System.out.println("Present Project Directory : "+ System.getProperty("user.dir"));
        Layer layer = new ExampleLayer();

        layer.onStart();
        layer.run(args);
        layer.onClose();
    }

}
