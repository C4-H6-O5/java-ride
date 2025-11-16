enum BookingStatus {
    PENDING,
    ACCEPTED,
    CANCELLED
}

public class Booking {
    private Passenger passenger;     
    private Driver driver; 
    private Vehicle vehicle;          
    private String pickupPoint;     
    private String dropOffPoint;      
    private double distance;         
    private double amount;           
    private BookingStatus status;
    
    public Booking(Passenger passenger, String pickupPoint, String dropOffPoint, double distance, Vehicle vehicle) {
        this.passenger = passenger;
        this.pickupPoint = pickupPoint;
        this.dropOffPoint = dropOffPoint;
        this.distance = distance;
        this.status = BookingStatus.PENDING;
        this.driver = null;
        this.vehicle = vehicle;
        this.amount = vehicle.calculateFare(distance);  
    }

    // GETTERS
    public Passenger getPassenger() { return passenger; }
    public Driver getDriver() { return driver; }
    public String getPickupPoint() { return pickupPoint; }
    public String getDropOffPoint() { return dropOffPoint; }
    public double getDistance() { return distance; }
    public double getAmount() { return amount; }
    public BookingStatus getStatus() { return status; }
    public Vehicle getVehicle() { return vehicle; }

    // SETTERS
    public void setDriver(Driver driver) { this.driver = driver; }
    public void setStatus(BookingStatus status) { this.status = status; }
    public void setVehicle(Vehicle vehicle) { 
        this.vehicle = vehicle; 
        if(vehicle != null) {
            this.amount = vehicle.calculateFare(this.distance);
        } else {
            this.amount = 0.0;
        }
    }
    
    // METHODS
    public void confirmBooking() {
        this.status = BookingStatus.ACCEPTED;
        System.out.println("Booking confirmed!");
    }
    
    // Mark booking as cancelled
    public void cancelBooking() {
        this.status = BookingStatus.CANCELLED;
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
        if (vehicle != null) {
            System.out.println("Vehicle: " + vehicle.getClass().getSimpleName());
        }
        System.out.println("Distance: " + distance + " km");
        System.out.println("Amount: Php" + amount);
        System.out.println("Status: " + status);
    }
}
