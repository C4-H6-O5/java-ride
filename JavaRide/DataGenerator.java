import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {
    private static Random random = new Random();
    
    private static String[] firstNames = {
        "Mhalik", "Regina", "Elaiza", "Rejc", "Vincent", "Ritzy", 
        "Fatima", "Maurice", "Lloyd", "Jenny", "Glen", "Karen",
        "Joey", "Aries", "Jei", "Nayeon", "Hyunjin", "Minho"
    };
    
    private static String[] lastNames = {
        "Perez", "Bool", "Espartinez", "Guillo", "Doria", "Celestial",
        "Agdon", "Dela Cruz", "Macatangay", "Mendoza", "Magadia", "Kim",
        "De Castro", "Park", "Pastrana", "Im", "Hwang", "Lee"
    };
    
    private static String[] cities = {
        "Manila", "Quezon City", "Makati", "Pasig", "Taguig", 
        "Mandaluyong", "Marikina", "Las Pinas", "Paranaque", "Valenzuela"
    };
    
    private static VehicleType[] vehicleTypes = VehicleType.values();
    private static UserType[] userTypes = UserType.values();
    
    private static String[] driverBios = {
        "Experienced driver with excellent safety record.",
        "Friendly driver who knows the city well.",
        "Professional driver providing comfortable rides.",
        "Quick and efficient. Always on time!",
        "Safe driver with years of experience.",
        "Comfortable rides with great service!",
        "Reliable driver perfect for your journey.",
        "Top-rated driver with luxury vehicle."
    };
    
    private static String[] reviewComments = {
        "Great ride, very smooth and safe.",
        "The driver was very friendly and professional.",
        "Clean car and a very comfortable trip. Highly recommended!",
        "Got me to my destination quickly. Thanks!",
        "A pleasant and enjoyable journey."
    };

    // Generate random phone number
    private static String generatePhoneNumber() {
        return "09" + (100000000 + random.nextInt(900000000));
    }
    
    private static Vehicle createRandomVehicle() {
        VehicleType type = vehicleTypes[random.nextInt(vehicleTypes.length)];
        
        StringBuilder plateBuilder = new StringBuilder();
        // Append 3 random uppercase letters
        for (int i = 0; i < 3; i++) {
            plateBuilder.append((char) ('A' + random.nextInt(26)));
        }
        // Append a 3-digit number (100-999)
        plateBuilder.append(100 + random.nextInt(900));
        String plateNumber = plateBuilder.toString();
        switch (type) {
            case MOTORCYCLE:
                return new Motorcycle(plateNumber);
            case NORMAL:
                return new Normal(plateNumber);
            case PREMIUM:
                return new Premium(plateNumber);
            default: 
                return new Normal(plateNumber);
        }
    }

    // Generate random driver
    public static Driver generateDriver() {
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        String name = firstName + " " + lastName;
        
        int age = 25 + random.nextInt(20); // Age between 25-44
        String city = cities[random.nextInt(cities.length)];
        String contact = generatePhoneNumber();
        Vehicle vehicle = createRandomVehicle(); // Create a vehicle object
        String bio = driverBios[random.nextInt(driverBios.length)];
        double rating = 3.0 + (random.nextDouble() * 2.0); // Rating between 3.0-5.0
        
        return new Driver(name, age, city, contact, vehicle, bio, rating);
    }
    
    // Generate random passenger
    public static Passenger generatePassenger() {
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        String name = firstName + " " + lastName;
        
        int age = 18 + random.nextInt(50); // Age between 18-67
        String city = cities[random.nextInt(cities.length)];
        String contact = generatePhoneNumber();
        UserType userType = userTypes[random.nextInt(userTypes.length)]; 
        
        String idNumber = "";
        if (userType != UserType.REGULAR) {
            // Generate random ID number for special types
            idNumber = "ID" + (100000 + random.nextInt(900000));
        }
        
        return new Passenger(name, age, city, contact, userType, idNumber);
    }
    
    // Generate multiple drivers
    public static List<Driver> generateDrivers(int count) {
        List<Driver> drivers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            drivers.add(generateDriver());
        }
        return drivers;
    }
    
    // Generate multiple passengers
    public static List<Passenger> generatePassengers(int count) {
        List<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            passengers.add(generatePassenger());
        }
        return passengers;
    }

    public static List<Booking> generateBookings(int count, LocationManager locationManager) {
        List<Booking> bookings = new ArrayList<>();
        List<Passenger> mockPassengers = generatePassengers(count);
        List<LocationManager.Location> locations = locationManager.getLocations();
        int numLocations = locations.size();

        for (int i = 0; i < count; i++) {
            Passenger p = mockPassengers.get(i);
            int numPassengers = 1;

            // Select two different random locations
            int pickupIndex = random.nextInt(numLocations);
            int dropoffIndex;
            do {
                dropoffIndex = random.nextInt(numLocations);
            } while (pickupIndex == dropoffIndex);

            Vehicle tempVehicle = createRandomVehicle();
            Booking booking = new Booking(p, locations.get(pickupIndex), locations.get(dropoffIndex), tempVehicle, numPassengers);
            bookings.add(booking);
        }
        return bookings;
    }

    public static String getRandomReviewComment() {
        if (reviewComments == null || reviewComments.length == 0) return "Good ride.";
        return reviewComments[random.nextInt(reviewComments.length)];
    }
}