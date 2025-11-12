import java.util.Scanner;

public class TestClass {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Ride Hailing App!");
        System.out.print("Are you registering as Passenger or Driver? ");
        String role = sc.nextLine();

        if (role.equalsIgnoreCase("Passenger")) {
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

            System.out.println("\n=== Passenger Details ===");
            passenger.displayInfo();

        } else if (role.equalsIgnoreCase("Driver")) {
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

            Driver driver = new Driver(name, age, address, contactNumber, vehicleType, bio, rating);

            System.out.println("\n=== Driver Details ===");
            driver.displayInfo();

        } else {
            System.out.println("Invalid role. Please restart and enter Passenger or Driver.");
        }

        sc.close();
    }
}
