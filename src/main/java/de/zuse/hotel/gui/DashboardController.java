package de.zuse.hotel.gui;

import de.zuse.hotel.core.Booking;
import de.zuse.hotel.core.HotelCore;
import de.zuse.hotel.util.pdf.PdfFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.util.function.Consumer;

public class DashboardController implements ControllerApi
{
    public ListView<BookingContainerController> listView;
    public AnchorPane dashboardRoot;
    public ImageView deleteBtnImageId;


    @Override
    public void onStart()
    {
        setupStyling();

        listView.setCellFactory(new Callback<ListView<BookingContainerController>, ListCell<BookingContainerController>>()
        {
            @Override
            public ListCell<BookingContainerController> call(ListView<BookingContainerController> listView)
            {
                return new ListCell<BookingContainerController>()
                {
                    @Override
                    protected void updateItem(BookingContainerController item, boolean empty)
                    {
                        super.updateItem(item, empty);
                        if (item != null)
                        {
                            setGraphic(item.getContent());
                        } else
                        {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        viewBookingData();
    }

    @Override
    public void onUpdate()
    {
        viewBookingData();
    }

    public void viewBookingData()
    {
        listView.getItems().clear();
        HotelCore.get().getAllBooking().forEach(new Consumer<Booking>()
        {
            @Override
            public void accept(Booking booking)
            {
                addBookingToDashboard(booking);
            }
        });
    }

    public void onSaveClicked(ActionEvent event)
    {
        if (listView.getSelectionModel().getSelectedItem() == null)
        {
            InfoController.showMessage(InfoController.LogLevel.Warn, "delete", "Select Booking to save");
            return;
        }

        PdfFile bookingFile = HotelCore.get().getBookingAsPdfFile(listView.getSelectionModel().getSelectedItem().getBookingID());
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Save PDf");
        File file = fileChooser.showSaveDialog(listView.getScene().getWindow());

        if (file != null)
        {
            bookingFile.saveFile(file.getPath());
            InfoController.showMessage(InfoController.LogLevel.Info, "Successful",
                    file.getName() + " saved in " + file.getPath());
        }
    }

    public void addBookingToDashboard(Booking booking)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BookingContainer.fxml"));
            Parent bookingContainer = loader.load();

            BookingContainerController bookingContainerController = loader.getController();
            bookingContainerController.setStyle(booking.isCanceled());
            bookingContainerController.guestName.setText(booking.getGuestName());
            bookingContainerController.arrivalDate.setText(String.valueOf(booking.getStartDate()));
            bookingContainerController.departureDate.setText(String.valueOf(booking.getEndDate()));
            bookingContainerController.roomAndFloor.setText(booking.getRoomNumber() + "-" + booking.getFloorNumber());
            bookingContainerController.setBookingID(booking.getBookingID());
            listView.getItems().add(bookingContainerController);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteBooking(ActionEvent event)
    {
        if (listView.getSelectionModel().getSelectedItem() == null)
        {
            InfoController.showMessage(InfoController.LogLevel.Warn, "delete", "Select Booking to delete");
            return;
        }

        Booking booking = HotelCore.get().getBooking(listView.getSelectionModel().getSelectedItem().getBookingID());
        if (booking.isCanceled())
        {
            InfoController.showMessage(InfoController.LogLevel.Warn, "delete", "The booking you have selected " +
                    "has already been canceled.");
            return;
        }

        if (InfoController.showConfirmMessage(InfoController.LogLevel.Warn, "Delete Booking", "Are you sure?"))
        {
            HotelCore.get().removeBooking(booking);
        }
    }

    public void setupStyling()
    {
        if (SettingsController.currentMode == SettingsController.SystemMode.LIGHT)
        {
            listView.setStyle("");
            Image image = new Image(getClass().getResource("images/deletebtn_lightMode.png").toExternalForm());
            deleteBtnImageId.setImage(image);
        } else
        {
            Image image = new Image(getClass().getResource("images/deletebtn_darkMode.png").toExternalForm());
            deleteBtnImageId.setImage(image);
        }

        dashboardRoot.getStylesheets().clear();
        dashboardRoot.getStylesheets().addAll(SettingsController.getCorrectStylePath("background.css")
                , SettingsController.getCorrectStylePath("NavMenu.css"));

        listView.getStylesheets().add(SettingsController.getCorrectStylePath("Tableview.css"));
    }

}
