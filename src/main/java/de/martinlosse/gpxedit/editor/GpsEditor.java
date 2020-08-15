package de.martinlosse.gpxedit.editor;

import de.martinlosse.gpxedit.model.TrackSegment;
import de.martinlosse.gpxedit.model.Waypoint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GpsEditor {

    /**
     * <p>
     * Splits a given track segment into two. Track segments are lists of waypoints with a zero-based index.
     * </p><p>
     * Given a segment (a,b) where a is start index and b end index, splitting the segment at index n will result in 2
     * track segments (a, n-1), (n, b).
     * </p><p>
     * Preconditions:
     * <ul>
     *     <li>if n < a: operation fails</li>
     *     <li>if n = a: results in (), (a,b)</li>
     *     <li>if n = b + 1: results in (a, b), ()</li>
     *     <li>if n > b + 1: operation fails</li>
     * </ul>
     * </p>
     *
     * @param sourceSegment The track segment to split.
     * @param splitIndex    The index at which the split is to be performed.
     * @return A {@code List} containing exactly 2 track segments that are result of the split.
     */
    public static List<TrackSegment> split(TrackSegment sourceSegment, int splitIndex) {
        if (sourceSegment == null) {
            throw new NullPointerException("sourceSegment must not be null");
        }
        if (splitIndex < 0 || splitIndex > sourceSegment.getTrackPoints().size()) {
            throw new IllegalArgumentException(
                "splitIndex must be greater than 0 and less than or equal to the number of track points of the given {@code sourceSegment}");
        }

        List<Waypoint> trackPoints = sourceSegment.getTrackPoints();

        TrackSegment segment1 = new TrackSegment(trackPoints.subList(0, splitIndex));
        TrackSegment segment2 = new TrackSegment(trackPoints.subList(splitIndex, trackPoints.size()));

        return List.of(segment1, segment2);
    }

    public static TrackSegment reverse(TrackSegment sourceSegment, TimestampMode timestampMode) {
        TrackSegment reversedSegment = reverseWaypointOrder(sourceSegment);

        return timestampMode.updateTimestamps(reversedSegment);
    }

    private static TrackSegment reverseWaypointOrder(TrackSegment sourceSegment) {
        var reversedWps = new ArrayList<>(sourceSegment.getTrackPoints());
        Collections.reverse(reversedWps);
        TrackSegment reversedSegment = new TrackSegment(reversedWps);
        return reversedSegment;
    }

}
