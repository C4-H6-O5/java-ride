import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {
    private static Random random = new Random();
    
    // Preset data pools
    private static String[] firstNames = {
        "Mhalik", "Regina", "Elai", "Rejc", "Vincent", "Fatima", 
        "Ritzy", "Maurice", "Jennifer", "Glenn", "Jom", "Sheena",
        "Nicko", "Francis", "Cole", "Israel", "Trevor", "Sofhia"
    };
    
    private static String[] lastNames = {
        "Perez", "Bool", "Espartinez", "Guillo", "Doria", "Agdon",
        "Celestial", "Dela Cruz", "Mendoza", "Guinoban", "Manguit", "Abe",
        "Palicpic", "Santiago", "Bulaon", "Penero", "Marcos", "Asilo"
    };
    
    private static String[] cities = {
        "Manila", "Quezon City", "Makati", "Pasig", "Taguig", 
        "Mandaluyong", "Marikina", "Las Pinas", "Paranaque", "Valenzuela"
    };
    
    private static String[] vehicleTypes = {"Motorcycle", "Normal", "Premium"};
    
    private static String[] userTypes = {"Regular", "Student", "Senior", "PWD"};
    
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
    
    // Generate random phone number
    private static String generatePhoneNumber() {
        return "09" + (100000000 + random.nextInt(900000000));
    }
    
    // Generate random driver
    public static Driver generateDriver() {
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        String name = firstName + " " + lastName;
        
        int age = 25 + random.nextInt(20); // Age between 25-44
        String city = cities[random.nextInt(cities.length)];
        String contact = generatePhoneNumber();
        String vehicleType = vehicleTypes[random.nextInt(vehicleTypes.length)];
        String bio = driverBios[random.nextInt(driverBios.length)];
        double rating = 3.0 + (random.nextDouble() * 2.0); // Rating between 3.0-5.0
        
        return new Driver(name, age, city, contact, vehicleType, bio, rating);
    }
    
    // Generate random passenger
    public static Passenger generatePassenger() {
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        String name = firstName + " " + lastName;
        
        int age = 18 + random.nextInt(50); // Age between 18-67
        String city = cities[random.nextInt(cities.length)];
        String contact = generatePhoneNumber();
        String userType = userTypes[random.nextInt(userTypes.length)];
        
        String idNumber = "";
        if (!userType.equals("Regular")) {
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
}