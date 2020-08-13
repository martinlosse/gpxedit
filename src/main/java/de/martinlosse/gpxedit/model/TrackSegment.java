package de.martinlosse.gpxedit.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TrackSegment {

    private final List<Waypoint> trackPoints;

    public TrackSegment(List<Waypoint> trackPoints) {
        this.trackPoints = Collections.unmodifiableList(trackPoints);
    }

    public List<Waypoint> getTrackPoints() {
        return trackPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TrackSegment that = (TrackSegment) o;
        return Objects.equals(trackPoints, that.trackPoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackPoints);
    }

    @Override
    public String toString() {
        return "TrackSegment{" +
            "trackPoints=" + trackPoints +
            '}';
    }
}
