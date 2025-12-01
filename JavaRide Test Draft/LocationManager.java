import java.util.*;

public class LocationManager {
    public static class Location {
        private final String name;
        private final int value;

        public Location(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() { return name; }
        public int getValue() { return value; }

        @Override
        public String toString() {
            return name;
        }
    }

    private final List<Location> locations;

    public LocationManager() {
        this.locations = new ArrayList<>();
        initializeLocations();
    }

    private void initializeLocations() {
        locations.add(new Location("BatStateU Alangilan", 0));
        locations.add(new Location("Grand terminal", 5));
        locations.add(new Location("Don Ramos", 7));
        locations.add(new Location("Capitolio", 10));
        locations.add(new Location("Nuciti", 12));
        locations.add(new Location("Plaza", 15));
        locations.add(new Location("SM Mall", 17));
        locations.add(new Location("BatStateU Pablo Borbon", 20));
        locations.add(new Location("Batangas Pier", 25));
        
        locations.sort(Comparator.comparing(Location::getName));
    }

    public List<Location> getLocations() {
        return Collections.unmodifiableList(locations);
    }

    public Location getLocationByIndex(int index) {
        if (index >= 0 && index < locations.size()) {
            return locations.get(index);
        }
        return null;
    }
}