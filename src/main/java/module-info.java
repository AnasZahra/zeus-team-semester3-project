module de.zuse.hotel
{


    requires javafx.controls;
    requires javafx.fxml;

    opens de.zuse.hotel.gui to javafx.fxml , javafx.graphics;
}