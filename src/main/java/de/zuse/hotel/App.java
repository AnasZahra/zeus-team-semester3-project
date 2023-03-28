package de.zuse.hotel;

import de.zuse.hotel.gui.Gui;

public class App
{
    public static void main(String[] args)
    {
        Layer layer = new Gui();
        layer.onStart();
        layer.run(args);
        layer.onClose();
        
    }
}


