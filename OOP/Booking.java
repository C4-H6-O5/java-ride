public class Booking {
    private Passenger passenger;     
    private Driver driver;           
    private String pickupPoint;     
    private String dropOffPoint;      
    private double distance;         
    private double amount;           
    private String status;
    
    public Booking(Passenger passenger, String pickupPoint, String dropOffPoint, double distance, double amount) {
        this.passenger = passenger;
        this.pickupPoint = pickupPoint;
        this.dropOffPoint = dropOffPoint;
        this.distance = distance;
        this.amount = amount;
        this.status = "pending";
        this.driver = null;  
    }

    // GETTERS
    public Passenger getPassenger() { return passenger; }
    public Driver getDriver() { return driver; }
    public String getPickupPoint() { return pickupPoint; }
    public String getDropOffPoint() { return dropOffPoint; }
    public double getDistance() { return distance; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }

    // SETTERS
    public void setDriver(Driver driver) { this.driver = driver; }
    public void setStatus(String status) { this.status = status; }
    
    // METHODS
    public void confirmBooking() {
        this.status = "accepted";
        System.out.println("Booking confirmed!");
    }
    
    // Mark booking as cancelled
    public void cancelBooking() {
        this.status = "cancelled";
        System.out.println("Booking cancelled!");
    }
    
    // Display booking information
    public void displayBookingInfo() {
        System.out.println("\n=== Booking Details ===");
        System.out.println("Passenger: " + passenger.getName());
        if (driver != null) {
            System.out.println("Driver: " + driver.getName());
        } else {
            System.out.println("Driver: Not assigned yet");
        }
        System.out.println("Pickup: " + pickupPoint);
        System.out.println("Drop-off: " + dropOffPoint);
        System.out.println("Distance: " + distance + " km");
        System.out.println("Amount: ₱" + amount);
        System.out.println("Status: " + status);
    }
}
