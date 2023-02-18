package co.junwei.cpabe;

import java.util.HashMap;
import java.util.Map;

public class LocationStore {
    private final Map<String, Location> locations;

    private static final LocationStore store = new LocationStore();

    private LocationStore() {
        locations = new HashMap<>();
    }

    public static void addLocation(Location l) {
        store.locations.put(l.locationName, l);
    }

    public static Location getLocation(String locationName) {
        return store.locations.get(locationName);
    }

    public static boolean contains(String locationName) {
        return store.locations.containsKey(locationName);
    }
}
