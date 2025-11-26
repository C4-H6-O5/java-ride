import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static final LocationManager locationManager = new LocationManager();

    private static final List<Passenger> passengerAccounts = new ArrayList<>();
    private static final List<Driver> driverAccounts = new ArrayList<>();
    private static final List<Booking> allBookings = new ArrayList<>();

    public static void main(String[] args) {
        driverAccounts.addAll(DataGenerator.generateDrivers(10));

        System.out.println("Hello! Welcome to the JavaRide app 🛺");
        System.out.print("New to JavaRide? Press 'Enter' to Sign Up! ");
        input.nextLine();

        boolean isRunning = true;
        while (isRunning) {
            Utility.clearConsole();
            System.out.println("\nYour Journey Starts Here 🛤️");
            System.out.println("\nPlease select your Account Type:");
            System.out.println("[1] I'm a Passenger");
            System.out.println("[2] I'm a Driver");
            System.out.println("[3] Exit Application\n");
            System.out.print("Account Type: ");
            
            int choice = Utility.getIntInput(input);

            switch (choice) {
                case 1:
                    runPassengerFlow();
                    break;
                case 2:
                    runDriverFlow();
                    break;
                case 3:
                    isRunning = false;
                    System.out.println("\nThank you for using JavaRide. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        input.close(); 
    }
    
    // PASSENGER FLOW!

    private static void runPassengerFlow() {
        Passenger currentPassenger = registerPassenger();
        if (currentPassenger == null) return;

        Utility.welcomePrompt(input, currentPassenger.getName());
        passengerAccounts.add(currentPassenger);

        boolean passengerSession = true;
        while (passengerSession) {
            Utility.clearConsole();
            System.out.println("\n--- Passenger Menu ---");
            System.out.println("[1] Book a Ride");
            System.out.println("[2] View My Profile & Booking History");
            System.out.println("[3] Logout");
            System.out.print("Select an option: ");
            int choice = Utility.getIntInput(input);

            switch (choice) {
                case 1:
                    bookARide(currentPassenger);
                    break;
                case 2:
                    viewPassengerProfile(currentPassenger);
                    break;
                case 3:
                    passengerSession = false;
                    System.out.println("\nLogging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static Passenger registerPassenger() {
        Utility.clearConsole();
        System.out.println("\n--- New Passenger Registration ---");
    
        System.out.print("Name: ");
        String name = input.nextLine();

        System.out.print("Age: ");
        int age = Utility.getIntInput(input);

        System.out.print("Address: ");
        String address = input.nextLine();

        System.out.print("Contact Number: ");
        String contactNumber = input.nextLine();

        UserType userType = Utility.selectUserType(input);
        String idNumber = "N/A";

        if (userType != UserType.REGULAR) {
            System.out.print("Enter your ID Number (" + userType + "): ");
            idNumber = input.nextLine();
        }

        return new Passenger(name, age, address, contactNumber, userType, idNumber);
    }

    private static void bookARide(Passenger passenger) {
        Utility.clearConsole();
        System.out.println("\n--- Book a New Ride ---");

        System.out.print("Enter number of passengers: ");
        int numPassengers = Utility.getIntInput(input);

        Utility.clearConsole();
        System.out.println("\nSelect a Pickup Point:");
        LocationManager.Location pickupPoint = Utility.selectLocation(input, locationManager, null);

        Utility.clearConsole();
        System.out.println("\nSelect a Drop-off Point:");
        LocationManager.Location dropOffPoint = Utility.selectLocation(input, locationManager, pickupPoint);

        Utility.clearConsole();
        System.out.println("\nSelect a Vehicle Type:");
        VehicleType vehicleType = Utility.selectVehicleType(input);

        Utility.clearConsole();
        Utility.showLoading("\n🚗💨 Searching for a driver...\n", 3);
        Driver assignedDriver = Utility.findRandomDriver(vehicleType, driverAccounts, true);

        if (assignedDriver == null) {
            Utility.clearConsole();
            System.out.println("Sorry, no " + vehicleType + " drivers are available right now. Please try again later.");
            return;
        }

        Utility.clearConsole();
        System.out.println("Driver Found!");
        System.out.println("----------------------------------------");
        System.out.println(assignedDriver.toString());
        System.out.println("----------------------------------------");
        System.out.print("Do you want to [1] Confirm or [2] Reject this driver? ");
        int confirmChoice = Utility.getIntInput(input);

        if (confirmChoice != 1) {
            System.out.println("Booking rejected. Returning to menu.");
            return;
        }

        Booking booking = new Booking(passenger, pickupPoint, dropOffPoint, assignedDriver.getVehicle(), numPassengers);
        booking.setDriver(assignedDriver);
        booking.confirmBooking();
        allBookings.add(booking);

        System.out.println("\nBooking Confirmed!");
        System.out.printf("Total Amount to be Paid: Php %.2f\n", booking.getAmount());

        simulateTrip(booking);
    }

    private static void simulateTrip(Booking booking) {
        Utility.clearConsole();
        System.out.println("\n--- Trip in Progress ---");
        for (int progress = 0; progress <= 100; progress += 20) {
            System.out.println("Trip Progress: " + progress + "%");

            if (progress < 50) {
                System.out.print("Do you want to cancel the ride? [1] Yes [2] No: ");
                if (Utility.getIntInput(input) == 1) {
                    System.out.print("Confirm cancellation? [1] Yes [2] No: ");
                    if (Utility.getIntInput(input) == 1) {
                        booking.cancelBooking();
                        System.out.println("Booking has been cancelled.");
                        return;
                    }
                }
            }

            if (progress >= 80) {
                System.out.println("Driver Message: \"I'm on my way!\"");
            }
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
        }

        System.out.println("\nDriver has arrived!");
        
        Utility.clearConsole();
        System.out.println("\n--- Leave a Review ---");
        System.out.print("Please leave a rating for " + booking.getDriver().getName() + " (1-5 stars): ");
        int rating = 0;
        while (rating < 1 || rating > 5) {
            rating = Utility.getIntInput(input);
            if (rating < 1 || rating > 5) {
                System.out.print("Invalid input. Please enter a number between 1 and 5: ");
            }
        }

        System.out.print("Leave a comment: ");
        String comment = input.nextLine();

        Review review = new Review(booking.getPassenger(), booking.getDriver(), rating, comment);
        booking.getDriver().addReview(review);
        System.out.println("Thank you for your feedback!");
    }

    private static void viewPassengerProfile(Passenger passenger) {
        Utility.clearConsole();
        System.out.println("\n--- My Profile ---");
        System.out.println(passenger.toString());

        System.out.println("\n--- My Booking History ---");
        List<Booking> myBookings = allBookings.stream()
            .filter(b -> b.getPassenger().equals(passenger))
            .collect(Collectors.toList());

        if (myBookings.isEmpty()) {
            System.out.println("You have no booking history.");
        } else {
            myBookings.forEach(b -> System.out.println(b.toString()));
        }

        System.out.print("\nDo you want to [1] Delete Account or [2] Return to Menu? ");
        if (Utility.getIntInput(input) == 1) {
            System.out.print("Are you sure you want to delete your account permanently? [1] Yes [2] No: ");
            if (Utility.getIntInput(input) == 1) {
                passengerAccounts.remove(passenger);
                System.out.println("Account deleted successfully.");
                Utility.clearConsole();
                System.out.println("Whoa, we just had the ride of our life together!");
                System.out.println("Maybe we'll see you again soon when you have a ride with JavaRide!");
                System.out.print("Press Enter to exit the application...");
                input.nextLine(); 
                System.exit(0);  
            }
        }
    }

    // DRIVER FLOW!

    private static void runDriverFlow() {
        Driver currentDriver = registerDriver();
        if (currentDriver == null) return;

        Utility.welcomePrompt(input, currentDriver.getName());
        driverAccounts.add(currentDriver);

        boolean driverSession = true;
        while (driverSession) {
            System.out.println("\n--- Driver Menu ---");
            System.out.println("[1] View Ride Requests");
            System.out.println("[2] View My Booking History");
            System.out.println("[3] View My Earnings");
            System.out.println("[4] Logout");
            System.out.print("Select an option: ");
            int choice = Utility.getIntInput(input);

            switch (choice) {
                case 1:
                    viewRideRequests(currentDriver);
                    break;
                case 2:
                    viewDriverBookingHistory(currentDriver);
                    break;
                case 3:
                    viewDriverEarnings(currentDriver);
                    break;
                case 4:
                    driverSession = false;
                    System.out.println("\nLogging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static Driver registerDriver() {
        Utility.clearConsole();
        System.out.println("\n--- New Driver Registration ---");
    
        System.out.print("Name: ");
        String name = input.nextLine();

        System.out.print("Age: ");
        int age = Utility.getIntInput(input);

        System.out.print("Address: ");
        String address = input.nextLine();

        System.out.print("Contact Number: ");
        String contactNumber = input.nextLine();

        System.out.print("Enter a short Bio about yourself: ");
        String bio = input.nextLine();

        Utility.clearConsole();
        System.out.println("\n--- Vehicle Details ---");
        System.out.print("Enter your vehicle's Plate Number: ");
        String plateNumber = input.nextLine();
        VehicleType type = Utility.selectVehicleType(input);
        Vehicle vehicle;
        switch (type) {
            case MOTORCYCLE: vehicle = new Motorcycle(plateNumber); break;
            case NORMAL: vehicle = new Normal(plateNumber); break;
            case PREMIUM: vehicle = new Premium(plateNumber); break;
            default: vehicle = new Normal(plateNumber);
        }

        return new Driver(name, age, address, contactNumber, vehicle, bio, 3.0);
    }

    private static void viewRideRequests(Driver driver) {
        Utility.clearConsole();
        System.out.println("\n--- Pending Ride Requests ---");
        List<Booking> pendingRequests = allBookings.stream()
            .filter(b -> b.getStatus() == BookingStatus.PENDING && b.getVehicle().getVehicleType() == driver.getVehicle().getVehicleType())
            .collect(Collectors.toList());

        if (pendingRequests.isEmpty()) {
            System.out.println("No pending requests for your vehicle type.");
            return;
        }

        for (int i = 0; i < pendingRequests.size(); i++) {
            Booking b = pendingRequests.get(i);
            System.out.printf("[%d] From: %s -> To: %s | Distance: %.1f km\n",
                i + 1, b.getPickupPoint().getName(), b.getDropOffPoint().getName(), b.getDistance());
        }

        System.out.print("\nChoose a request to view details (0 to cancel): ");
        int choice = Utility.getIntInput(input);
        if (choice <= 0 || choice > pendingRequests.size()) return;

        Booking selectedBooking = pendingRequests.get(choice - 1);
        Utility.clearConsole();
        System.out.println("\n--- Request Details ---");
        System.out.println("Pickup: " + selectedBooking.getPickupPoint().getName());
        System.out.println("Drop Off: " + selectedBooking.getDropOffPoint().getName());
        System.out.println("Distance: " + selectedBooking.getDistance() + " km");
        System.out.println("Number of Passengers: " + selectedBooking.getNumberOfPassengers());

        System.out.print("\nDo you want to [1] Accept or [2] Decline this ride? ");
        if (Utility.getIntInput(input) == 1) {
            selectedBooking.setDriver(driver);
            selectedBooking.confirmBooking();
            System.out.println("Ride Accepted! You will be notified upon passenger confirmation.");
            Utility.showLoading("Waiting for passenger confirmation...", 3);
            System.out.println("\nPassenger confirmed. Trip is starting.");
        } else {
            selectedBooking.setStatus(BookingStatus.CANCELLED); 
            System.out.print("Enter a short message for declining (e.g., 'Too far'): ");
            String declineMsg = input.nextLine();
            System.out.println("Request declined. Message sent: \"" + declineMsg + "\"");
        }
    }

    private static void viewDriverBookingHistory(Driver driver) {
        Utility.clearConsole();
        System.out.println("\n--- My Booking History ---");
        List<Booking> myRides = allBookings.stream()
            .filter(b -> driver.equals(b.getDriver()))
            .collect(Collectors.toList());

        if (myRides.isEmpty()) {
            System.out.println("You have no completed rides.");
        } else {
            System.out.println("\n-- Accepted Rides --");
            myRides.stream()
                .filter(b -> b.getStatus() == BookingStatus.ACCEPTED)
                .forEach(b -> System.out.println(b.toString()));

            System.out.println("\n-- Cancelled/Declined Rides --");
            myRides.stream()
                .filter(b -> b.getStatus() == BookingStatus.CANCELLED)
                .forEach(b -> System.out.println(b.toString()));
        }
        System.out.println("\n-- Reviews --");
        List<Review> reviews = driver.getReviews();
        if (reviews.isEmpty()) {
            System.out.println("You have not received any reviews yet.");
        } else {
            for (Review review : reviews) {
                System.out.println("- " + review.toString());
            }
        }
    }

    private static void viewDriverEarnings(Driver driver) {
        Utility.clearConsole();
        System.out.println("\n--- My Earnings ---");
        double totalEarnings = allBookings.stream()
            .filter(b -> driver.equals(b.getDriver()) && b.getStatus() == BookingStatus.ACCEPTED)
            .mapToDouble(Booking::getAmount)
            .sum();

        System.out.printf("Total accumulated earnings: Php %.2f\n", totalEarnings);
        System.out.print("\nDo you want to [1] Cash Out or [2] Return to Menu? ");
        if (Utility.getIntInput(input) == 1) {
            System.out.printf("Cashing out Php %.2f. Funds will be transferred to your account within 2-3 business days.\n", totalEarnings);
        }
    }

}
