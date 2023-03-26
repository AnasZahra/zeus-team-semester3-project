module de.zuse.hotel
{
    requires javafx.controls;
    requires javafx.fxml;
    requires itextpdf;
    requires de.jensd.fx.glyphs.fontawesome;



    requires com.fasterxml.jackson.dataformat.yaml;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires java.persistence;
    requires org.hibernate.orm.core;


    opens de.zuse.hotel.gui to javafx.fxml, javafx.graphics;
    opens de.zuse.hotel.core to com.fasterxml.jackson.dataformat.yaml, com.fasterxml.jackson.databind,
            org.hibernate.orm.core;


    exports de.zuse.hotel.util;
    exports de.zuse.hotel.util.pdf;

    //exports de.zuse.hotel.util;

}