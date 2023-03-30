package de.zuse.hotel;

import de.zuse.hotel.core.HotelCore;
import de.zuse.hotel.gui.Gui;
import de.zuse.hotel.util.ConsoleDialogLayer;

public class App
{
    public static void main(String[] args)
    {
    	 Layer layer = new Gui();
         //Layer layer = new ConsoleDialogLayer();

         Thread onStartThread = new Thread(() -> layer.onStart());
         Thread runThread = new Thread(() -> layer.run(args));

         onStartThread.start();
         runThread.start();

         try {
             onStartThread.join();
             runThread.join();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }

         layer.onClose();
    }
}


