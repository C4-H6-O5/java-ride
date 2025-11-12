import java.util.Scanner;

public class Design {
    // ANSI Colors
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String CYAN = "\u001B[36m";
    public static final String YELLOW = "\u001B[33m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        // Title
        System.out.println(YELLOW + "=======================================" + RESET);
        System.out.println(YELLOW + "               🚗 GRAB APP             " + RESET);
        System.out.println(YELLOW + "=======================================" + RESET);

        // User Input
        System.out.print(CYAN + "Enter your name: " + RESET);
        String name = scanner.nextLine();

        System.out.print(CYAN + "Enter pickup location: " + RESET);
        String pickup = scanner.nextLine();

        System.out.print(CYAN + "Enter destination: " + RESET);
        String destination = scanner.nextLine();

        // Ride Type Menu
        System.out.println("\n" + GREEN + "Hi " + name + "! Choose your ride type:" + RESET);
        System.out.println(GREEN + "[1] Car");
        System.out.println("[2] Motorcycle");
        System.out.println("[3] Premium" + RESET);
        System.out.print(CYAN + "Enter choice: " + RESET);
        choice = scanner.nextInt();

        double fare = 0;
        String rideType = " ";

        switch (choice) {
            case 1 -> {
                fare = 15.0;
                rideType = "Car";
            }
            case 2 -> {
                fare = 10.0;
                rideType = "Motorcycle";
            }
            case 3 -> {
                fare = 25.0;
                rideType = "Premium";
            }
            default -> {
                System.out.println(RED + "Invalid choice. Exiting..." + RESET);
                scanner.close();
                return;
            }
        }

        System.out.println(GREEN + "\nYou chose: " + rideType + RESET);

        // Simulate driver search with animation
        System.out.print(YELLOW + "\nSearching for available drivers" + RESET);
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(500);
                System.out.print(".");
            } catch (InterruptedException e) {
                System.out.println(RED + "\nError in simulation." + RESET);
            }
        }
        System.out.println(GREEN + "\nDriver found!" + RESET);

        // Trip Summary
        System.out.println(YELLOW + "\n================ TRIP SUMMARY ================" + RESET);
        System.out.println("Passenger    : " + name);
        System.out.println("Pickup       : " + pickup);
        System.out.println("Destination  : " + destination);
        System.out.println("Ride Type    : " + rideType);
        System.out.println("Estimated Fare: " + fare * 5 + " Php"); // Example multiplier
        System.out.println(YELLOW + "=============================================" + RESET);

        // Confirmation
        System.out.print(CYAN + "\nConfirm ride? (Y/N): " + RESET);
        char confirm = scanner.next().charAt(0);

        if (confirm == 'Y' || confirm == 'y') {
            System.out.println(GREEN + "\nRide started... Enjoy your trip! 🚗" + RESET);
        } else {
            System.out.println(RED + "\nRide cancelled." + RESET);
        }

        scanner.close();
    }
}
