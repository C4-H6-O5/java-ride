import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;


// MAIN 
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

        Utility.clearConsole();
        Utility.displayGrabLogo();
        System.out.println(" ");
        System.out.print(Utility.BOLD +"Press " + Utility.BRIGHT_GREEN + "[ENTER]" + Utility.RESET + Utility.BOLD + " to continue... " + Utility.RESET);
        input.nextLine();

        boolean isRunning = true;
        while (isRunning) {

        // JAVA RIDE MAIN MENU
            Utility.clearConsole();
            Utility.displayGrabLogo();
            System.out.println(" ");
            Utility.boxTitle("JAVA RIDE MAIN MENU");

            System.out.println(Utility.BLUE + Utility.BOLD + "[1]" + Utility.RESET + " Passenger");
            System.out.println(Utility.BLUE + Utility.BOLD +  "[2]" + Utility.RESET + " Driver");
            System.out.println(Utility.RED + Utility.BOLD +"[3]" + Utility.RESET + " Exit Application");
            System.out.print(Utility.BOLD + Utility.BOLD + "\nSelect your account type: " + Utility.RESET);

            int choice = Utility.getIntInput(input);

            switch (choice) {
                case 1 -> runPassengerFlow();
                case 2 -> runDriverFlow();
                case 3 -> {
                    isRunning = false;
                    System.out.println(Utility.GREEN + "\nThank you for using JavaRide." + Utility.RESET +"Goodbye!" + Utility.RESET);
                }
                default -> {
                    System.out.println(Utility.RED + Utility.BOLD + "Invalid choice. Try again." + Utility.RESET);
                    Utility.showLoading(Utility.GRAY + "<<< Returning to Main Menu..." + Utility.RESET, 2 );
                }
            }
        }

        input.close();
    }

    // PASSENGER FLOW
    private static void runPassengerFlow() {
        Passenger currentPassenger = registerPassenger();
        if (currentPassenger == null) return;

        passengerAccounts.add(currentPassenger);
        Utility.welcomePrompt(input, currentPassenger.getName());

        boolean passengerSession = true;
        while (passengerSession) {

               // PASSENGER MENU
            Utility.clearConsole();
            Utility.displayGrabLogo();
            Utility.boxTitle("PASSENGER MENU");

            System.out.println(Utility.GREEN + Utility.BOLD + "[1]" + Utility.RESET +  " Book a Ride" );
            System.out.println(Utility.GREEN + Utility.BOLD + "[2]" + Utility.RESET + " View Profile & Booking History");
            System.out.println(Utility.RED + Utility.BOLD + "[3]" + Utility.RESET + " Logout");
            System.out.print(Utility.BOLD + "\nSelect an option: " + Utility.RESET);

            int choice = Utility.getIntInput(input);
            switch (choice) {
                case 1 -> bookARide(currentPassenger);
                case 2 -> viewPassengerProfile(currentPassenger);
                case 3 -> {
                    passengerSession = false;
                    System.out.println(Utility.YELLOW + "\nLogging out..." + Utility.RESET);
                    Utility.showLoading(Utility.GRAY + "<<< Returning to Main Menu..." + Utility.RESET, 2);
                }
                default -> {
                    System.out.println(Utility.RED + "Invalid choice. Try again." + Utility.RESET);
                    Utility.showLoading(Utility.GRAY + "<<< Returning to Passenger Menu..." + Utility.RESET, 2);
                }
            }
        }
    }

    private static Passenger registerPassenger() {

        // NEW PASSENGER REGISTRATION
        Utility.clearConsole();
        Utility.boxTitle("NEW PASSENGER REGISTRATION");

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
            System.out.print(Utility.BOLD + "Enter your ID Number (" + userType + "): " + Utility.RESET);
            idNumber = input.nextLine();
        }

        return new Passenger(name, age, address, contactNumber, userType, idNumber);
    }

    private static void bookARide(Passenger passenger) {
        
        // BOOK A NEW RIDE 
        Utility.clearConsole();
        Utility.boxTitle("BOOK A NEW RIDE");

        Utility.clearConsole();
        System.out.println(Utility.CYAN + "Select Pickup Point" + Utility.RESET);
        LocationManager.Location pickup = Utility.selectLocation(input, locationManager, null);

        Utility.clearConsole();
        System.out.println(Utility.CYAN + "Select Drop-off Point" + Utility.RESET);
        LocationManager.Location dropOff = Utility.selectLocation(input, locationManager, pickup);

        Utility.clearConsole();
        System.out.println(Utility.CYAN + "Select Vehicle Type" + Utility.RESET);
        VehicleType vehicleType = Utility.selectVehicleType(input);

        int maxPassengers;
        switch (vehicleType) {
            case MOTORCYCLE -> maxPassengers = 1;
            case NORMAL, PREMIUM -> maxPassengers = 4;
            default -> maxPassengers = 4; // Fallback
        }

        int numPassengers;
        while (true) {
            System.out.print(Utility.BOLD + "Number of passengers (" + maxPassengers + "): " + Utility.RESET);
            numPassengers = Utility.getIntInput(input);
            if (numPassengers > 0 && numPassengers <= maxPassengers) {
                break;
            }
            System.out.println(Utility.RED + "Invalid number of passengers for " + vehicleType + ". Please enter a number between 1 and " + maxPassengers + "." + Utility.RESET);
        }

        Utility.clearConsole();
        Utility.showLoading(Utility.YELLOW +">>>>> Searching for a driver..." + Utility.RESET, 3);

        List<Driver> excludedDrivers = new ArrayList<>();
        Driver assignedDriver = Utility.findRandomDriver(vehicleType, driverAccounts, excludedDrivers);

        if (assignedDriver == null) {
            Utility.clearConsole();
            System.out.println(Utility.RED + "No drivers available for " + Utility.BOLD + vehicleType + Utility.RESET + Utility.RED + ". Please try again later." + Utility.RESET);
            return;
        }

        boolean bookingComplete = false;
        int cancellationCount = 0;

        // DRIVER FOUND
        while (!bookingComplete) {
            Utility.clearConsole();
            Utility.boxTitle("DRIVER FOUND");

            System.out.println(assignedDriver);
            Vehicle vehicle = assignedDriver.getVehicle();
            double distance = Math.abs(pickup.getValue() - dropOff.getValue());
            double fare = vehicle.calculateFare(distance);
            System.out.println(" ");
            System.out.printf(Utility.BRIGHT_GREEN + "Estimated Fare: Php %.2f\n" + Utility.RESET, fare);

            System.out.println("\n[1]" + Utility.GREEN + " Confirm " + Utility.RESET + Utility.YELLOW + Utility.BOLD + " | " + Utility.RESET + " [2] " + Utility.RED +  "Reject "+ Utility.RESET);
            System.out.print(Utility.BOLD + "Enter your choice: " + Utility.RESET);
            int confirmChoice = Utility.getIntInput(input);

            if (confirmChoice != 1) {
                cancellationCount++;
                if (cancellationCount >= 3) {
                    System.out.println(Utility.RED + "\nMaximum cancellations reached.Returning to menu." + Utility.RESET);
                    Utility.showLoading(Utility.GRAY + "<<< Returning..." + Utility.RESET, 2);
                    return;
                }
                excludedDrivers.add(assignedDriver);
                Utility.clearConsole();
                System.out.println(Utility.RED + "Driver rejected. Searching for another..." + Utility.RESET);
                Utility.showLoading(Utility.YELLOW + ">>>>> Searching..." + Utility.RESET, 3);

                Driver nextDriver = Utility.findRandomDriver(vehicleType, driverAccounts, excludedDrivers);
                if (nextDriver == null) {
                    System.out.println(Utility.RED + "No other drivers available." + Utility.RESET + Utility.YELLOW + "Returning to menu." + Utility.RESET);
                    Utility.showLoading(Utility.GRAY + "<<< Returning..." + Utility.RESET, 2);
                    return;
                }
                assignedDriver = nextDriver;
                continue;
            }

            Booking booking = new Booking(passenger, pickup, dropOff, assignedDriver.getVehicle(), numPassengers);
            booking.setDriver(assignedDriver);
            booking.confirmBooking();
            allBookings.add(booking);

            System.out.println(Utility.GREEN + Utility.BOLD + "\nBooking Confirmed!" + Utility.RESET);
            System.out.printf(Utility.BRIGHT_GREEN + "Total Fare: Php %.2f\n" + Utility.RESET, booking.getAmount());

            if (simulateTrip(booking, excludedDrivers)) {
                cancellationCount++;
                excludedDrivers.add(assignedDriver);
                Utility.clearConsole();
                Utility.showLoading(Utility.YELLOW + ">>>>> Searching for another driver..." + Utility.RESET, 3);
                assignedDriver = Utility.findRandomDriver(vehicleType, driverAccounts, excludedDrivers);
                if (assignedDriver == null) {
                    System.out.println(Utility.RED + "No other drivers available. Returning to menu." + Utility.RESET);
                    Utility.showLoading(Utility.GRAY + "<<< Returning..." + Utility.RESET, 2);
                    return;
                }
            } else {
                bookingComplete = true;
            }
        }
    }

    private static boolean simulateTrip(Booking booking, List<Driver> excludedDrivers) {
        Utility.clearConsole();
        System.out.println(Utility.CYAN + ">>>>> Waiting for driver to arrive..." + Utility.RESET);
        System.out.println(Utility.RED + Utility.BOLD + "(!)" + Utility.BOLD + " Press " + Utility.GREEN + "[ENTER] " + Utility.RESET + Utility.RED + Utility.BOLD +"to cancel if below 50%\n" + Utility.RESET);

        try {
            for (int progress = 0; progress <= 100; progress++) {
                if (progress == 80){
                    System.out.print("\r                                                  \r");
                    System.out.println(Utility.YELLOW + "New Message from Driver: " + Utility.RESET + "I'm on my way!");
                }

                int barLength = 30;
                int filledLength = (progress * barLength) / 100;
                StringBuilder bar = new StringBuilder();

                for (int b = 0; b < barLength; b++) {
                    if (b < filledLength) bar.append("█");
                    else bar.append("░");
                }

                System.out.print("\r" + Utility.GREEN + "Trip Progress: " + bar + " " + progress + "% " + Utility.RESET);

                if (progress < 50) {
                    if (System.in.available() > 0) {
                        while(System.in.available() > 0) {
                            System.in.read();
                        }

                        System.out.println(Utility.RED + Utility.BOLD + "\n--- < PAUSED > ---" + Utility.RESET); 
                        System.out.println(Utility.BOLD + "Do you want to cancel the ride?" + Utility.RESET);
                        System.out.print("Enter [1]" + Utility.BRIGHT_GREEN + " Yes" + Utility.RESET + Utility.YELLOW + Utility.BOLD + " | " + Utility.RESET + "[2]" + Utility.RED + " No: "+ Utility.RESET);
                        
                        int choice = Utility.getIntInput(input);

                        if (choice == 1) {
                            System.out.println(" ");
                            System.out.print(Utility.YELLOW + "Confirm cancellation? " + Utility.RESET + "[1] " + Utility.BRIGHT_GREEN + "Yes " + Utility.RESET +  "[2] " +Utility.RED + "No: "+ Utility.RESET);
                            if (Utility.getIntInput(input) == 1) {
                                booking.cancelBooking();
                                System.out.println(Utility.RED + Utility.BOLD + "\n! Booking has been cancelled !"+ Utility.RESET);
                                System.out.print(Utility.BOLD + "Would you like to find another driver? [1]" + Utility.BRIGHT_GREEN + " Yes " + Utility.RESET + Utility.BOLD + "[2] " + Utility.RED + "No, return to Menu: " + Utility.RESET);
                                if (Utility.getIntInput(input) == 1) {
                                    return true; 
                                } else {
                                    System.out.println(Utility.GRAY + "<<< Returning to Menu..." + Utility.RESET);
                                    return false; 
                                }
                            }
                        }
                        Utility.clearConsole();
                        System.out.println(Utility.YELLOW + "Waiting for driver to arrive..." + Utility.RESET);
                        System.out.println(Utility.RED + Utility.BOLD + "(!)" + Utility.RESET + Utility.BOLD + " Press " + Utility.GREEN + "[ENTER] " + Utility.RESET + Utility.RED + Utility.BOLD +"to cancel if below 50%\n" + Utility.RESET);
                    }
                }

                Thread.sleep(100); 
            }
            
            System.out.println(Utility.CYAN + Utility.BOLD + "\n\nDriver has arrived! " + Utility.RESET);
            System.out.print("Press" + Utility.GREEN + " [ENTER] " + Utility.RESET + "to start the trip. ");
            input.nextLine();

            Utility.clearConsole();
            Utility.showLoading(Utility.YELLOW + Utility.BOLD + "=====> Traveling to destination... ======> " + Utility.RESET, 6);
        } catch (Exception e) {
            System.out.println(Utility.RED + Utility.BOLD + "Trip simulation error." + Utility.RESET);
        }

        Utility.clearConsole();
        System.out.println(Utility.GREEN + "You have arrived at your destination!" + Utility.RESET);

        System.out.print(Utility.BOLD + "\nLeave a rating for " + Utility.CYAN + booking.getDriver().getName() + Utility.RESET + Utility.YELLOW + Utility.BOLD + " (1-5): " + Utility.RESET);
        int rating = 0;
        while (rating < 1 || rating > 5) rating = Utility.getIntInput(input);

        System.out.println(" ");
        System.out.print(Utility.BOLD + "Leave a comment: " + Utility.RESET);
        String comment = input.nextLine();

        Review review = new Review(booking.getPassenger(), booking.getDriver(), rating, comment);
        booking.getDriver().addReview(review);

        System.out.println(Utility.YELLOW + "\nThank you!" + Utility.RESET); 
        System.out.print("Press" + Utility.GREEN + " [ENTER] " + Utility.RESET +  "to return to menu. ");
        input.nextLine();
        return false;
    }

    private static void viewPassengerProfile(Passenger passenger) {
        while (true) {
            Utility.clearConsole();
            Utility.boxTitle("MY PROFILE");
            System.out.println(passenger);
            System.out.println(" ");
            System.out.println(Utility.GREEN + Utility.BOLD + "============================================="+ Utility.RESET);

            System.out.println(Utility.BLUE + Utility.BOLD + "\n[1]" + Utility.RESET + " View Booking History");
            System.out.println(Utility.BLUE + Utility.BOLD + "[2]" + Utility.RESET + " Delete Account");
            System.out.println(Utility.RED + Utility.BOLD + "[3]" + Utility.RESET + " Return to Menu");
            System.out.println(" ");
            System.out.print(Utility.BOLD + "Select an option: "+ Utility.RESET);

            int choice = Utility.getIntInput(input);
            switch (choice) {
                case 1 -> {
                    Utility.clearConsole();
                    Utility.boxTitle("BOOKING HISTORY");
                    List<Booking> myBookings = allBookings.stream()
                            .filter(b -> b.getPassenger().equals(passenger))
                            .collect(Collectors.toList());
                    if (myBookings.isEmpty()) System.out.println(Utility.RED + "No booking history found." + Utility.RESET);
                    else myBookings.forEach(b -> System.out.println("\n" + b + "\n--------------------"));
                    System.out.print("Press" + Utility.GREEN + " [ENTER] " + Utility.RESET +  "to return. ");
                    input.nextLine();
                }
                case 2 -> {
                    System.out.print(Utility.YELLOW + "Are you sure? [1] " + Utility.BRIGHT_GREEN + "Yes " + Utility.RESET + Utility.YELLOW + "[2] " + Utility.RESET + Utility.RED + "No: "+ Utility.RESET);
                    if (Utility.getIntInput(input) == 1) {
                        passengerAccounts.remove(passenger);
                        System.out.println(Utility.YELLOW + "Account deleted. Exiting..." + Utility.RESET);
                        input.nextLine();
                        System.exit(0);
                    }
                }
                case 3 -> { return; }
                default -> System.out.println(Utility.RED + "Invalid choice." + Utility.RESET);
            }
        }
    }


    // DRIVER FUNCTIONS

    private static void runDriverFlow() {
        Driver currentDriver = registerDriver();
        if (currentDriver == null) return;

        driverAccounts.add(currentDriver);
        Utility.welcomePrompt(input, currentDriver.getName());

        boolean driverSession = true;
        while (driverSession) {
            Utility.clearConsole();
            Utility.displayGrabLogo();
            Utility.boxTitle("DRIVER MENU");

            System.out.println(Utility.GREEN + Utility.BOLD + "[1]" + Utility.RESET + " View Ride Requests" );
            System.out.println(Utility.GREEN + Utility.BOLD + "[2]" + Utility.RESET +  " View Booking History");
            System.out.println(Utility.GREEN + Utility.BOLD + "[3]" + Utility.RESET + " View Profile" );
            System.out.println(Utility.RED + Utility.BOLD + "[4]" + Utility.RESET + " Logout" );
            System.out.println(" ");
            System.out.print(Utility.BOLD + "\nSelect an option: " + Utility.RESET);

            int choice = Utility.getIntInput(input);
            switch (choice) {
                case 1 -> viewRideRequests(currentDriver);
                case 2 -> viewDriverBookingHistory(currentDriver);
                case 3 -> viewDriverProfile(currentDriver);
                case 4 -> {
                    driverSession = false;
                    System.out.println(Utility.YELLOW + "\n<<< Logging out..." + Utility.RESET);
                    Utility.showLoading(Utility.GRAY + "<<< Returning to Main Menu...", 2);
                }
                default -> {
                    System.out.println(Utility.RED + "Invalid choice. Try again." + Utility.RESET);
                    Utility.showLoading(Utility.GRAY + "<<< Returning..." + Utility.RESET, 2);
                }
            }
        }
    }

     private static void viewDriverProfile(Driver driver) {
        while (true) {
            Utility.clearConsole();
            System.out.println(Utility.CYAN + Utility.BOLD + "===== |  My Profile  | =====" + Utility.RESET);
            System.out.println(driver.toString());


            System.out.println(Utility.BLUE + Utility.BOLD + "======================================="+ Utility.RESET);
            System.out.println(Utility.BRIGHT_GREEN + Utility.BOLD + "\n[1]" + Utility.RESET + " View Average Rating from Passengers");
            System.out.println(Utility.GREEN + Utility.BOLD + "[2]" + Utility.RESET + " View My Earnings");
            System.out.println(Utility.RED + Utility.BOLD + "[3]" + Utility.RESET + " Return to Driver Menu");
            System.out.println(" ");
            System.out.print(Utility.BOLD + "Select an option: " + Utility.BOLD);
            int choice = Utility.getIntInput(input);

            switch (choice) {
                case 1:
                    Utility.clearConsole();
                    System.out.println(Utility.BRIGHT_GREEN + Utility.BOLD + "++++++|  Average Rating  | +++++" + Utility.RESET);
                    List<Review> reviews = driver.getReviews();
                    if (reviews.isEmpty()) {
                        System.out.println("");
                        System.out.println(Utility.BOLD + "You have not received any reviews yet. Your starting rating is shown."+ Utility.RESET);
                        System.out.println("");
                        System.out.printf(Utility.YELLOW + "Current Rating: %.1f / 5.0\n" + Utility.RESET, driver.getRating());
                    } else {
                        System.out.println(" ");
                        System.out.printf(Utility.GREEN + "Your average rating from %d review(s) is: %.1f / 5.0\n" + Utility.RESET, reviews.size(), driver.getRating());
                    }

                    System.out.println("");
                    System.out.print("Press" + Utility.GREEN + " [ENTER] " + Utility.RESET +  "to return to your profile. ");
                    input.nextLine();
                    break;
                case 2:
                    viewDriverEarnings(driver);
                    break;
                case 3:
                    return; 
                default:
                    System.out.println(Utility.RED + "Invalid choice. Please try again." + Utility.RESET);
            }
        }
    }

    private static Driver registerDriver() {
        Utility.clearConsole();
        Utility.boxTitle("NEW DRIVER REGISTRATION");

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

        Utility.clearConsole();
        Utility.boxTitle("VEHICLE DETAILS");

         System.out.print("Enter your vehicle's" + Utility.GREEN +  " Plate Number: " + Utility.RESET);
        String plateNumber;        
        while (true) {
            plateNumber = input.nextLine();
            final String finalPlateNumber = plateNumber; 
            boolean plateExists = driverAccounts.stream()
                .anyMatch(d -> d.getVehicle().getPlateNumber().equalsIgnoreCase(finalPlateNumber));

            if (plateNumber.trim().isEmpty()) {
                System.out.print(Utility.RED + "Plate number cannot be empty. Please enter a valid plate number: " + Utility.RESET);
            } else if (plateExists) {
                System.out.print(Utility.BOLD + "This plate number is already registered. Please enter a different one: " + Utility.RESET);
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
        Utility.clearConsole();
        System.out.println(Utility.YELLOW + Utility.BOLD + "~~~~~ |  Ride Requests  | ~~~~~" + Utility.RESET);

        List<Booking> pendingRequests = allBookings.stream()
            .filter(b -> b.getStatus() == BookingStatus.PENDING && b.getVehicle().getVehicleType() == driver.getVehicle().getVehicleType())
            .collect(Collectors.toList());

        for (int i = 0; i < pendingRequests.size(); i++) {
            Booking b = pendingRequests.get(i);
            System.out.printf(Utility.RED + Utility.BOLD + "[%d]" + Utility.RESET  + " From: %s -> To: %s" + Utility.YELLOW + Utility.BOLD + " | Distance: " +Utility.RESET + "%.1f km\n",
                i + 1, b.getPickupPoint().getName(), b.getDropOffPoint().getName(), b.getDistance());
        }

        System.out.print(Utility.BOLD + "\nChoose a request to view details" + Utility.RED + " (0 to cancel): " + Utility.RESET);
        int reqChoice = Utility.getIntInput(input);
        if (reqChoice <= 0 || reqChoice > pendingRequests.size()) return;

        Booking selectedBooking = pendingRequests.get(reqChoice - 1);
        Utility.clearConsole();
        System.out.println("~~~~~ |  Request Details  | ~~~~~");
        System.out.println(Utility.BRIGHT_GREEN + "Pickup: " + Utility.RESET + selectedBooking.getPickupPoint().getName());
        System.out.println(Utility.RED + "Drop Off: " + Utility.RESET + selectedBooking.getDropOffPoint().getName());
        System.out.println(Utility.GREEN + "Distance: " + Utility.RESET + selectedBooking.getDistance() + " km");
        System.out.println(Utility.BOLD + "Number of Passengers: " + Utility.RESET + selectedBooking.getNumberOfPassengers());
        System.out.println("");
        System.out.printf(Utility.YELLOW + Utility.BOLD + "Estimated Fare: Php %.2f\n" + Utility.RESET, driver.getVehicle().calculateFare(selectedBooking.getDistance()));

        System.out.print(Utility.BOLD + "\nDo you want to [1]" + Utility.BRIGHT_GREEN + " Accept or " + Utility.RESET + Utility.BOLD + "[2] " + Utility.RED + "Decline this ride? " + Utility.RESET);
        if (Utility.getIntInput(input) == 1) {
            selectedBooking.setDriver(driver);
            selectedBooking.setVehicle(driver.getVehicle());
            selectedBooking.confirmBooking();
            System.out.print("\nRide Accepted! Click" +  Utility.GREEN +" 'Enter' " + Utility.RESET + "to proceed to the pickup location.");
            input.nextLine();
            Utility.clearConsole();
            simulateDriverTrip(selectedBooking);
        } else {
            selectedBooking.setDriver(driver); 
            selectedBooking.setStatus(BookingStatus.CANCELLED);

            System.out.println("");
            System.out.print(Utility.BOLD + "Enter a short message for declining (e.g., 'Too far'): " + Utility.RESET);
            String declineMsg = input.nextLine();

            System.out.println("");
            System.out.println(Utility.BLUE + "Request declined. Message sent: " + Utility.RESET + "\"" + declineMsg + "\"");
            System.out.println(" ");
            System.out.print("Press" + Utility.GREEN + " [ENTER] " + Utility.RESET +  "to return to menu. ");
            input.nextLine();
        }
    }

    private static void simulateDriverTrip(Booking booking) {
        Utility.showLoading("Driving to pickup location: " + booking.getPickupPoint().getName() + "\n", 4);
        
        Utility.clearConsole();
        System.out.println(Utility.GREEN + Utility.BOLD + "You have arrived at the pickup location!" + Utility.RESET);
        System.out.println(Utility.BOLD +"Passenger: "+ Utility.RESET + booking.getPassenger().getName());
        System.out.print("\nPress" + Utility.BRIGHT_GREEN + " 'Enter' "+ Utility.RESET + "to start the trip to " + booking.getDropOffPoint().getName() + ". ");
        input.nextLine();

        Utility.clearConsole();
        Utility.showLoading(Utility.YELLOW + Utility.BOLD + ">>>>> Trip to destination in progress...\n" + Utility.RESET, 6);
        System.out.println(Utility.BOLD + "You have arrived at the destination. The ride is complete!" + Utility.RESET);

        allBookings.removeIf(b -> b.getStatus() == BookingStatus.PENDING);
        allBookings.addAll(DataGenerator.generateBookings(5, locationManager)); 

        if (random.nextBoolean()) {
            int rating = 3 + random.nextInt(3); 
            String comment = DataGenerator.getRandomReviewComment();
            Review review = new Review(booking.getPassenger(), booking.getDriver(), rating, comment);
            booking.getDriver().addReview(review);
            System.out.println(Utility.BOLD + Utility.BLUE + "\n -- ! New Review Received! Check your reviews in the booking history menu ! --"+ Utility.RESET);
        }

        System.out.println("");
        System.out.print("Press" + Utility.GREEN + " [ENTER] " + Utility.RESET +  "to return to Menu. ");
        input.nextLine();
    }

    private static void viewDriverBookingHistory(Driver driver) {
        while (true) {
            Utility.clearConsole();
            System.out.println(Utility.GRAY + "==<<<<<< My Booking History >>>>>=="+ Utility.RESET);
            System.out.println("");
            System.out.println(Utility.GREEN + Utility.BOLD + "[1]" + Utility.RESET + " View Accepted Rides");
            System.out.println(Utility.GREEN + Utility.BOLD + "[2]" + Utility.RESET + " View Cancelled/Declined Rides");
            System.out.println(Utility.GREEN + Utility.BOLD + "[3]" + Utility.RESET + "  View My Reviews");
            System.out.println(Utility.RED + Utility.BOLD + "[4]" + Utility.RESET + " Return to Driver Menu");
            System.out.println("");
            System.out.print(Utility.BOLD + "Select an option: " + Utility.RESET);
            int choice = Utility.getIntInput(input);

            List<Booking> myRides = allBookings.stream().filter(b -> driver.equals(b.getDriver())).collect(Collectors.toList());

            switch (choice) {
                case 1:
                    Utility.clearConsole();
                    System.out.println(Utility.GREEN + Utility.BOLD + "+++++ |  Accepted Rides  | +++++" + Utility.RESET);
                    List<Booking> acceptedRides = myRides.stream().filter(b -> b.getStatus() == BookingStatus.ACCEPTED).collect(Collectors.toList());
                    if (acceptedRides.isEmpty()) System.out.println(Utility.RED +"No accepted rides found." + Utility.RESET);
                    else acceptedRides.forEach(b -> System.out.println("\n===================================\n" + b.toDriverString() + "\n==================================="));
                    break;
                case 2:
                    Utility.clearConsole();
                    System.out.println(Utility.RED + Utility.BOLD + "----- |  Cancelled/Declined Rides  | -----" + Utility.RESET);
                    List<Booking> cancelledRides = myRides.stream().filter(b -> b.getStatus() == BookingStatus.CANCELLED).collect(Collectors.toList());
                    if (cancelledRides.isEmpty()) System.out.println("No cancelled or declined rides found.");
                    else cancelledRides.forEach(b -> System.out.println("\n===================================\n" + b.toDriverString() + "\n==================================="));
                    break;
                case 3:
                    Utility.clearConsole();
                    System.out.println(Utility.BLUE + Utility.BOLD + "<<<<< |  My Reviews  | >>>>>" + Utility.RESET);
                    List<Review> reviews = driver.getReviews();
                    if (reviews.isEmpty()) System.out.println(Utility.BOLD + "You have not received any reviews yet." + Utility.RESET);
                    else reviews.forEach(r -> System.out.println("- " + r.toString()));
                    break;
                case 4:
                    return; 
                default:
                    System.out.println(Utility.RED + "Invalid choice. Please try again." + Utility.RESET);
                    Utility.showLoading(Utility.GRAY + "<<< Returning..." + Utility.RESET, 1);
                    continue;
            }

            System.out.println("");
            System.out.print("Press" + Utility.GREEN + " [ENTER] " + Utility.RESET +  "to return to the history menu. ");
            input.nextLine();
        }
    }

    private static void viewDriverEarnings(Driver driver) {
        Utility.clearConsole();
        System.out.println(Utility.YELLOW + Utility.BOLD + "+++++ |  $ My Earnings $ | +++++" + Utility.RESET);
        double totalEarnings = allBookings.stream()
            .filter(b -> driver.equals(b.getDriver()) && b.getStatus() == BookingStatus.ACCEPTED && !b.isPaidOut())
            .mapToDouble(Booking::getAmount)
            .sum();

            System.out.println(" ");
            System.out.printf(Utility.BOLD + "Available earnings to cash out: " + Utility.GREEN + "Php %.2f\n" + Utility.RESET, totalEarnings);

        if (totalEarnings > 0) {
            System.out.println(" ");
            System.out.print(Utility.BOLD + "Do you want to [1]" + Utility.BRIGHT_GREEN + " Cash Out " + Utility.RESET + Utility.BOLD + " or [2] " + Utility.RED + "Return to Menu: " + Utility.RESET);
            if (Utility.getIntInput(input) == 1) {
                allBookings.stream()
                    .filter(b -> driver.equals(b.getDriver()) && b.getStatus() == BookingStatus.ACCEPTED && !b.isPaidOut())
                    .forEach(b -> b.setPaidOut(true));

                System.out.printf(Utility.BOLD + "\nCashing out Php %.2f. Funds will be transferred to your account within 2-3 business days.\n" + Utility.RESET, totalEarnings);
            }
            else {
                return;
            }
        } else {
            System.out.println(Utility.RED + "\nYou have no available earnings to cash out." + Utility.RESET);
            System.out.println("");
            System.out.print("Press" + Utility.GREEN + " [ENTER] " + Utility.RESET +  "to return to menu. ");
        }

        input.nextLine();
    }

}
