import java.util.*;

public class LocationManager {
    private Map<String, Integer> locations = new HashMap<>();
    private ArrayList<String> locationList = new ArrayList<>();

    public LocationManager() {
        listLocations();
    }

    private void listLocations() {
        locations.put("BatStateU Alangilan", 0);
        locations.put("Grand terminal", 10);
        locations.put("Don Ramos", 12);
        locations.put("Capitolio", 15);
        locations.put("Nuciti", 18);
        locations.put("Plaza", 25);
        locations.put("SM Mall", 25);
        locations.put("BatStateU Pablo Borbon", 30);
        locations.put("Batangas Pier", 35);

        locationList.addAll(locations.keySet());
        Collections.sort(locationList); 
    }

    public void displayLocations() {
        for (int i = 0; i < locationList.size(); i++) {
            System.out.println((i + 1) + ". " + locationList.get(i));
        }
    }

    public List<String> getLocationList() {return locationList;}

    public String getLocationByIndex(int index) {return locationList.get(index);}

    public int getValue(String location) {return locations.get(location);}

    public int computeDistance(String from, String to) {
        return Math.abs(locations.get(from) - locations.get(to));
    }

    public String removeLocationByIndex(int index) {
        return locationList.remove(index);
    }

    public void addLocationAtIndex(String location, int index) {
        locationList.add(index, location);
    }
}