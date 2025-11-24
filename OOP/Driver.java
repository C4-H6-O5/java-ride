public class Driver extends User {
    private Vehicle vehicle;
    private String bio;
    private double rating;

    public Driver(String name, int age, String address, String contactNumber, Vehicle vehicle, String bio, double rating) {
        super(name, age, address, contactNumber);
        this.vehicle = vehicle;
        this.bio = bio;
        this.rating = rating;
    }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    @Override
    public void displayInfo() {
        super.displayInfo();
        if (vehicle != null) {
            System.out.println("Vehicle: " + vehicle);
            System.out.println("Vehicle Type: " + vehicle.getVehicleType());
        } else {
            System.out.println("Vehicle: None assigned");
        }
        System.out.println("Bio: " + bio);
        System.out.println("Rating: " + rating + "⭐");
    }
}
