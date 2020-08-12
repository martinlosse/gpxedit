package de.martinlosse.gpxedit.ui;

import java.io.IOException;

import de.martinlosse.gpxedit.App;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        GpxEditUI.setRoot("primary");
    }
}