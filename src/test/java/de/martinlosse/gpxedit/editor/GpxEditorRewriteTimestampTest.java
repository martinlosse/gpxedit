package de.martinlosse.gpxedit.editor;

import static de.martinlosse.gpxedit.util.GpxXmlUtil.waypoint;
import static org.assertj.core.api.Assertions.assertThat;

import de.martinlosse.gpxedit.model.TrackSegment;
import de.martinlosse.gpxedit.model.Waypoint;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class GpxEditorRewriteTimestampTest {
    
    @MethodSource("testCases")
    @ParameterizedTest
    void testSplitSegments(TestCaseRewrite testCase) {
        TrackSegment result = GpsEditor.rewriteTimestamps(testCase.sourceSegment, testCase.startTime, testCase.endTime);
        
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(testCase.targetSegment);
    }
    
    private static List<TestCaseRewrite> testCases() {
        List<TestCaseRewrite> testCases = new ArrayList<>();
        
        // source segment
        List<Waypoint> points;
        List<Waypoint> targetPoints;
        
        TrackSegment sourceSegment;
        ZonedDateTime startTime;
        ZonedDateTime endTime;
        TrackSegment targetSegment;
        
        // n = 0
        sourceSegment = new TrackSegment(Collections.emptyList());
        startTime = ZonedDateTime.parse("2161-07-01T12:00:00Z");
        endTime = ZonedDateTime.parse("2161-07-01T13:37:00Z");
        targetSegment = new TrackSegment(Collections.emptyList());
        testCases.add(new TestCaseRewrite(sourceSegment, startTime, endTime, targetSegment));
        
        // n = 1
        points = List.of(
                waypoint("52.1650840", "11.6835130", "43.0", "2020-08-06T18:42:15Z")
        );
        targetPoints = List.of(
                waypoint("52.1650840", "11.6835130", "43.0", "2161-07-01T12:00:00Z")
        );
        
        sourceSegment = new TrackSegment(points);
        startTime = ZonedDateTime.parse("2161-07-01T12:00:00Z");
        endTime = ZonedDateTime.parse("2161-07-01T13:37:00Z");
        targetSegment = new TrackSegment(targetPoints);
        testCases.add(new TestCaseRewrite(sourceSegment, startTime, endTime, targetSegment));
        
        // n = 2
        points = List.of(
                waypoint("52.1650840", "11.6835130", "43.0", "2020-08-06T18:42:15Z"),
                waypoint("52.1651800", "11.6835150", "43.0", "2020-08-06T18:42:22Z")
        );
        
        targetPoints = List.of(
                waypoint("52.1650840", "11.6835130", "43.0", "2161-07-01T12:00:00Z"),
                waypoint("52.1651800", "11.6835150", "43.0", "2161-07-01T13:37:00Z")
        );
        
        sourceSegment = new TrackSegment(points);
        startTime = ZonedDateTime.parse("2161-07-01T12:00:00Z");
        endTime = ZonedDateTime.parse("2161-07-01T13:37:00Z");
        targetSegment = new TrackSegment(targetPoints);
        testCases.add(new TestCaseRewrite(sourceSegment, startTime, endTime, targetSegment));
        
        // n > 2
        points = List.of(
                waypoint("52.1650840", "11.6835130", "43.0", "2020-08-06T18:42:15Z"),
                waypoint("52.1651380", "11.6835250", "43.0", "2020-08-06T18:42:17Z"),
                waypoint("52.1651800", "11.6835150", "43.0", "2020-08-06T18:42:22Z")
        );
        targetPoints = List.of(
                waypoint("52.1650840", "11.6835130", "43.0", "2161-07-01T12:00:00Z"),
                waypoint("52.1651380", "11.6835250", "43.0", "2161-07-01T12:48:30Z"),
                waypoint("52.1651800", "11.6835150", "43.0", "2161-07-01T13:37:00Z")
        );
        
        sourceSegment = new TrackSegment(points);
        startTime = ZonedDateTime.parse("2161-07-01T12:00:00Z");
        endTime = ZonedDateTime.parse("2161-07-01T13:37:00Z");
        targetSegment = new TrackSegment(targetPoints);
        testCases.add(new TestCaseRewrite(sourceSegment, startTime, endTime, targetSegment));
        
        return testCases;
    }
    
    private static class TestCaseRewrite {
        
        TrackSegment sourceSegment;
        
        ZonedDateTime startTime;
        ZonedDateTime endTime;
        
        TrackSegment targetSegment;
        
        public TestCaseRewrite(final TrackSegment sourceSegment,
                               final ZonedDateTime startTime,
                               final ZonedDateTime endTime,
                               final TrackSegment targetSegment) {
            this.sourceSegment = sourceSegment;
            this.startTime = startTime;
            this.endTime = endTime;
            this.targetSegment = targetSegment;
        }
    }
    
}
