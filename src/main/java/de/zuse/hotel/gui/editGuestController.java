package de.zuse.hotel.gui;

import de.zuse.hotel.core.Address;
import de.zuse.hotel.core.HotelCore;
import de.zuse.hotel.core.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class editGuestController implements ControllerApi
{
    public AnchorPane root;
    @FXML
    private DatePicker bDateID;

    @FXML
    private TextField cityID;

    @FXML
    private TextField countreyID;

    @FXML
    private TextField emailID;

    @FXML
    private TextField firstNameID;

    @FXML
    private TextField houseNrID;

    @FXML
    private TextField lastNameID;

    @FXML
    private TextField plzID;

    @FXML
    private TextField streetID;

    @FXML
    private TextField telefonID;

    private Person selectedUser;

    public editGuestController()
    {
    }

    public void saveChanges(ActionEvent actionEvent)
    {
        if (InfoController.showConfirmMessage(InfoController.LogLevel.Info, "Save Changes", "Are you sure?"))
        {
            selectedUser.setFirstName(firstNameID.getText());
            selectedUser.setLastName(lastNameID.getText());
            selectedUser.setEmail(emailID.getText());
            selectedUser.setTelNumber(telefonID.getText());
            selectedUser.setBirthday(bDateID.getValue());
            Address newAddress = new Address(countreyID.getText(), cityID.getText(), streetID.getText(),
                    plzID.getText(), Integer.parseInt(houseNrID.getText()));
            selectedUser.setAddress(newAddress);
            HotelCore.get().updateGuest(selectedUser); //update guest data
            HotelCore.get().getCurrentStage().close();// close window
        }
    }

    private void getSelected(Person guest)
    {
        if (guest != null)
        {
            firstNameID.setText(guest.getFirstName());
            lastNameID.setText(guest.getLastName());
            emailID.setText(guest.getEmail());
            telefonID.setText(guest.getTelNumber());
            bDateID.setValue(guest.getBirthday());
            countreyID.setText(guest.getAddress().getCountry());
            cityID.setText(guest.getAddress().getCity());
            streetID.setText(guest.getAddress().getStreet());
            plzID.setText(guest.getAddress().getPlz());
            houseNrID.setText(Integer.toString(guest.getAddress().getHouseNr()));
        }
    }

    @Override
    public void onStart()
    {
        root.getStylesheets().add(SettingsController.getCorrectStylePath("BookingWindow.css"));
        bDateID.getStylesheets().add(SettingsController.getCorrectStylePath("datePickerStyle.css"));

        getSelected(selectedUser); //view the current guest  data

        JavaFxUtil.makeFieldOnlyNumbers(houseNrID);
        JavaFxUtil.makeFieldOnlyNumbers(plzID);
        JavaFxUtil.makeFieldOnlyNumbers(telefonID);

        JavaFxUtil.makeFieldOnlyChars(firstNameID);
        JavaFxUtil.makeFieldOnlyChars(lastNameID);
        JavaFxUtil.makeFieldOnlyChars(cityID);
        JavaFxUtil.makeFieldOnlyChars(countreyID);
        JavaFxUtil.makeFieldOnlyChars(streetID);
    }

    @Override
    public void onUpdate()
    {
    }

    public void cancelChanges(ActionEvent actionEvent)
    {
        HotelCore.get().getCurrentStage().close();
    }

    public void setSelectedUser(Person selectedUser)
    {
        this.selectedUser = selectedUser;
    }
}