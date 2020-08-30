package de.martinlosse.gpxedit;

import com.topografix.gpx.GpxType;
import de.martinlosse.gpxedit.editor.GpxEditor;
import de.martinlosse.gpxedit.editor.TimestampMode;
import de.martinlosse.gpxedit.model.GpxFile;
import de.martinlosse.gpxedit.model.Track;
import de.martinlosse.gpxedit.model.TrackSegment;
import de.martinlosse.gpxedit.parser.GpxConverter;
import de.martinlosse.gpxedit.parser.GpxParser;
import de.martinlosse.gpxedit.parser.UnsupportedGpxVersionException;
import de.martinlosse.gpxedit.ui.GpxEditUI;
import de.martinlosse.gpxedit.util.XmlTimeConverter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;

public class GpxEdit {
    
    private final GpxConverter converter;
    private final GpxParser parser;
    
    public static void main(String[] args)
            throws DatatypeConfigurationException, UnsupportedGpxVersionException, IOException, JAXBException {
        GpxEdit gpxEdit = new GpxEdit();
        
        if (args[0].equals("rewriteTimestamp")) {
            if (args.length != 4) {
                throw new IllegalArgumentException("must provide the following arguments: <path to file>, <start time>, <end time>");
            }
            Path gpxSourcePath = Paths.get(args[1]);
            ZonedDateTime startTime = ZonedDateTime.parse(args[2]);
            ZonedDateTime endTime = ZonedDateTime.parse(args[3]);
            
            gpxEdit.rewriteTimestamp(gpxSourcePath, startTime, endTime);
        }
        if (args[0].equals("reverse")) {
            if (args.length != 2) {
                throw new IllegalArgumentException("must provide the following argument: <path to file>");
            }
            Path gpxSourcePath = Paths.get(args[1]);
            
            gpxEdit.reverse(gpxSourcePath);
        }
    }
    
    public GpxEdit() throws DatatypeConfigurationException, JAXBException {
        XmlTimeConverter timeConverter = new XmlTimeConverter();
        converter = new GpxConverter(timeConverter);
        parser = new GpxParser();
    }
    
    private void rewriteTimestamp(Path gpxSourcePath, ZonedDateTime startTime, ZonedDateTime endTime)
            throws UnsupportedGpxVersionException, IOException {
        GpxType gpxType = parser.parseGpxFile(gpxSourcePath);
        GpxFile gpxFile = converter.fromXml(gpxType);
        
        Track track = gpxFile.getTracks().get(0);
        List<TrackSegment> segments = track.getSegments();
        
        TrackSegment rewrittenSegment = GpxEditor.rewriteTimestamps(segments.get(0), startTime, endTime);
        
        
        segments.clear();
        segments.add(rewrittenSegment);
        
        GpxType rewrittenFile = converter.toXml(gpxFile);
        parser.writeGpxFile(gpxSourcePath.resolveSibling("rewritten.gpx"), rewrittenFile);
    }
    
    private void reverse(Path gpxSourcePath) throws UnsupportedGpxVersionException, IOException {
        GpxType gpxType = parser.parseGpxFile(gpxSourcePath);
        GpxFile gpxFile = converter.fromXml(gpxType);
        
        Track track = gpxFile.getTracks().get(0);
        List<TrackSegment> segments = track.getSegments();
        
        TrackSegment rewrittenSegment = GpxEditor.reverse(segments.get(0), TimestampMode.drop());
        
        segments.clear();
        segments.add(rewrittenSegment);
        
        GpxType rewrittenFile = converter.toXml(gpxFile);
        parser.writeGpxFile(gpxSourcePath.resolveSibling("reversed.gpx"), rewrittenFile);
    }
    
    private void startUi() {
        GpxEditUI ui = new GpxEditUI();
        ui.launch();
    }
    
}