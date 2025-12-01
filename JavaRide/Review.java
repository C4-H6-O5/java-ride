public class Review {
    private Passenger passenger;
    private Driver driver;
    private int rating;
    private String comment;

    public Review(Passenger passenger, Driver driver, int rating, String comment) {
        this.passenger = passenger;
        this.driver = driver;
        this.rating = rating;
        this.comment = comment;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Driver getDriver() {
        return driver;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return String.format("From: %s | Rating: %d/5 - \"%s\"", passenger.getName(), rating, comment);
    }
}