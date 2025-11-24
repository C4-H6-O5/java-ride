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
        locations.add(new Location("Grand terminal", 10));
        locations.add(new Location("Don Ramos", 12));
        locations.add(new Location("Capitolio", 15));
        locations.add(new Location("Nuciti", 18));
        locations.add(new Location("Plaza", 25));
        locations.add(new Location("SM Mall", 25));
        locations.add(new Location("BatStateU Pablo Borbon", 30));
        locations.add(new Location("Batangas Pier", 35));
        
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