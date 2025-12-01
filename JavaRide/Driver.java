import java.util.ArrayList;
import java.util.List;

public class Driver extends User {
    private Vehicle vehicle;
    private String bio;
    private final double baseRating;
    private final List<Review> reviews;

    public Driver(String name, int age, String address, String contactNumber, Vehicle vehicle, String bio, double rating) {
        super(name, age, address, contactNumber);
        this.vehicle = vehicle;
        this.bio = bio;
        this.baseRating = rating;
        this.reviews = new ArrayList<>();
    }

    public Vehicle getVehicle() { return vehicle; }
    public String getBio() { return bio; }
    public double getRating() {
        if (reviews.isEmpty()) {
            return baseRating;
        }
        
        return reviews.stream().mapToInt(Review::getRating).average().orElse(baseRating);
    }
    public List<Review> getReviews() { return reviews; }

    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
    public void setBio(String bio) { this.bio = bio; }
    public void addReview(Review review) { this.reviews.add(review); }

    @Override
    public String toString() {
        return super.toString() + "\n" +
               "Vehicle: " + (vehicle != null ? vehicle.toString() : "None assigned") + "\n" +
               "Bio: " + bio + "\n" +
               String.format("Rating: %.1f", getRating());
    }
}
