package de.martinlosse.gpxedit;

import de.martinlosse.gpxedit.ui.GpxEditUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App {

    public static void main(String[] args) {
        GpxEditUI ui = new GpxEditUI();
        ui.launch();
    }

}