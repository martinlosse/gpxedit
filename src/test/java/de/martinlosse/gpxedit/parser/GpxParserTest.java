package de.martinlosse.gpxedit.parser;

import com.topografix.gpx.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.xml.bind.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class GpxParserTest {

    @MethodSource("testFiles")
    @ParameterizedTest
    void testParser(TestFile testFile) throws IOException, JAXBException, URISyntaxException {
        Path path = Path.of(getClass().getResource(testFile.resourceName).toURI());

        GpxParser parser = new GpxParser();
        GpxType gpxRecord = parser.parseGpxFile(path);

        assertThat(gpxRecord).isNotNull();

        if (testFile.trackCount == null) {
            return;
        }

        List<TrkType> tracks = gpxRecord.getTrk();
        assertThat(tracks.size()).isEqualTo(testFile.trackCount);

        if (testFile.segmentCount == null) {
            return;
        }

        List<TrksegType> trackSegments = tracks.get(0).getTrkseg();
        assertThat(trackSegments.size()).isEqualTo(testFile.segmentCount);

        if (testFile.pointCounts == null) {
            return;
        }

        for (int i = 0; i < testFile.pointCounts.length; i++) {
            Integer pointCount = testFile.pointCounts[i];

            if (pointCount == null) {
                continue;
            }

            List<WptType> segmentPoints = trackSegments.get(i).getTrkpt();
            assertThat(segmentPoints.size()).isEqualTo(pointCount);
        }
    }

    private static List<TestFile> testFiles() {
        return List.of(
                new TestFile("2020-08-06-run.gpx", 1, 1, new Integer[]{2856})
        );
    }

    private static class TestFile {

        String resourceName;
        Integer trackCount;
        Integer segmentCount;
        Integer[] pointCounts;

        public TestFile(String resourceName, Integer trackCount, Integer segmentCount, Integer[] pointCounts) {
            this.resourceName = resourceName;
            this.trackCount = trackCount;
            this.segmentCount = segmentCount;
            this.pointCounts = pointCounts;
        }
    }

}
