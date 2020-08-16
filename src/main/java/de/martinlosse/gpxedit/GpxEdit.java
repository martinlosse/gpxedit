package de.martinlosse.gpxedit;

import de.martinlosse.gpxedit.parser.GpxConverter;
import de.martinlosse.gpxedit.parser.GpxParser;
import de.martinlosse.gpxedit.ui.GpxEditUI;
import de.martinlosse.gpxedit.util.XmlTimeConverter;
import javax.xml.datatype.DatatypeConfigurationException;

public class GpxEdit {

    public static void main(String[] args) throws DatatypeConfigurationException {
        XmlTimeConverter timeConverter = new XmlTimeConverter();
        GpxConverter converter = new GpxConverter(timeConverter);
        GpxParser parser = new GpxParser();
        
        GpxEditUI ui = new GpxEditUI();
        ui.launch();
    }

}