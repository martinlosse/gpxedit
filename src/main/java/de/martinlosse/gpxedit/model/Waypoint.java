package de.martinlosse.gpxedit.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

public class Waypoint {

    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final BigDecimal elevation;
    private final ZonedDateTime timestamp;

    public Waypoint(BigDecimal latitude, BigDecimal longitude, BigDecimal elevation, ZonedDateTime timestamp) {
        if (latitude == null) {
            throw new NullPointerException("latitude must not be null");
        }
        if (longitude == null) {
            throw new NullPointerException("longitude must not be null");
        }

        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.timestamp = timestamp;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public BigDecimal getElevation() {
        return elevation;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Waypoint waypoint = (Waypoint) o;
        return latitude.equals(waypoint.latitude) &&
            longitude.equals(waypoint.longitude) &&
            Objects.equals(elevation, waypoint.elevation) &&
            Objects.equals(timestamp, waypoint.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, elevation, timestamp);
    }

    @Override
    public String toString() {
        return "Waypoint{" +
            "latitude=" + latitude +
            ", longitude=" + longitude +
            ", elevation=" + elevation +
            ", timestamp=" + timestamp +
            '}';
    }
}
