package de.martinlosse.gpxedit.parser;

import com.topografix.gpx.GpxType;
import com.topografix.gpx.MetadataType;
import com.topografix.gpx.RteType;
import com.topografix.gpx.TrkType;
import com.topografix.gpx.TrksegType;
import com.topografix.gpx.WptType;
import de.martinlosse.gpxedit.model.Constants;
import de.martinlosse.gpxedit.model.GpxFile;
import de.martinlosse.gpxedit.model.Route;
import de.martinlosse.gpxedit.model.Track;
import de.martinlosse.gpxedit.model.TrackSegment;
import de.martinlosse.gpxedit.model.Waypoint;
import de.martinlosse.gpxedit.util.XmlTimeConverter;
import java.util.List;
import java.util.stream.Collectors;

public class GpxConverter {
    
    private final XmlTimeConverter timeConverter;
    
    public GpxConverter(XmlTimeConverter timeConverter) {
        this.timeConverter = timeConverter;
    }
    
    public GpxType toXml(GpxFile gpxFile) {
        MetadataType meta = new MetadataType();
        meta.setTime(timeConverter.toXmlTime(gpxFile.getTimestamp()));
        
        GpxType gpxRecord = new GpxType();
        gpxRecord.setCreator(gpxFile.getCreator());
        gpxRecord.setVersion(gpxFile.getVersion());
        gpxRecord.setMetadata(meta);
        gpxRecord.getWpt().addAll(toXmlWpt(gpxFile.getWaypoints()));
        gpxRecord.getRte().addAll(toXmlRte(gpxFile.getRoutes()));
        gpxRecord.getTrk().addAll(toXmlTrk(gpxFile.getTracks()));
        
        return gpxRecord;
    }
    
    private List<WptType> toXmlWpt(List<Waypoint> waypoints) {
        return waypoints.stream()
                .map(this::toXml)
                .collect(Collectors.toList());
    }
    
    private WptType toXml(Waypoint waypoint) {
        WptType wpt = new WptType();
        wpt.setLat(waypoint.getLatitude());
        wpt.setLon(waypoint.getLongitude());
        wpt.setEle(waypoint.getElevation());
        wpt.setTime(timeConverter.toXmlTime(waypoint.getTimestamp()));
        
        return wpt;
    }
    
    private List<RteType> toXmlRte(List<Route> routes) {
        return routes.stream()
                .map(this::toXml)
                .collect(Collectors.toList());
    }
    
    private RteType toXml(Route route) {
        RteType rte = new RteType();
        rte.setName(route.getName());
        rte.setType(route.getType());
        rte.getRtept().addAll(toXmlWpt(route.getRoutePoints()));
        
        return rte;
    }
    
    private List<TrkType> toXmlTrk(List<Track> tracks) {
        return tracks.stream()
                .map(this::toXml)
                .collect(Collectors.toList());
    }
    
    private TrkType toXml(Track track) {
        TrkType trk = new TrkType();
        trk.setName(track.getName());
        trk.setType(track.getType());
        trk.getTrkseg().addAll(toXmlSeg(track.getSegments()));
        
        return trk;
    }
    
    private List<TrksegType> toXmlSeg(List<TrackSegment> segments) {
        return segments.stream()
                .map(this::toXml)
                .collect(Collectors.toList());
    }
    
    private TrksegType toXml(TrackSegment segment) {
        TrksegType trkSeg = new TrksegType();
        trkSeg.getTrkpt().addAll(toXmlWpt(segment.getTrackPoints()));
        
        return trkSeg;
    }
    
    public GpxFile fromXml(GpxType gpxRecord) throws UnsupportedGpxVersionException {
        if (!gpxRecord.getVersion().equals(Constants.GPX_VERSION)) {
            throw new UnsupportedGpxVersionException("Version not supported: " + gpxRecord.getVersion());
        }
        return new GpxFile(gpxRecord.getCreator(),
                timeConverter.fromXmlTime(gpxRecord.getMetadata().getTime()),
                fromXmlWpt(gpxRecord.getWpt()),
                fromXmlRte(gpxRecord.getRte()),
                fromXmlTrk(gpxRecord.getTrk())
            );
    }
    
    private List<Waypoint> fromXmlWpt(List<WptType> wpt) {
        return wpt.stream()
                .map(this::fromXml)
                .collect(Collectors.toList());
    }
    
    private Waypoint fromXml(WptType wpt) {
        return new Waypoint(wpt.getLat(), wpt.getLon(), wpt.getEle(), timeConverter.fromXmlTime(wpt.getTime()));
    }
    
    private List<Route> fromXmlRte(List<RteType> rte) {
        return rte.stream()
                .map(this::fromXml)
                .collect(Collectors.toList());
    }
    
    private Route fromXml(RteType rte) {
        return new Route(rte.getName(), rte.getType(), fromXmlWpt(rte.getRtept()));
    }
    
    private List<Track> fromXmlTrk(List<TrkType> trk) {
        return trk.stream()
                .map(this::fromXml)
                .collect(Collectors.toList());
    }
    
    private Track fromXml(TrkType trk) {
        return new Track(trk.getName(), trk.getType(), fromXmlSeg(trk.getTrkseg()));
    }
    
    private List<TrackSegment> fromXmlSeg(List<TrksegType> trkSeg) {
        return trkSeg.stream()
                .map(this::fromXml)
                .collect(Collectors.toList());
    }
    
    private TrackSegment fromXml(TrksegType seg) {
        return new TrackSegment(fromXmlWpt(seg.getTrkpt()));
    }
    
}
