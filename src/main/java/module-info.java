module de.martinlosse.gpxedit {
    requires javafx.controls;
    requires javafx.fxml;

    opens de.martinlosse.gpxedit to javafx.fxml;
    exports de.martinlosse.gpxedit;
}