package de.zuse.hotel.gui;

import de.zuse.hotel.core.Address;
import de.zuse.hotel.core.HotelCore;
import de.zuse.hotel.core.Person;
import de.zuse.hotel.core.Room;
import de.zuse.hotel.db.JDBCConnecter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GuestController implements ControllerApi
{
    public AnchorPane root;
    public Text guestTitleId;
    public Text searchTextid;
    @FXML
    private TableView<Person> guestTable;

    @FXML
    private TableColumn<Person, Integer> guestIdCln;

    @FXML
    private TableColumn<Person, String> firstNameCln;

    @FXML
    private TableColumn<Person, String> lastNameCln;
    @FXML
    private TableColumn<Person, LocalDate> birthdayCln;
    @FXML
    private TableColumn<Person, String> emailCln;
    @FXML
    private TableColumn<Person, String> telefonCln;
    @FXML
    private TableColumn<Person, Address> addressCln;

    private Person selectedUser = null;

    @FXML
    private TextField seachBarID;

    @Override
    public void onStart()
    {
        setupStyling();
        guestIdCln.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameCln.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCln.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        birthdayCln.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        emailCln.setCellValueFactory(new PropertyValueFactory<>("email"));
        telefonCln.setCellValueFactory(new PropertyValueFactory<>("telNumber"));
        addressCln.setCellValueFactory(new PropertyValueFactory<>("address"));
        //refresh the database and load the data from it on the table
        onRefresh();

        guestTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
        {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue)
            {
                if (guestTable.getSelectionModel().getSelectedItem() != null)
                    selectedUser = guestTable.getSelectionModel().getSelectedItem();
                else
                    selectedUser = null;
            }
        });

        seachBarID.textProperty().addListener((observable, oldValue, newValue) ->
        { //listener on the table, that calls the seachGuests method when it is triggered by changing the textfield of it and seach for the changed text on the textfield(new value)
            GuestController.this.searcGuests(newValue);
        });
    }

    @Override
    public void onUpdate()
    {
        onRefresh();
    }

    public void onRefresh()
    {
        List<Person> personList = HotelCore.get().getAllGuest();
        ObservableList<Person> pList = FXCollections.observableArrayList(personList);
        guestTable.setItems(pList);
    }

    public void addGuestBtn(ActionEvent event) throws Exception
    {
        createFXMLoader("addGuest.fxml", 450, 720, "Add a Guest");
    }

    public void deleteGuestBtn(ActionEvent event) throws Exception
    {
        //TODO: maybe select guest instead of typing id
        createFXMLoader("deleteGuest.fxml", 370, 161, "Delete a Guest");
    }

    public void updateGuestBtn(ActionEvent event) throws Exception
    {
        Person selectedPerson = guestTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null)
        {
            createFXMLoader("editGuest.fxml", 575, 755, "Update a Guest");
        } else
        {
            InfoController.showMessage(InfoController.LogLevel.Error, "Update Guest", "No Guest was selected on the table! Please select a Guest.");
        }
    }

    public void createFXMLoader(String string, int width, int height, String description) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(string));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle(description);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); //default, for closing th pop up window
        stage.show();
        stage.resizableProperty().setValue(false);
        HotelCore.get().setCurrentStage(stage);

        //Only for editGuest
        if (fxmlLoader.getController() instanceof editGuestController)
        {
            ((editGuestController) fxmlLoader.getController()).setSelectedUser(selectedUser);
        }
        ((ControllerApi) fxmlLoader.getController()).onStart();

    }

    public void searcGuests(String string)
    {
        List<Person> filteredGuests = HotelCore.get().getAllGuest().stream()
                .filter(guest -> guest.getFirstName().toLowerCase().contains(string.toLowerCase()))
                .collect(Collectors.toList()); //stream filter search

        ObservableList<Person> GuestList = FXCollections.observableArrayList(filteredGuests); //refresh the table
        guestTable.setItems(GuestList);
    }

    public void setupStyling()
    {
        root.getStylesheets().clear();
        if (SettingsController.currentMode == SettingsController.SystemMode.LIGHT)
            guestTable.setStyle("");

        root.getStylesheets().add(SettingsController.getCorrectStylePath("background.css"));
        root.getStylesheets().add(SettingsController.getCorrectStylePath("NavMenu.css"));
        guestTable.getStylesheets().add(SettingsController.getCorrectStylePath("Tableview.css"));
        seachBarID.getStylesheets().add(SettingsController.getCorrectStylePath("BookingWindow.css"));
    }

}
