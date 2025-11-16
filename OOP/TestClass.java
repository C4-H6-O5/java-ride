import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TestClass {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Welcome to Java Ride!");
        System.out.println("Are you registering as Passenger or Driver? ");
        System.out.println("[1] Passenger");
        System.out.println("[2] Driver");
        int role = sc.nextInt();
        sc.nextLine();

        if (role == 1) {
            // passenger registration
            System.out.print("Enter name: ");
            String name = sc.nextLine();

            System.out.print("Enter age: ");
            int age = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter address: ");
            String address = sc.nextLine();

            System.out.print("Enter contact number: ");
            String contactNumber = sc.nextLine();

            System.out.print("Enter user type (Student, PWD, Senior, Regular): ");
            String userType = sc.nextLine();

            String idNumber = "N/A";
            if (!userType.equalsIgnoreCase("Regular")) {
                System.out.print("Enter ID number: ");
                idNumber = sc.nextLine();
            }

            Passenger passenger = new Passenger(name, age, address, contactNumber, userType, idNumber);

            System.out.println("\nPassenger Details:");
            passenger.displayInfo();

            System.out.println("\nCreate Booking:");

            System.out.print("Enter pickup point: "); //Make a preset locations for the pickup and drop-off point next time
            String pickupPoint = sc.nextLine();

            System.out.print("Enter drop-off point: ");
            String dropOffPoint = sc.nextLine();

            System.out.print("Enter distance (km): ");
            double distance = sc.nextDouble();

            System.out.print("Enter fare amount: ");
            double amount = sc.nextDouble();
            sc.nextLine();

            Booking booking = new Booking(passenger, pickupPoint, dropOffPoint, distance, amount);
            booking.displayBookingInfo();

            // get drivers
            int driverCount = 4 + random.nextInt(5);
            List<Driver> availableDrivers = DataGenerator.generateDrivers(driverCount);
            
            System.out.println("\nAvailable Drivers:");
            for (int i = 0; i < availableDrivers.size(); i++) {
                Driver d = availableDrivers.get(i);
                System.out.println((i + 1) + ". " + d.getName() + " - " + d.getVehicleType() + 
                " | Rating: " + String.format("%.1f", d.getRating()) + " | " + d.getBio());
            }
            
            System.out.print("\nSelect driver (1-" + availableDrivers.size() + "): ");
            int driverChoice = sc.nextInt();
            sc.nextLine();
            
            if (driverChoice >= 1 && driverChoice <= availableDrivers.size()) {
                Driver selectedDriver = availableDrivers.get(driverChoice - 1);
                System.out.println("\nSelected: " + selectedDriver.getName());
                selectedDriver.displayInfo();
                
                booking.setDriver(selectedDriver);
                booking.confirmBooking();
                booking.displayBookingInfo();
            } else {
                System.out.println("Invalid.");
            }

        } else if (role == 2) {
            System.out.print("Enter name: ");
            String name = sc.nextLine();

            System.out.print("Enter age: ");
            int age = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter address: ");
            String address = sc.nextLine();

            System.out.print("Enter contact number: ");
            String contactNumber = sc.nextLine();

            System.out.print("Enter vehicle type (Motorcycle, Normal, Premium): ");
            String vehicleType = sc.nextLine();

            System.out.print("Enter short bio: ");
            String bio = sc.nextLine();

            System.out.print("Enter rating (e.g. 4.9): ");
            double rating = sc.nextDouble();
            sc.nextLine();

            Driver driver = new Driver(name, age, address, contactNumber, vehicleType, bio, rating);

            System.out.println("\nDriver Details:");
            driver.displayInfo();

            // generate bookings
            System.out.println("\nAvailable Bookings:");
            int bookingCount = 3 + random.nextInt(4);
            List<Booking> availableBookings = new ArrayList<>();
            
            String[] locations = {"SM Mall", "University", "Airport", "Downtown", "Hospital", "Beach", "Shopping Center", "Train Station"};
            
            for (int i = 0; i < bookingCount; i++) {
                Passenger p = DataGenerator.generatePassenger();
                String pickup = locations[random.nextInt(locations.length)];
                String dropoff = locations[random.nextInt(locations.length)];
                double distance = 2.0 + (random.nextDouble() * 15.0);
                double amount = 50.0 + (distance * 10.0) + (random.nextDouble() * 50.0);
                
                Booking b = new Booking(p, pickup, dropoff, distance, amount);
                availableBookings.add(b);
            }
            
            for (int i = 0; i < availableBookings.size(); i++) {
                Booking b = availableBookings.get(i);
                System.out.println((i + 1) + ". Passenger: " + b.getPassenger().getName() +
                " | From: " + b.getPickupPoint() + " → To: " + b.getDropOffPoint() +
                " | Distance: " + b.getDistance() + " km | Fare: ₱" + String.format("%.2f", b.getAmount()));
            }
            
            System.out.print("\nSelect booking (1-" + availableBookings.size() + "): ");
            int bookingChoice = sc.nextInt();
            sc.nextLine();
            
            if (bookingChoice >= 1 && bookingChoice <= availableBookings.size()) {
                Booking selectedBooking = availableBookings.get(bookingChoice - 1);
                selectedBooking.setDriver(driver);
                selectedBooking.confirmBooking();
                selectedBooking.displayBookingInfo();
            } else {
                System.out.println("Invalid.");
            }

        } else {
            System.out.println("Invalid role.");
        }

        sc.close();
    }
}
