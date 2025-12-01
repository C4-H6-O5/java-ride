public class Booking {
    private final Passenger passenger;
    private Driver driver; 
    private Vehicle vehicle;
    private final LocationManager.Location pickupPoint;
    private final LocationManager.Location dropOffPoint;
    private final double distance;
    private double amount;           
    private final int numberOfPassengers;
    private BookingStatus status;
    
    private boolean paidOut;
    public Booking(Passenger passenger, LocationManager.Location pickupPoint, LocationManager.Location dropOffPoint, Vehicle vehicle, int numberOfPassengers) {
        this.passenger = passenger;
        this.pickupPoint = pickupPoint;
        this.dropOffPoint = dropOffPoint;
        this.vehicle = vehicle;
        this.distance = Math.abs(pickupPoint.getValue() - dropOffPoint.getValue());
        this.amount = vehicle.calculateFare(this.distance);
        this.numberOfPassengers = numberOfPassengers;
        this.status = BookingStatus.PENDING;
        this.paidOut = false;
        this.driver = null;
    }

    public Passenger getPassenger() { return passenger; }
    public Driver getDriver() { return driver; }
    public LocationManager.Location getPickupPoint() { return pickupPoint; }
    public LocationManager.Location getDropOffPoint() { return dropOffPoint; }
    public double getDistance() { return distance; }
    public double getAmount() { return amount; }
    public BookingStatus getStatus() { return status; }
    public Vehicle getVehicle() { return vehicle; }
    public int getNumberOfPassengers() { return numberOfPassengers; }
    public boolean isPaidOut() { return paidOut; }

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
    public void setPaidOut(boolean paidOut) { this.paidOut = paidOut; }
    
    public void confirmBooking() {
        this.status = BookingStatus.ACCEPTED;
    }
    
    public void cancelBooking() {
        this.status = BookingStatus.CANCELLED;
    }
    
    @Override
    public String toString() {
        return String.format(Utility.BOLD + "Booking for %s from %s to %s " + Utility.CYAN + "[Status: %s]" + Utility.RESET,
            passenger.getName(), pickupPoint.getName(), dropOffPoint.getName(), status);
    }

    public String toPassengerString() {
        StringBuilder sb = new StringBuilder();
        if (driver != null) {
            sb.append("Driver: ").append(driver.getName())
              .append(" (").append(vehicle.toString()).append(")")
              .append(" | Rating: ").append(String.format("%.1f", driver.getRating())).append("\n");
        } else {
            sb.append("Not assigned yet\n");
        }
        sb.append("Pickup: ").append(pickupPoint.getName()).append("\n");
        sb.append("Drop-off: ").append(dropOffPoint.getName()).append("\n");
        sb.append("Distance: ").append(String.format("%.1f km", distance)).append("\n");
        sb.append("Amount: ").append(String.format("Php %.2f", amount)).append("\n");
        sb.append("Status: ").append(status);
        return sb.toString();
    }

    public String toDriverString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Passenger: ").append(passenger.getName()).append("\n");
        sb.append("Pickup: ").append(pickupPoint.getName()).append("\n");
        sb.append("Drop-off: ").append(dropOffPoint.getName()).append("\n");
        sb.append("Distance: ").append(String.format("%.1f km", distance)).append("\n");
        sb.append("Amount: ").append(String.format("Php %.2f", amount)).append("\n");
        sb.append("Status: ").append(status);
        return sb.toString();
    }
}
