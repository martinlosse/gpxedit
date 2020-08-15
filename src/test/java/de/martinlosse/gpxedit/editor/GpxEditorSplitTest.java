package de.martinlosse.gpxedit.editor;

import static de.martinlosse.gpxedit.util.GpxXmlUtil.waypoint;
import static org.assertj.core.api.Assertions.*;

import de.martinlosse.gpxedit.model.TrackSegment;
import de.martinlosse.gpxedit.model.Waypoint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class GpxEditorSplitTest {

    @MethodSource("testCases")
    @ParameterizedTest
    void testSplitSegments(TestCaseSplit testCase) {
        List<TrackSegment> splitSegments = GpsEditor.split(testCase.sourceSegment, testCase.splitIndex);

        assertThat(splitSegments.size()).isEqualTo(2);
        assertThat(splitSegments.get(0)).isEqualTo(testCase.splitSegment1);
        assertThat(splitSegments.get(1)).isEqualTo(testCase.splitSegment2);
    }

    private static List<TestCaseSplit> testCases() {
        List<TestCaseSplit> testCases = new ArrayList<>();

        // source segment
        List<Waypoint> points = List.of(
            waypoint("52.1650840", "11.6835130", "43.0", "2020-08-06T18:42:15Z"),
            waypoint("52.1651380", "11.6835250", "43.0", "2020-08-06T18:42:17Z"),
            waypoint("52.1651800", "11.6835150", "43.0", "2020-08-06T18:42:22Z")
        );

        TrackSegment segment = new TrackSegment(points);

        Integer splitIndex;
        TrackSegment splitSeg1;
        TrackSegment splitSeg2;

        // split 1
        splitIndex = 0;
        splitSeg1 = new TrackSegment(Collections.emptyList());
        splitSeg2 = new TrackSegment(points);
        testCases.add(new TestCaseSplit(segment, splitIndex, splitSeg1, splitSeg2));

        // split 2
        splitIndex = 1;
        splitSeg1 = new TrackSegment(List.of(points.get(0)));
        splitSeg2 = new TrackSegment(List.of(points.get(1), points.get(2)));
        testCases.add(new TestCaseSplit(segment, splitIndex, splitSeg1, splitSeg2));

        // split 3
        splitIndex = 2;
        splitSeg1 = new TrackSegment(List.of(points.get(0), points.get(1)));
        splitSeg2 = new TrackSegment(List.of(points.get(2)));
        testCases.add(new TestCaseSplit(segment, splitIndex, splitSeg1, splitSeg2));

        // split 4
        splitIndex = 3;
        splitSeg1 = new TrackSegment(List.of(points.get(0), points.get(1), points.get(2)));
        splitSeg2 = new TrackSegment(Collections.emptyList());
        testCases.add(new TestCaseSplit(segment, splitIndex, splitSeg1, splitSeg2));

        return testCases;
    }

    private static class TestCaseSplit {

        TrackSegment sourceSegment;

        int splitIndex;

        TrackSegment splitSegment1;
        TrackSegment splitSegment2;

        public TestCaseSplit(TrackSegment sourceSegment, int splitIndex, TrackSegment splitSegment1, TrackSegment splitSegment2) {
            this.sourceSegment = sourceSegment;
            this.splitIndex = splitIndex;
            this.splitSegment1 = splitSegment1;
            this.splitSegment2 = splitSegment2;
        }
    }

}
