#GpxEditor

This is a simple UI tool to edit GPX files.

## Entities

### Record        
Represents the complete GPX file with meta data and waypoint, route and track information. A record may contain 0 or more tracks.   

### Track
A track will usually describe the exact path of travel of a GPS unit. A track consists of 0 or more track segments.    
Most of the time a GPX file will have a single track.

### Track Segment
Each track segment is a part of the path travelled. 
If the recording of a track is interrupted (e.g. GPS lost, workout paused) it should be split into multiple segments that each represent a consecutive set of waypoints. 

### Waypoint
A waypoint represents an exact location given in GPS coordinates (latitude, longitude, elevation) and may be extended by additional information.  
Additional information will usually include a timestamp (if part of a track segment)

## Operations

### Calculations

#### determine average waypoint distance

| Parameter | description |
| ---       | ---         | 
| segment   | The track segment to examine.  |

Determines the average distance between waypoints adjacent waypoints on the given track segment

### Modifications

#### split

| Parameter | description |
| ---       | ---         | 
| segment   | The track segment to split.  |
| index     | The index at which the split is to be performed.  |

Splits a given track segment into two. Track segments are lists of waypoints with a zero-based index.  
Given a segment (a,b) where a is start index and b end index, splitting the segment at index n will result in 2 track segments (a, n-1), (n, b).  
Preconditions
- if n < a: operation fails
- if n = a: results in (), (a,b)
- if n > b + 1: operation fails  
- if n = b + 1: results in (a, b), ()

#### reverse

| Parameter | description |
| ---       | ---         | 
| segment   | The track segment to reverse.  |
| timestampMode | The mode by which to handle timestamps |

values for timestampMode
- drop: Drops timestamps from all waypoints.
- keep: Waypoint timestamps are kept as is.  
- reverse: Exchanges timestamps of start and end waypoint. The timestamps for the remaining waypoints are calculated based on the new starting timestamp and the original diffs between the waypoints.  
- startAt: 

#### create track segment

| Parameter  | description |
| ---        | ---         | 
| waypoint1  | The starting waypoint.  |
| waypoint2  | The target waypoint.  |
| distance   | Distance in metres between two waypoints. | 
| inclusive  | Optional: If `true` will include the given waypoints in the track, otherwise the given waypoints are excluded. Default: `false` | 

Creates a track segment as a straight line between the given points 

#### merge

| Parameter | description |
| ---       | ---         | 
| segment1  | A track segment.  |
| segment2  | A track segment. Will be appended to the first segment. |
| segments  | Optional: An array of track segments to append to the previous segments. |

Merges the given segments by appending each segment after the first to the previous segment.

#### rewrite timestamps

| Parameter | description |
| ---       | ---         | 
| segment   | The track segment to modify.  |
| start     | The start time for the segment. Will be applied to the first waypoint. |
| end       | The end time for the segment. Will be applied to the last segment.     |

Rewrites the timestamp on every waypoint of the given segment. 
The first and the last segment assume the start and end time respectively.
Approximates the timestamps of intermediate waypoints based on 
- the duration between start and end times and
- the number of waypoints in between. 
