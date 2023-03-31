package de.zuse.hotel.gui;

import de.zuse.hotel.core.Address;
import de.zuse.hotel.core.HotelCore;
import de.zuse.hotel.core.Person;
import de.zuse.hotel.db.JDBCConnecter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class GuestController implements ControllerApi
{
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

    @Override
    public void onStart()
    {
        guestIdCln.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameCln.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCln.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        birthdayCln.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        emailCln.setCellValueFactory(new PropertyValueFactory<>("email"));
        telefonCln.setCellValueFactory(new PropertyValueFactory<>("telNumber"));
        addressCln.setCellValueFactory(new PropertyValueFactory<>("address"));
        //refresh the database and load the data from it on the table
        onRefresh();
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
        createFXMLoader("updateGuest.fxml", 331, 720, "Update a Guest");
    }

    public void createFXMLoader(String string, int width, int height, String description) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(string));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        Stage stage = new Stage();
        stage.setTitle(description);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); //default, for closing th pop up window
        stage.show();
        stage.resizableProperty().setValue(false);
        HotelCore.get().setCurrentStage(stage);
        ((ControllerApi)fxmlLoader.getController()).onStart();
    }

}
