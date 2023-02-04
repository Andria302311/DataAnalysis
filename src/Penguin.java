
import java.util.List;

public class Penguin {
    private List<Geo> locations;
    private String trackID;

    public Penguin(List<Geo> locations, String trackID) {
        this.locations = locations;
        this.trackID = trackID;
    }

    @Override
    public String toString() {
        return "Penguin{" +
                "locations=" + locations +
                ", trackID='" + trackID + '\'' +
                '}';
    }

    public List<Geo> getLocations() {
        return locations;
    }

    public String getTrackID() {
        return trackID;
    }

    public String toStringUsingStreams() {
        StringBuilder sb = new StringBuilder();
        sb.append("Penguin{locations=[");
        locations.stream()
                .sorted((loc1, loc2) -> {
                    if (loc1.latitude == loc2.latitude) {
                        return (loc1.longitude < loc2.longitude) ? 1 : -1;
                    }
                    return (loc1.latitude < loc2.latitude) ? 1 : -1;
                })
                .forEach(geo -> sb.append(geo.toString()).append(", "));
        sb.append("], trackID='").append(trackID).append("'}");
        return sb.toString();
    }
}
