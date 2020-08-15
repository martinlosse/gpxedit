package de.martinlosse.gpxedit.editor;

import de.martinlosse.gpxedit.model.TrackSegment;
import de.martinlosse.gpxedit.model.Waypoint;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface TimestampMode {

    TrackSegment updateTimestamps(TrackSegment trackSegment);

    static TimestampMode drop() {
        return new DropMode();
    }

    static TimestampMode reverse() {
        return new ReverseMode();
    }

    static TimestampMode startAt(ZonedDateTime startTime) {
        return new StartAtMode(startTime);
    }

    class DropMode implements TimestampMode {

        @Override
        public TrackSegment updateTimestamps(TrackSegment trackSegment) {
            List<Waypoint> waypoints = trackSegment.getTrackPoints().stream()
                .map(waypoint -> new Waypoint(waypoint.getLatitude(), waypoint.getLongitude(), waypoint.getElevation(),
                    null))
                .collect(Collectors.toUnmodifiableList());

            return new TrackSegment(waypoints);
        }
    }

    class ReverseMode implements TimestampMode {

        @Override
        public TrackSegment updateTimestamps(TrackSegment trackSegment) {
            List<Waypoint> reversedPoints = trackSegment.getTrackPoints();

            if (reversedPoints.size() <= 1) {
                return trackSegment;
            }

            ZonedDateTime startTime = determineStartTime(reversedPoints);
            return adjustTimestamps(reversedPoints, startTime);
        }

        private ZonedDateTime determineStartTime(List<Waypoint> reversedPoints) {
            Waypoint lastPoint = reversedPoints.get(reversedPoints.size() - 1);
            return lastPoint.getTimestamp();
        }
    }

    class StartAtMode implements TimestampMode {

        private final ZonedDateTime startTime;

        public StartAtMode(ZonedDateTime startTime) {
            this.startTime = startTime;
        }

        @Override
        public TrackSegment updateTimestamps(TrackSegment trackSegment) {
            List<Waypoint> reversedPoints = trackSegment.getTrackPoints();

            if (reversedPoints.size() < 1) {
                return trackSegment;
            }

            return adjustTimestamps(reversedPoints, startTime);
        }
    }

    private static TrackSegment adjustTimestamps(List<Waypoint> reversedPoints, ZonedDateTime startTime) {
        Waypoint firstPt = reversedPoints.get(0);
        Waypoint newFirst = new Waypoint(firstPt.getLatitude(), firstPt.getLongitude(), firstPt.getElevation(), startTime);

        List<Waypoint> updatedWaypoints = new ArrayList<>(reversedPoints.size());
        updatedWaypoints.add(newFirst);

        for (int i = 1; i < reversedPoints.size(); i++) {
            Waypoint prevWp = reversedPoints.get(i - 1);
            Waypoint currWp = reversedPoints.get(i);
            Duration timeElapsed = Duration.between(currWp.getTimestamp(), prevWp.getTimestamp());
            ZonedDateTime prevTimestamp = updatedWaypoints.get(i - 1).getTimestamp();
            ZonedDateTime timestamp = prevTimestamp.plus(timeElapsed);

            updatedWaypoints.add(new Waypoint(currWp.getLatitude(), currWp.getLongitude(), currWp.getElevation(), timestamp));
        }

        return new TrackSegment(updatedWaypoints);
    }
}
