package de.martinlosse.gpxedit.editor;

import static de.martinlosse.gpxedit.util.GpxXmlUtil.waypoint;
import static org.assertj.core.api.Assertions.assertThat;

import de.martinlosse.gpxedit.model.TrackSegment;
import de.martinlosse.gpxedit.model.Waypoint;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class GpsEditorReverseTest {

    @MethodSource({"testCasesDrop", "testCasesReverse", "testCasesStartAt"})
    @ParameterizedTest
    void testReverseDrop(TestCaseReverse testCase) {
        TrackSegment reversed = GpsEditor.reverse(testCase.sourceSegment, testCase.timestampMode);

        assertThat(reversed).isEqualTo(testCase.reversedSegment);
    }

    private static List<TestCaseReverse> testCasesDrop() {
        var testData = new ArrayList<TestCaseReverse>();

        // source segment
        List<Waypoint> points;
        List<Waypoint> reversedPoints;
        TrackSegment original;
        TrackSegment reversed;

        // reverse, drop timestamp, n = 0
        points = List.of();
        reversedPoints = List.of();

        original = new TrackSegment(points);
        reversed = new TrackSegment(reversedPoints);

        testData.add(new TestCaseReverse(original, TimestampMode.drop(), reversed));

        // reverse, drop timestamp, n = 1
        points = List.of(
            waypoint("52.1651800", "11.6835150", "43.0", "2020-08-06T18:42:22Z")
        );

        reversedPoints = List.of(
            waypoint("52.1651800", "11.6835150", "43.0", null)
        );

        original = new TrackSegment(points);
        reversed = new TrackSegment(reversedPoints);

        testData.add(new TestCaseReverse(original, TimestampMode.drop(), reversed));

        // reverse, drop timestamp, n > 1
        points = List.of(
            waypoint("52.1650840", "11.6835130", "43.0", "2020-08-06T18:42:15Z"),
            waypoint("52.1651380", "11.6835250", "43.0", "2020-08-06T18:42:17Z"),
            waypoint("52.1651800", "11.6835150", "43.0", "2020-08-06T18:42:22Z")
        );

        reversedPoints = List.of(
            waypoint("52.1651800", "11.6835150", "43.0", null),
            waypoint("52.1651380", "11.6835250", "43.0", null),
            waypoint("52.1650840", "11.6835130", "43.0", null)
        );

        original = new TrackSegment(points);
        reversed = new TrackSegment(reversedPoints);

        testData.add(new TestCaseReverse(original, TimestampMode.drop(), reversed));

        return testData;
    }

    private static List<TestCaseReverse> testCasesReverse() {
        var testData = new ArrayList<TestCaseReverse>();

        // source segment
        List<Waypoint> points;
        List<Waypoint> reversedPoints;
        TrackSegment original;
        TrackSegment reversed;

        // reverse, reverse timestamp, n = 0
        points = List.of();
        reversedPoints = List.of();

        original = new TrackSegment(points);
        reversed = new TrackSegment(reversedPoints);

        testData.add(new TestCaseReverse(original, TimestampMode.reverse(), reversed));

        // reverse, reverse timestamp, n = 1
        points = List.of(
            waypoint("52.1651800", "11.6835150", "43.0", "2020-08-06T18:42:22Z")
        );

        reversedPoints = List.of(
            waypoint("52.1651800", "11.6835150", "43.0", "2020-08-06T18:42:22Z")
        );

        original = new TrackSegment(points);
        reversed = new TrackSegment(reversedPoints);

        testData.add(new TestCaseReverse(original, TimestampMode.reverse(), reversed));

        // reverse, reverse timestamp, n > 1
        points = List.of(
            waypoint("52.1650840", "11.6835130", "43.0", "2020-08-06T18:42:15Z"),
            waypoint("52.1651380", "11.6835250", "43.0", "2020-08-06T18:42:17Z"),
            waypoint("52.1651800", "11.6835150", "43.0", "2020-08-06T18:42:22Z")
        );

        reversedPoints = List.of(
            waypoint("52.1651800", "11.6835150", "43.0", "2020-08-06T18:42:15Z"),
            waypoint("52.1651380", "11.6835250", "43.0", "2020-08-06T18:42:20Z"),
            waypoint("52.1650840", "11.6835130", "43.0", "2020-08-06T18:42:22Z")
        );

        original = new TrackSegment(points);
        reversed = new TrackSegment(
            reversedPoints);

        testData.add(new TestCaseReverse(original, TimestampMode.reverse(), reversed));

        return testData;
    }

    private static List<TestCaseReverse> testCasesStartAt() {
        var testData = new ArrayList<TestCaseReverse>();

        // source segment
        List<Waypoint> points;
        List<Waypoint> reversedPoints;
        TrackSegment original;
        TrackSegment reversed;

        ZonedDateTime startAt = ZonedDateTime.parse("2161-07-01T12:00:00Z");

        // reverse, startAt timestamp, n = 0
        points = List.of();
        reversedPoints = List.of();

        original = new TrackSegment(points);
        reversed = new TrackSegment(reversedPoints);

        testData.add(new TestCaseReverse(original, TimestampMode.startAt(startAt), reversed));

        // reverse, startAt timestamp, n = 1
        points = List.of(
            waypoint("52.1651800", "11.6835150", "43.0", "2020-08-06T18:42:22Z")
        );

        reversedPoints = List.of(
            waypoint("52.1651800", "11.6835150", "43.0", "2161-07-01T12:00:00Z")
        );

        original = new TrackSegment(points);
        reversed = new TrackSegment(reversedPoints);

        testData.add(new TestCaseReverse(original, TimestampMode.startAt(startAt), reversed));

        // reverse, startAt timestamp, n > 1
        points = List.of(
            waypoint("52.1650840", "11.6835130", "43.0", "2020-08-06T18:42:15Z"),
            waypoint("52.1651380", "11.6835250", "43.0", "2020-08-06T18:42:17Z"),
            waypoint("52.1651800", "11.6835150", "43.0", "2020-08-06T18:42:22Z")
        );

        reversedPoints = List.of(
            waypoint("52.1651800", "11.6835150", "43.0", "2161-07-01T12:00:00Z"),
            waypoint("52.1651380", "11.6835250", "43.0", "2161-07-01T12:00:05Z"),
            waypoint("52.1650840", "11.6835130", "43.0", "2161-07-01T12:00:07Z")
        );

        original = new TrackSegment(points);
        reversed = new TrackSegment(
            reversedPoints);

        testData.add(new TestCaseReverse(original, TimestampMode.startAt(startAt), reversed));

        return testData;
    }

    private static class TestCaseReverse {

        TrackSegment sourceSegment;
        TimestampMode timestampMode;

        TrackSegment reversedSegment;

        public TestCaseReverse(final TrackSegment sourceSegment, final TimestampMode timestampMode,
            final TrackSegment reversedSegment) {

            this.sourceSegment = sourceSegment;
            this.timestampMode = timestampMode;
            this.reversedSegment = reversedSegment;
        }
    }

}
