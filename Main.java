import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static final Random random = new Random();
    private static final LocationManager locationManager = new LocationManager();

    private static final List<Passenger> passengerAccounts = new ArrayList<>();
    private static final List<Driver> driverAccounts = new ArrayList<>();
    private static final List<Booking> allBookings = new ArrayList<>();

    public static void main(String[] args) {

        // Show animated logo
        UI.clearScreen();
        UI.displayGrabLogo();
        input.nextLine();

        driverAccounts.addAll(DataGenerator.generateDrivers(10));

        boolean isRunning = true;
        while (isRunning) {

            UI.clearScreen();
            System.out.println(UI.CYAN + UI.BOLD + "Your Journey Starts Here 🛤️" + UI.RESET);

            String[] menu = {
                "[1] I'm a Passenger",
                "[2] I'm a Driver",
                "[3] Exit Application"
            };
            UI.drawMenuBox(menu);

            System.out.print(UI.YELLOW + "Account Type: " + UI.RESET);
            int choice = Utility.getIntInput(input);

            switch (choice) {
                case 1:
                    UI.fadeTransition("Loading Passenger System");
                    runPassengerFlow();
                    break;
                case 2:
                    UI.fadeTransition("Loading Driver System");
                    runDriverFlow();
                    break;
                case 3:
                    isRunning = false;
                    System.out.println(UI.GREEN + "\nThank you for using JavaRide. Goodbye!" + UI.RESET);
                    break;
                default:
                    System.out.println(UI.RED + "Invalid choice. Please try again." + UI.RESET);
            }
        }

        input.close(); 
    }
    
    // -------------------------
    // PASSENGER FLOW
    // -------------------------

    private static void runPassengerFlow() {
        Passenger currentPassenger = registerPassenger();
        if (currentPassenger == null) return;

        UI.welcomePrompt(input, currentPassenger.getName());
        passengerAccounts.add(currentPassenger);

        boolean passengerSession = true;
        while (passengerSession) {

            UI.clearScreen();
            System.out.println(UI.CYAN + UI.BOLD + "--- Passenger Menu ---" + UI.RESET);

            String[] menu = {
                "[1] Book a Ride",
                "[2] View My Profile & Booking History",
                "[3] Logout"
            };
            UI.drawMenuBox(menu);

            System.out.print(UI.YELLOW + "Select an option: " + UI.RESET);
            int choice = Utility.getIntInput(input);

            switch (choice) {
                case 1:
                    UI.fadeTransition("Redirecting to Booking Page");
                    bookARide(currentPassenger);
                    break;
                case 2:
                    UI.fadeTransition("Opening Profile");
                    viewPassengerProfile(currentPassenger);
                    break;
                case 3:
                    passengerSession = false;
                    System.out.println(UI.RED + "\nLogging out..." + UI.RESET);
                    break;
                default:
                    System.out.println(UI.RED + "Invalid choice. Try again." + UI.RESET);
            }
        }
    }

    private static Passenger registerPassenger() {
        UI.clearScreen();
        System.out.println(UI.GREEN + "--- New Passenger Registration ---" + UI.RESET);

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
        UI.clearScreen();
        System.out.println(UI.CYAN + "--- Book a New Ride ---" + UI.RESET);

        System.out.print("Enter number of passengers: ");
        int numPassengers = Utility.getIntInput(input);

        System.out.println("\nSelect a Pickup Point:");
        LocationManager.Location pickupPoint = Utility.selectLocation(input, locationManager, null);

        System.out.println("\nSelect a Drop-off Point:");
        LocationManager.Location dropOffPoint = Utility.selectLocation(input, locationManager, pickupPoint);

        System.out.println("\nSelect a Vehicle Type:");
        VehicleType vehicleType = Utility.selectVehicleType(input);

        UI.fadeTransition("Searching for an available driver");

        Driver assignedDriver = Utility.findRandomDriver(vehicleType, driverAccounts, true);

        if (assignedDriver == null) {
            System.out.println(UI.RED + "No " + vehicleType + " drivers available now." + UI.RESET);
            return;
        }

        System.out.println(UI.GREEN + "Driver Found!" + UI.RESET);
        System.out.println("----------------------------------------");
        System.out.println(assignedDriver);
        System.out.println("----------------------------------------");

        System.out.print("Do you want to [1] Confirm or [2] Reject this driver? ");
        int confirmChoice = Utility.getIntInput(input);

        if (confirmChoice != 1) {
            System.out.println(UI.RED + "Booking rejected." + UI.RESET);
            return;
        }

        Booking booking = new Booking(passenger, pickupPoint, dropOffPoint, assignedDriver.getVehicle(), numPassengers);
        booking.setDriver(assignedDriver);
        booking.confirmBooking();
        allBookings.add(booking);

        System.out.printf(UI.GREEN + "\nBooking Confirmed! Fare: Php %.2f\n" + UI.RESET, booking.getAmount());

        simulateTrip(booking);
    }

    private static void simulateTrip(Booking booking) {
        UI.fadeTransition("Trip is starting");

        System.out.println(UI.CYAN + "--- Trip in Progress ---" + UI.RESET);

        for (int progress = 0; progress <= 100; progress += 20) {
            System.out.println("Trip Progress: " + progress + "%");

            if (progress < 50) {
                System.out.print("Cancel ride? [1] Yes [2] No: ");
                if (Utility.getIntInput(input) == 1) {
                    System.out.print("Confirm cancellation? [1] Yes [2] No: ");
                    if (Utility.getIntInput(input) == 1) {
                        booking.cancelBooking();
                        System.out.println(UI.RED + "Booking cancelled." + UI.RESET);
                        return;
                    }
                }
            }

            if (progress >= 80) {
                System.out.println(UI.YELLOW + "Driver: \"I'm on my way!\"" + UI.RESET);
            }

            UI.sleep(800);
        }

        System.out.println(UI.GREEN + "\nDriver has arrived!" + UI.RESET);

        System.out.println(UI.CYAN + "\n--- Leave a Review ---" + UI.RESET);
        System.out.print("Rating (1–5): ");
        int rating = Utility.getIntInput(input);

        System.out.print("Comment: ");
        String comment = input.nextLine();

        Review review = new Review(booking.getPassenger(), booking.getDriver(), rating, comment);
        booking.getDriver().addReview(review);

        System.out.println(UI.GREEN + "Thank you for your feedback!" + UI.RESET);
    }

    private static void viewPassengerProfile(Passenger passenger) {
        UI.clearScreen();
        System.out.println(UI.CYAN + "--- My Profile ---" + UI.RESET);
        System.out.println(passenger);

        System.out.println(UI.CYAN + "\n--- Booking History ---" + UI.RESET);
        List<Booking> myBookings = allBookings.stream()
            .filter(b -> b.getPassenger().equals(passenger))
            .collect(Collectors.toList());

        if (myBookings.isEmpty()) {
            System.out.println(UI.YELLOW + "No booking history." + UI.RESET);
        } else {
            myBookings.forEach(b -> System.out.println(b));
        }

        System.out.print("\n[1] Delete Account | [2] Back: ");
        if (Utility.getIntInput(input) == 1) {
            System.out.print("Delete permanently? [1] Yes [2] No: ");
            if (Utility.getIntInput(input) == 1) {
                passengerAccounts.remove(passenger);
                System.out.println(UI.RED + "Account Deleted." + UI.RESET);
                System.exit(0);
            }
        }
    }

    // -------------------------
    // DRIVER FLOW
    // -------------------------

    private static void runDriverFlow() {
        Driver currentDriver = registerDriver();
        if (currentDriver == null) return;

        UI.welcomePrompt(input, currentDriver.getName());
        driverAccounts.add(currentDriver);

        boolean driverSession = true;
        while (driverSession) {

            UI.clearScreen();
            System.out.println(UI.CYAN + UI.BOLD + "--- Driver Menu ---" + UI.RESET);

            String[] menu = {
                "[1] View Ride Requests",
                "[2] View My Booking History",
                "[3] View My Earnings",
                "[4] Logout"
            };
            UI.drawMenuBox(menu);

            System.out.print(UI.YELLOW + "Select an option: " + UI.RESET);
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
                    System.out.println(UI.RED + "Logging out..." + UI.RESET);
                    break;
                default:
                    System.out.println(UI.RED + "Invalid choice." + UI.RESET);
            }
        }
    }

    private static Driver registerDriver() {
        UI.clearScreen();
        System.out.println(UI.GREEN + "--- Driver Registration ---" + UI.RESET);

        System.out.print("Name: ");
        String name = input.nextLine();

        System.out.print("Age: ");
        int age = Utility.getIntInput(input);

        System.out.print("Address: ");
        String address = input.nextLine();

        System.out.print("Contact Number: ");
        String contactNumber = input.nextLine();

        System.out.print("Short Bio: ");
        String bio = input.nextLine();

        System.out.println("\n--- Vehicle Details ---");
        System.out.print("Plate Number: ");
        String plateNumber = input.nextLine();

        VehicleType type = Utility.selectVehicleType(input);
        Vehicle vehicle;

        switch (type) {
            case MOTORCYCLE: vehicle = new Motorcycle(plateNumber); break;
            case NORMAL: vehicle = new Normal(plateNumber); break;
            case PREMIUM: vehicle = new Premium(plateNumber); break;
            default: vehicle = new Normal(plateNumber);
        }

        return new Driver(name, age, address, contactNumber, vehicle, bio);
    }

    private static void viewRideRequests(Driver driver) {
        UI.clearScreen();
        System.out.println(UI.CYAN + "--- Pending Ride Requests ---" + UI.RESET);

        List<Booking> pendingRequests = allBookings.stream()
            .filter(b -> b.getStatus() == BookingStatus.PENDING 
                      && b.getVehicle().getVehicleType() == driver.getVehicle().getVehicleType())
            .collect(Collectors.toList());

        if (pendingRequests.isEmpty()) {
            System.out.println(UI.YELLOW + "No pending requests for your vehicle type." + UI.RESET);
            return;
        }

        for (int i = 0; i < pendingRequests.size(); i++) {
            Booking b = pendingRequests.get(i);
            System.out.printf("[%d] %s -> %s | %.1f km\n",
                i + 1, b.getPickupPoint().getName(), b.getDropOffPoint().getName(), b.getDistance());
        }

        System.out.print("\nSelect a request (0 to cancel): ");
        int choice = Utility.getIntInput(input);
        if (choice <= 0 || choice > pendingRequests.size()) return;

        Booking selectedBooking = pendingRequests.get(choice - 1);

        UI.clearScreen();
        System.out.println(UI.CYAN + "--- Request Details ---" + UI.RESET);
        System.out.println(selectedBooking);

        System.out.print("\n[1] Accept | [2] Decline: ");
        int answer = Utility.getIntInput(input);

        if (answer == 1) {
            selectedBooking.setDriver(driver);
            selectedBooking.confirmBooking();
            System.out.println(UI.GREEN + "Ride Accepted!" + UI.RESET);
            UI.fadeTransition("Waiting for passenger confirmation");
            System.out.println(UI.GREEN + "Passenger confirmed. Trip starts." + UI.RESET);
        } 
        else {
            selectedBooking.setStatus(BookingStatus.CANCELLED);
            System.out.print("Decline reason: ");
            String declineMsg = input.nextLine();
            System.out.println(UI.RED + "Declined. Message: " + declineMsg + UI.RESET);
        }
    }

    private static void viewDriverBookingHistory(Driver driver) {
        UI.clearScreen();
        System.out.println(UI.CYAN + "--- My Booking History ---" + UI.RESET);

        List<Booking> myRides = allBookings.stream()
            .filter(b -> driver.equals(b.getDriver()))
            .collect(Collectors.toList());

        if (myRides.isEmpty()) {
            System.out.println(UI.YELLOW + "You have no completed rides." + UI.RESET);
            return;
        }

        System.out.println(UI.GREEN + "\n-- Accepted Rides --" + UI.RESET);
        myRides.stream()
            .filter(b -> b.getStatus() == BookingStatus.ACCEPTED)
            .forEach(b -> System.out.println(b));

        System.out.println(UI.RED + "\n-- Cancelled / Declined Rides --" + UI.RESET);
        myRides.stream()
            .filter(b -> b.getStatus() == BookingStatus.CANCELLED)
            .forEach(b -> System.out.println(b));

        System.out.println(UI.CYAN + "\n-- Reviews --" + UI.RESET);
        List<Review> reviews = driver.getReviews();
        if (reviews.isEmpty()) {
            System.out.println(UI.YELLOW + "No reviews yet." + UI.RESET);
        } else {
            reviews.forEach(r -> System.out.println("- " + r));
        }
    }

    private static void viewDriverEarnings(Driver driver) {
        UI.clearScreen();
        System.out.println(UI.CYAN + "--- My Earnings ---" + UI.RESET);

        double totalEarnings = allBookings.stream()
            .filter(b -> driver.equals(b.getDriver()) && b.getStatus() == BookingStatus.ACCEPTED)
            .mapToDouble(Booking::getAmount)
            .sum();

        System.out.printf(UI.GREEN + "Total Earnings: Php %.2f\n" + UI.RESET, totalEarnings);

        System.out.print("\n[1] Cash Out | [2] Back: ");
        if (Utility.getIntInput(input) == 1) {
            System.out.printf(UI.GREEN + "Cashing out Php %.2f...\n" + UI.RESET, totalEarnings);
            UI.fadeTransition("Processing cash-out");
        }
    }

}
