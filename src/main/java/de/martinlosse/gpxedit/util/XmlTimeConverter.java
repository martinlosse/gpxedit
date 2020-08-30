package de.martinlosse.gpxedit.util;

import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class XmlTimeConverter {

    private final DatatypeFactory datatypeFactory;

    public XmlTimeConverter() throws DatatypeConfigurationException {
        this.datatypeFactory = DatatypeFactory.newInstance();
    }

    public XMLGregorianCalendar toXmlTime(String dateTime) {
        if (dateTime == null) {
            return null;
        }

        return toXmlTime(ZonedDateTime.parse(dateTime));
    }
    
    public XMLGregorianCalendar toXmlTime(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        GregorianCalendar cal = GregorianCalendar.from(dateTime);
        return datatypeFactory.newXMLGregorianCalendar(cal);
    }
    
    public ZonedDateTime fromXmlTime(XMLGregorianCalendar cal) {
        return cal == null ? null : cal.toGregorianCalendar().toZonedDateTime();
    }
}
