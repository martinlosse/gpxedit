package de.martinlosse.gpxedit.parser;

import com.topografix.gpx.GpxType;
import com.topografix.gpx.ObjectFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class GpxParser {
    
    private final JAXBContext ctx;
    
    public GpxParser() throws JAXBException {
        ctx = JAXBContext.newInstance(GpxType.class);
    }
    
    public GpxType parseGpxFile(Path path) throws IOException {
        try (InputStream is = Files.newInputStream(path)) {
            return JAXB.unmarshal(is, GpxType.class);
        }
    }
    
    public void writeGpxFile(Path destination, GpxType gpxRecord) throws IOException {
        try {
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.marshal(new ObjectFactory().createGpx(gpxRecord), Files.newOutputStream(destination));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
