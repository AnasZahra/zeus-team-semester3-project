module de.zuse.hotel
{
    requires javafx.controls;
    requires javafx.fxml;
    requires itextpdf;

    opens de.zuse.hotel.gui to javafx.fxml, javafx.graphics;

    exports de.zuse.hotel.util;
}