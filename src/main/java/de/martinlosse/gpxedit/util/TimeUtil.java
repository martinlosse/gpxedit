package de.martinlosse.gpxedit.util;

import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class TimeUtil {

    private final DatatypeFactory datatypeFactory;

    public TimeUtil() throws DatatypeConfigurationException {
        this.datatypeFactory = DatatypeFactory.newInstance();
    }

    public XMLGregorianCalendar xmlTime(String dateTime) {
        if (dateTime == null) {
            return null;
        }

        return xmlTime(ZonedDateTime.parse(dateTime));
    }
    public XMLGregorianCalendar xmlTime(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        GregorianCalendar cal = GregorianCalendar.from(dateTime);
        return datatypeFactory.newXMLGregorianCalendar(cal);
    }
}
