import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static final LocationManager locationManager = new LocationManager();
    private static final Random random = new Random();

    private static final List<Passenger> passengerAccounts = new ArrayList<>();
    private static final List<Driver> driverAccounts = new ArrayList<>();
    private static final List<Booking> allBookings = new ArrayList<>();

    public static void main(String[] args) {
        driverAccounts.addAll(DataGenerator.generateDrivers(10));
        allBookings.addAll(DataGenerator.generateBookings(5, locationManager));

        System.out.println("Hello! Welcome to the JavaRide app 🛺");
        System.out.print("New to JavaRide? Press 'Enter' to Sign Up! ");
        input.nextLine();

        boolean isRunning = true;
        while (isRunning) {
            Utility.clearConsole();
            System.out.println("Your Journey Starts Here 🛤️");
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
            System.out.println("--- Passenger Menu ---");
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
        System.out.println("--- New Passenger Registration ---");
    
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
        System.out.println("--- Book a New Ride ---");

        System.out.print("Enter number of passengers: ");
        int numPassengers = Utility.getIntInput(input);

        Utility.clearConsole();
        System.out.println("Select a Pickup Point:");
        LocationManager.Location pickupPoint = Utility.selectLocation(input, locationManager, null);

        Utility.clearConsole();
        System.out.println("Select a Drop-off Point:");
        LocationManager.Location dropOffPoint = Utility.selectLocation(input, locationManager, pickupPoint);

        Utility.clearConsole();
        System.out.println("Select a Vehicle Type:");
        VehicleType vehicleType = Utility.selectVehicleType(input);

        Utility.clearConsole();
        Utility.showLoading("🚗💨 Searching for a driver...\n", 3);
        
        List<Driver> excludedDrivers = new ArrayList<>();
        Driver assignedDriver = Utility.findRandomDriver(vehicleType, driverAccounts, excludedDrivers);

        if (assignedDriver == null) {
            Utility.clearConsole();
            System.out.println("Sorry, no " + vehicleType + " drivers are available right now. Please try again later.");
            return;
        }
        
        boolean bookingSuccessful = false;
        int cancellationCount = 0;
        while (!bookingSuccessful) {
            Utility.clearConsole();
            System.out.println("Driver Found!");
            System.out.println("----------------------------------------");
            System.out.println(assignedDriver.toString());
            
            Vehicle vehicle = assignedDriver.getVehicle();
            double distance = Math.abs(pickupPoint.getValue() - dropOffPoint.getValue());
            double fare = vehicle.calculateFare(distance);
            System.out.printf("Estimated Fare: Php %.2f\n", fare);

            System.out.println("----------------------------------------");
            System.out.print("Do you want to [1] Confirm or [2] Reject this driver? ");
            int confirmChoice = Utility.getIntInput(input);

            if (confirmChoice != 1) {
                cancellationCount++;
                if (cancellationCount >= 3) {
                    Utility.clearConsole();
                    System.out.println("You have reached the maximum number of cancellations for this booking.");
                    System.out.println("Please start a new booking if you wish to find a ride.");
                    System.out.print("Press 'Enter' to return to the Menu. ");
                    input.nextLine();
                    return;
                }
                excludedDrivers.add(assignedDriver);
                Utility.clearConsole();
                System.out.println("Driver rejected. Finding another one for you.");
                Utility.showLoading("🚗💨 Searching for another driver...\n", 3);                
                Driver nextDriver = Utility.findRandomDriver(vehicleType, driverAccounts, excludedDrivers);
                if (nextDriver == null) {
                    System.out.println("Sorry, no other drivers are available right now. Please try again later.");
                    System.out.print("Press 'Enter' to return to the Menu. ");
                    input.nextLine();
                    return;
                }
                assignedDriver = nextDriver;
                continue; 
            }
    
            Booking booking = new Booking(passenger, pickupPoint, dropOffPoint, assignedDriver.getVehicle(), numPassengers);
            booking.setDriver(assignedDriver);
            booking.confirmBooking();
            allBookings.add(booking);
    
            System.out.println("\nBooking Confirmed!");
            System.out.printf("Total Amount to be Paid: Php %.2f\n", booking.getAmount());
    
            if (simulateTrip(booking, excludedDrivers)) {
                cancellationCount++;
                if (cancellationCount >= 3) {
                    Utility.clearConsole();
                    System.out.println("You have reached the maximum number of cancellations for this booking.");
                    System.out.println("Please start a new booking if you wish to find a ride.");
                    System.out.print("\nPress 'Enter' to return to the Menu. ");
                    input.nextLine();
                    return;
                }
                excludedDrivers.add(assignedDriver);
                Utility.clearConsole();
                Utility.showLoading("🚗💨 Searching for another driver...\n", 3);
                assignedDriver = Utility.findRandomDriver(vehicleType, driverAccounts, excludedDrivers);
                 if (assignedDriver == null) {
                    Utility.clearConsole();
                    System.out.println("Sorry, no other " + vehicleType + " drivers are available right now. Please try again later.");
                    System.out.print("Press 'Enter' to return to the Menu. ");
                    input.nextLine();
                    break; 
                }
                continue;
            } else {
                bookingSuccessful = true; 
                return; 
            }
        }
    }

    private static boolean simulateTrip(Booking booking, List<Driver> excludedDrivers) {
        Utility.clearConsole();
        System.out.println("Waiting for driver to arrive...");
        System.out.println("(!) Press [ENTER] to cancel (valid below 50%)\n");

        String GREEN = "\u001B[32m";
        String RESET = "\u001B[0m";

        try {
            for (int progress = 0; progress <= 100; progress++) {
                if (progress == 80) {
                    System.out.print("\r                                                  \r");
                    System.out.println("[!] New Message from Driver: \"I'm on my way!\"\n");
                }

                int barLength = 30;
                int filledLength = (progress * barLength) / 100;
                StringBuilder bar = new StringBuilder();
                for (int b = 0; b < barLength; b++) {
                    if (b < filledLength) bar.append("█");
                    else bar.append("░");
                }
                
                System.out.print("\r" + GREEN + "Trip Progress: " + bar + " " + progress + "% " + RESET);

                if (progress < 50) {
                    if (System.in.available() > 0) {
                        while(System.in.available() > 0) {
                            System.in.read();
                        }

                        System.out.println("\n--- PAUSED ---"); 
                        System.out.print("Do you want to cancel the ride? [1] Yes [2] No: ");
                        
                        int choice = Utility.getIntInput(input);

                        if (choice == 1) {
                            System.out.print("Confirm cancellation? [1] Yes [2] No: ");
                            if (Utility.getIntInput(input) == 1) {
                                booking.cancelBooking();
                                System.out.println("\n[!] Booking has been cancelled.");
                                System.out.print("Would you like to find another driver? [1] Yes [2] No, return to Menu: ");
                                if (Utility.getIntInput(input) == 1) {
                                    return true; 
                                } else {
                                    System.out.println("Returning to Menu...");
                                    return false; 
                                }
                            }
                        }
                        Utility.clearConsole();
                        System.out.println("Waiting for driver to arrive...");
                        System.out.println("(!) Press [ENTER] to cancel (valid below 50%)\n");
                    }
                }

                Thread.sleep(100); 
            }
            
            System.out.print("\n\nDriver has arrived! Click 'Enter' to hop in the vehicle and start the trip. ");
            // add graphics here
            input.nextLine();

            Utility.clearConsole();
            Utility.showLoading("Traveling to desination...\n", 6);
        } catch (Exception e) {
            System.out.println("Error in trip simulation.");
        }
        
        Utility.clearConsole();
        System.out.println("You have arrived at your destination. Thank you for having a ride with JavaRide!");
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
        System.out.print("\nThank you for your feedback! Press 'Enter' to return to Menu. ");
        input.nextLine();
        return false; 
    }

    private static void viewPassengerProfile(Passenger passenger) {
        while (true) {
            Utility.clearConsole();
            System.out.println("--- My Profile ---");
            System.out.println(passenger.toString());
    
            System.out.println("\n[1] View Booking History");
            System.out.println("[2] Delete Account");
            System.out.println("[3] Return to Menu");
            System.out.print("Select an option: ");
            int choice = Utility.getIntInput(input);
    
            switch (choice) {
                case 1:
                    Utility.clearConsole();
                    System.out.println("-------- My Booking History --------");
                    List<Booking> myBookings = allBookings.stream()
                        .filter(b -> b.getPassenger().equals(passenger))
                        .collect(Collectors.toList());
    
                    if (myBookings.isEmpty()) {
                        System.out.println("You have no booking history.");
                    } else {
                        for (int i = 0; i < myBookings.size(); i++) {
                            System.out.printf("\n======= Booking History %03d =======\n", i + 1);
                            System.out.println(myBookings.get(i).toString());
                            System.out.print("===================================\n");
                        }
                    }
                    System.out.print("\nPress 'Enter' to return to your profile. ");
                    input.nextLine();
                    break; 
                case 2:
                    System.out.print("Are you sure you want to delete your account permanently? [1] Yes [2] No: ");
                    if (Utility.getIntInput(input) == 1) {
                        passengerAccounts.remove(passenger);
                        System.out.println("Account deleted successfully.");
                        Utility.clearConsole();
                        System.out.println("Whoa, we just had the ride of our life together!");
                        System.out.println("Maybe we'll see you again soon when you have a ride with JavaRide!");
                        System.out.print("Press 'Enter' to exit the application. ");
                        input.nextLine(); 
                        System.exit(0);  
                    }
                    break; 
                case 3:
                    return; 
                default:
                    System.out.println("Invalid choice. Please try again.");
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
            Utility.clearConsole();
            System.out.println("--- Driver Menu ---");
            System.out.println("[1] View Ride Requests");
            System.out.println("[2] View My Booking History");
            System.out.println("[3] View My Profile");
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
                    viewDriverProfile(currentDriver);
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

    private static void viewDriverProfile(Driver driver) {
        while (true) {
            Utility.clearConsole();
            System.out.println("--- My Profile ---");
            System.out.println(driver.toString());

            System.out.println("\n[1] View Average Rating from Passengers");
            System.out.println("[2] View My Earnings");
            System.out.println("[3] Return to Driver Menu");
            System.out.print("Select an option: ");
            int choice = Utility.getIntInput(input);

            switch (choice) {
                case 1:
                    Utility.clearConsole();
                    System.out.println("--- Average Rating ---");
                    List<Review> reviews = driver.getReviews();
                    if (reviews.isEmpty()) {
                        System.out.println("You have not received any reviews yet. Your starting rating is shown.");
                        System.out.printf("Current Rating: %.1f / 5.0\n", driver.getRating());
                    } else {
                        System.out.printf("Your average rating from %d review(s) is: %.1f / 5.0\n", reviews.size(), driver.getRating());
                    }
                    System.out.print("\nPress 'Enter' to return to your profile. ");
                    input.nextLine();
                    break;
                case 2:
                    viewDriverEarnings(driver);
                    break;
                case 3:
                    return; 
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static Driver registerDriver() {
        Utility.clearConsole();
        System.out.println("--- New Driver Registration ---");
    
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
        System.out.println("--- Vehicle Details ---");
        System.out.print("Enter your vehicle's Plate Number: ");
        String plateNumber;        
        while (true) {
            plateNumber = input.nextLine();
            final String finalPlateNumber = plateNumber; 
            boolean plateExists = driverAccounts.stream()
                .anyMatch(d -> d.getVehicle().getPlateNumber().equalsIgnoreCase(finalPlateNumber));

            if (plateNumber.trim().isEmpty()) {
                System.out.print("Plate number cannot be empty. Please enter a valid plate number: ");
            } else if (plateExists) {
                System.out.print("This plate number is already registered. Please enter a different one: ");
            } else {
                break;
            }
        }
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
        while (true) {
            Utility.clearConsole();
            System.out.println("--- Ride Requests ---");

            long pendingCount = allBookings.stream()
                .filter(b -> b.getStatus() == BookingStatus.PENDING && b.getVehicle().getVehicleType() == driver.getVehicle().getVehicleType())
                .count();

            if (pendingCount == 0) {
                System.out.println("No pending ride requests available for your vehicle type.");
                System.out.print("Would you like to [1] Search Again or [2] Return to Menu? ");
                if (Utility.getIntInput(input) == 1) {
                    allBookings.addAll(DataGenerator.generateBookings(random.nextInt(4) + 3, locationManager));
                    Utility.showLoading("Searching for new ride requests...", 2);
                } else {
                    return;
                }
            } else {
                selectAndProcessRideRequest(driver);
                return;
            }
        }
    }

    private static void selectAndProcessRideRequest(Driver driver) {
        Utility.clearConsole();
        System.out.println("--- Pending Ride Requests ---");

        List<Booking> pendingRequests = allBookings.stream()
            .filter(b -> b.getStatus() == BookingStatus.PENDING && b.getVehicle().getVehicleType() == driver.getVehicle().getVehicleType())
            .collect(Collectors.toList());

        for (int i = 0; i < pendingRequests.size(); i++) {
            Booking b = pendingRequests.get(i);
            System.out.printf("[%d] From: %s -> To: %s | Distance: %.1f km\n",
                i + 1, b.getPickupPoint().getName(), b.getDropOffPoint().getName(), b.getDistance());
        }

        System.out.print("\nChoose a request to view details (0 to cancel): ");
        int reqChoice = Utility.getIntInput(input);
        if (reqChoice <= 0 || reqChoice > pendingRequests.size()) return;

        Booking selectedBooking = pendingRequests.get(reqChoice - 1);
        Utility.clearConsole();
        System.out.println("--- Request Details ---");
        System.out.println("Pickup: " + selectedBooking.getPickupPoint().getName());
        System.out.println("Drop Off: " + selectedBooking.getDropOffPoint().getName());
        System.out.println("Distance: " + selectedBooking.getDistance() + " km");
        System.out.println("Number of Passengers: " + selectedBooking.getNumberOfPassengers());
        System.out.printf("Estimated Fare: Php %.2f\n", driver.getVehicle().calculateFare(selectedBooking.getDistance()));

        System.out.print("\nDo you want to [1] Accept or [2] Decline this ride? ");
        if (Utility.getIntInput(input) == 1) {
            selectedBooking.setDriver(driver);
            selectedBooking.setVehicle(driver.getVehicle());
            selectedBooking.confirmBooking();
            System.out.println("\nRide Accepted! Please proceed to the pickup location.");
            simulateDriverTrip(selectedBooking);
        } else {
            selectedBooking.setDriver(driver); 
            selectedBooking.setStatus(BookingStatus.CANCELLED);
            System.out.print("Enter a short message for declining (e.g., 'Too far'): ");
            String declineMsg = input.nextLine();
            System.out.println("Request declined. Message sent: \"" + declineMsg + "\"");
            System.out.print("Press 'Enter' to return to the menu. ");
            input.nextLine();
        }
    }

    private static void simulateDriverTrip(Booking booking) {
        Utility.showLoading("Driving to pickup location: " + booking.getPickupPoint().getName(), 4);
        
        Utility.clearConsole();
        System.out.println("You have arrived at the pickup location.");
        System.out.println("Passenger: " + booking.getPassenger().getName());
        System.out.print("Press 'Enter' to start the trip to " + booking.getDropOffPoint().getName() + " ");
        input.nextLine();

        Utility.showLoading("Trip to destination in progress...", 6);
        System.out.println("You have arrived at the destination. The ride is complete!");

        allBookings.removeIf(b -> b.getStatus() == BookingStatus.PENDING);
        allBookings.addAll(DataGenerator.generateBookings(5, locationManager)); 

        if (random.nextBoolean()) {
            int rating = 3 + random.nextInt(3); 
            String comment = DataGenerator.getRandomReviewComment();
            Review review = new Review(booking.getPassenger(), booking.getDriver(), rating, comment);
            booking.getDriver().addReview(review);
            System.out.println("\n[!] New Review Received! Check your reviews in the booking history menu.");
        }

        System.out.print("Press 'Enter' to return to Menu. ");
        input.nextLine();
    }

    private static void viewDriverBookingHistory(Driver driver) {
        while (true) {
            Utility.clearConsole();
            System.out.println("--- My Booking History ---");
            System.out.println("[1] View Accepted Rides");
            System.out.println("[2] View Cancelled/Declined Rides");
            System.out.println("[3] View My Reviews");
            System.out.println("[4] Return to Driver Menu");
            System.out.print("Select an option: ");
            int choice = Utility.getIntInput(input);

            List<Booking> myRides = allBookings.stream().filter(b -> driver.equals(b.getDriver())).collect(Collectors.toList());

            switch (choice) {
                case 1:
                    Utility.clearConsole();
                    System.out.println("--- Accepted Rides ---");
                    List<Booking> acceptedRides = myRides.stream().filter(b -> b.getStatus() == BookingStatus.ACCEPTED).collect(Collectors.toList());
                    if (acceptedRides.isEmpty()) System.out.println("No accepted rides found.");
                    else acceptedRides.forEach(b -> System.out.println("\n===================================\n" + b.toDriverString() + "\n==================================="));
                    break;
                case 2:
                    Utility.clearConsole();
                    System.out.println("--- Cancelled/Declined Rides ---");
                    List<Booking> cancelledRides = myRides.stream().filter(b -> b.getStatus() == BookingStatus.CANCELLED).collect(Collectors.toList());
                    if (cancelledRides.isEmpty()) System.out.println("No cancelled or declined rides found.");
                    else cancelledRides.forEach(b -> System.out.println("\n===================================\n" + b.toDriverString() + "\n==================================="));
                    break;
                case 3:
                    Utility.clearConsole();
                    System.out.println("--- My Reviews ---");
                    List<Review> reviews = driver.getReviews();
                    if (reviews.isEmpty()) System.out.println("You have not received any reviews yet.");
                    else reviews.forEach(r -> System.out.println("- " + r.toString()));
                    break;
                case 4:
                    return; 
                default:
                    System.out.println("Invalid choice. Please try again.");
                    Utility.showLoading("Returning...", 1);
                    continue;
            }

            System.out.print("\nPress 'Enter' to return to the history menu. ");
            input.nextLine();
        }
    }

    private static void viewDriverEarnings(Driver driver) {
        Utility.clearConsole();
        System.out.println("--- My Earnings ---");
        double totalEarnings = allBookings.stream()
            .filter(b -> driver.equals(b.getDriver()) && b.getStatus() == BookingStatus.ACCEPTED && !b.isPaidOut())
            .mapToDouble(Booking::getAmount)
            .sum();

        System.out.printf("Available earnings to cash out: Php %.2f\n", totalEarnings);

        if (totalEarnings > 0) {
            System.out.print("\nDo you want to [1] Cash Out or [2] Return to Menu? ");
            if (Utility.getIntInput(input) == 1) {
                allBookings.stream()
                    .filter(b -> driver.equals(b.getDriver()) && b.getStatus() == BookingStatus.ACCEPTED && !b.isPaidOut())
                    .forEach(b -> b.setPaidOut(true));

                System.out.printf("\nCashing out Php %.2f. Funds will be transferred to your account within 2-3 business days.\n", totalEarnings);
            }
        } else {
            System.out.println("\nYou have no available earnings to cash out.");
        }

        System.out.print("Press 'Enter' to return to the menu. ");
        input.nextLine();
    }

}
