package de.zuse.hotel.gui;

import de.zuse.hotel.core.Address;
import de.zuse.hotel.core.HotelCore;
import de.zuse.hotel.core.Person;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class addGuestController implements ControllerApi
{
    public TextField country;
    public TextField city;
    public TextField street;
    public TextField plz;
    public TextField houseNr;
    @FXML
    private TextField lastName;
    @FXML
    private TextField firstName;
    @FXML
    private TextField email;
    @FXML
    private TextField telNum;
    @FXML
    private DatePicker birthDate;

    @FXML
    void addGuest(ActionEvent event) throws Exception
    {
        if (houseNr.getText().strip().isEmpty() || plz.getText().strip().isEmpty())
        {
            InfoController.showMessage(InfoController.LogLevel.Error, "Room Number", "fill all information about Guest");
            return;
        }

        Address address = new Address(country.getText(), city.getText(), street.getText()
                , plz.getText(), Integer.parseInt(houseNr.getText()));

        Person guest = new Person(firstName.getText(), lastName.getText(), birthDate.getValue(), email.getText(),
                telNum.getText(), address);

        //add guest to database
        boolean info = HotelCore.get().addGuest(guest);
        if (info)
            InfoController.showMessage(InfoController.LogLevel.Info,
                    "Information", "Guest Added Successfully!");

        HotelCore.get().getCurrentStage().close();
    }

    @Override
    public void onStart()
    {
        JavaFxUtil.makeFieldOnlyNumbers(houseNr);
        JavaFxUtil.makeFieldOnlyNumbers(plz);
        JavaFxUtil.makeFieldOnlyNumbers(telNum);

        JavaFxUtil.makeFieldOnlyChars(firstName);
        JavaFxUtil.makeFieldOnlyChars(lastName);
        JavaFxUtil.makeFieldOnlyChars(city);
        JavaFxUtil.makeFieldOnlyChars(country);
        JavaFxUtil.makeFieldOnlyChars(street);
    }

    @Override
    public void onUpdate(){}

}
