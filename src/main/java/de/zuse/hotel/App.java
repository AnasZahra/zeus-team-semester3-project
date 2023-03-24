package de.zuse.hotel;

import de.zuse.hotel.gui.*;

import de.zuse.hotel.util.ConsoleDialogLayer;

import de.zuse.hotel.db.JDBCConnecter;
//import de.zuse.hotel.util.ZuseCore;

import java.sql.Connection;


public class App {
    public static void main(String[] args) {

        Layer layer = new ConsoleDialogLayer();

        layer.onStart();
        layer.run(args);
        layer.onClose();

        Connection connection = null;
    }
}


