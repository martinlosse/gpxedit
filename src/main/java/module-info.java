module de.martinlosse.gpxedit {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.xml.bind;

    opens de.martinlosse.gpxedit to javafx.fxml;
    opens com.topografix.gpx to java.xml.bind;

    exports de.martinlosse.gpxedit;
}