package de.martinlosse.gpxedit.ui;

import java.io.IOException;

import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        GpxEditUI.setRoot("secondary");
    }
}
