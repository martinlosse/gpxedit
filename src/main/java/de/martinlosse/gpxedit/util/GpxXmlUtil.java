package de.martinlosse.gpxedit.util;

import de.martinlosse.gpxedit.model.Waypoint;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class GpxXmlUtil {

    public static Waypoint waypoint(String lat, String lng, String ele, String dateTime) {
        ZonedDateTime timestamp = dateTime == null ? null : ZonedDateTime.parse(dateTime);

        return new Waypoint(new BigDecimal(lat), new BigDecimal(lng), new BigDecimal(ele), timestamp);
    }

}
