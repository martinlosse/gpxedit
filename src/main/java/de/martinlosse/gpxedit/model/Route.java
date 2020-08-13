package de.martinlosse.gpxedit.model;

import java.util.List;

public class Route {

    private final String name;
    private final String type;

    private final List<Waypoint> routePoints;

    public Route(final String name, final String type, final List<Waypoint> routePoints) {
        this.name = name;
        this.type = type;

        this.routePoints = routePoints;
    }
}
