package de.martinlosse.gpxedit.model;

import java.util.Collections;
import java.util.List;

public class Track {

    private final String name;
    private final String type;

    private final List<TrackSegment> segments;

    public Track(String name, String type, List<TrackSegment> segments) {
        this.name = name;
        this.type = type;
        this.segments = Collections.unmodifiableList(segments);
    }
    
    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
    }
    
    public List<TrackSegment> getSegments() {
        return segments;
    }
}
