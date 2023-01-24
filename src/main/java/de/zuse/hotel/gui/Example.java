package de.zuse.hotel.gui;

import de.zuse.hotel.Layer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Example extends Application implements Layer {
    public Label welcomeText;

    @Override
    public void onStart() {
        System.out.println("On Starting The Hotel App...");
    }

    @Override
    public void run(String[] args) {
        launch();
    }

    @Override
    public void onClose() {
        System.out.println("On Closing The Hotel App...");
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Example.class.getResource("example-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hotel v1.0");
        stage.setScene(scene);
        stage.show();
    }

}
