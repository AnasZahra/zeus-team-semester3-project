package de.zuse.hotel;

import de.zuse.hotel.gui.*;

public class App
{
    public static void main(String[] args)
    {
        Layer layer = new ExampleLayer();

        layer.onStart();
        layer.run(args);
        layer.onClose();
    }
}
