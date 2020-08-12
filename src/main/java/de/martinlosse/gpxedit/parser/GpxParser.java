package de.martinlosse.gpxedit.parser;

import com.topografix.gpx.GpxType;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.xml.bind.JAXB;

public class GpxParser {

    public GpxType parseGpxFile(Path path) throws IOException {
        GpxType gpxRecording;
        try (InputStream is = Files.newInputStream(path)) {
            gpxRecording = JAXB.unmarshal(is, GpxType.class);
        }
        return gpxRecording;
    }
}
