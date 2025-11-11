import java.util.Scanner;

public class consoleGrab {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        System.out.println("===============================");
        System.out.println("               GRAB            ");
        System.out.println("===============================");
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Enter pickup location: ");
        String pickup = sc.nextLine();

        System.out.print("Enter destination: ");
        String destination = sc.nextLine();

        System.out.println("\nHi " + name + "! Choose your ride type:");
        System.out.println("[1] Car");
        System.out.println("[2] Motorcycle");
        System.out.println("[3] Premium");
        System.out.print("Enter choice: ");
        choice = sc.nextInt();

        double fare = 0;

        if (choice == 1) {
            fare = 15.0;
            System.out.println("You chose: Car");
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        System.out.println("\nSearching for available drivers...");
        try {
            Thread.sleep(1000);
            System.out.println("Driver found!");
        } catch (InterruptedException e) {
            System.out.println("Error in simulation.");
        }

        System.out.println("\nTrip Summary:");
        System.out.println("Passenger: " + name);
        System.out.println("Pickup: " + pickup);
        System.out.println("Destination: " + destination);
        System.out.println("Estimated Fare: " + fare * 5 + " Php");

        System.out.print("\nConfirm ride? (Y/N): ");
        char confirm = sc.next().charAt(0);

        if (confirm == 'Y' || confirm == 'y') {
            System.out.println("Ride started... Enjoy your trip!");
        } else {
            System.out.println("Ride cancelled.");
        }

        sc.close();
    }
}
