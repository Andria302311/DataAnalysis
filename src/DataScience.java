
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class DataScience {

    public static void outputLocationInformation(PenguinData data) {
        System.out.println("Penguin ID:" + data.fid + " Longitute: " + data.longitude + " Latitude: " +
                data.latitude);
    }

    public static Stream<Penguin> getDataByTrackId(Stream<PenguinData> stream) {
        Map<String, List<PenguinData>> mapped = stream.collect(groupingBy(PenguinData::getTrackID, toList()));
        return mapped.entrySet().stream()
                .map((entry) -> new Penguin(entry.getValue().stream().map(penguinData -> penguinData.getGeom()).collect(
                        Collectors.toUnmodifiableList()), entry.getKey()));
    }

    public static void outputPenguinStream() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDataByTrackId(CSVReading.processInputFile()).count());
        getDataByTrackId(CSVReading.processInputFile())
                .sorted(Comparator.comparing(Penguin::getTrackID))
                .forEach(penguin -> {
                    sb.append(System.lineSeparator());
                    sb.append(penguin.toStringUsingStreams());
                });
        System.out.println(sb.toString());
    }

    public static List<Double> getMinMaxPositionsForPenguin(Penguin penguin) {
        double longitudeMin = penguin.getLocations().stream()
                .mapToDouble(Geo::getLongitude).min().getAsDouble();
        double longitudeMax = penguin.getLocations().stream()
                .mapToDouble(Geo::getLongitude).max().getAsDouble();
        double latitudeMin = penguin.getLocations().stream()
                .mapToDouble(Geo::getLatitude).min().getAsDouble();
        double latitudeMax = penguin.getLocations().stream()
                .mapToDouble(Geo::getLatitude).max().getAsDouble();
        double longitudeAvg = penguin.getLocations().stream()
                .mapToDouble(Geo::getLongitude).average().getAsDouble();
        double latitudeAvg = penguin.getLocations().stream()
                .mapToDouble(Geo::getLatitude).average().getAsDouble();
        return List.of(longitudeMin, longitudeMax, longitudeAvg, latitudeMin, latitudeMax, latitudeAvg);
    }

    public static void outputLocationRangePerTrackid(Stream<PenguinData> stream) {
        getDataByTrackId(stream)
                .sorted(Comparator.comparing(Penguin::getTrackID))
                .map(penguin -> {
                    System.out.println(penguin.getTrackID());
                    return getMinMaxPositionsForPenguin(penguin);
                }).forEach(list -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Min Longitude: ");
                    sb.append(list.get(0));
                    sb.append(" Max Longitude: ");
                    sb.append(list.get(1));
                    sb.append(" Avg Longitude: ");
                    sb.append(list.get(2));
                    sb.append(" Min Latitude: ");
                    sb.append(list.get(3));
                    sb.append(" Max Latitude: ");
                    sb.append(list.get(4));
                    sb.append(" Avg Latitude: ");
                    sb.append(list.get(5));
                    System.out.println(sb.toString());
                });
    }
}
