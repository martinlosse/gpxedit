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

    public GpxFile(final String creator, final ZonedDateTime timestamp, final List<Waypoint> waypoints,
        final List<Route> routes, final List<Track> tracks) {

        this.creator = creator;
        this.version = Constants.GPX_VERSION;
        this.timestamp = timestamp;

        this.waypoints = Collections.unmodifiableList(waypoints);
        this.routes = Collections.unmodifiableList(routes);
        this.tracks = Collections.unmodifiableList(tracks);
    }
}
