public class Driver extends User {
    private String vehicleType;
    private String bio;
    private double rating;

    public Driver(String name, int age, String address, String contactNumber, String vehicleType, String bio, double rating) {
        super(name, age, address, contactNumber);
        this.vehicleType = vehicleType;
        this.bio = bio;
        this.rating = rating;
    }

    public String getVehicleType() { return vehicleType; }

    public void setRating(double rating) { this.rating = rating; }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Vehicle Type: " + vehicleType);
        System.out.println("Bio: " + bio);
        System.out.println("Rating: " + rating + "⭐");
    }
}
