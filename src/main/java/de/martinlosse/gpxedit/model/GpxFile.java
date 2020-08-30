package de.martinlosse.gpxedit.model;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

public class GpxFile {
    
    private final String creator;
    private final String version;
    private final ZonedDateTime timestamp;
    
    private final List<Waypoint> waypoints;
    private final List<Route> routes;
    private final List<Track> tracks;
    
    public GpxFile(final String creator,
                   final ZonedDateTime timestamp,
                   final List<Waypoint> waypoints,
                   final List<Route> routes,
                   final List<Track> tracks) {
        
        this.creator = creator;
        this.version = Constants.GPX_VERSION;
        this.timestamp = timestamp;
        
        this.waypoints = waypoints;
        this.routes = routes;
        this.tracks = tracks;
    }
    
    public String getCreator() {
        return creator;
    }
    
    public String getVersion() {
        return version;
    }
    
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
    
    public List<Waypoint> getWaypoints() {
        return waypoints;
    }
    
    public List<Route> getRoutes() {
        return routes;
    }
    
    public List<Track> getTracks() {
        return tracks;
    }
}
