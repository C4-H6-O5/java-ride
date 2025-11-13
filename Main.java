import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Hello! Welcome to Grab 🛺");
        System.out.print("New to Grab? Press 'Enter' to Sign Up! ");
        input.nextLine();

        System.out.println("\nTagline Here"); // edit this, something like "Your Journey Starts Here"

        System.out.print("Name: ");
        String name = input.nextLine();

        System.out.print("Age: ");
        int age = input.nextInt();
        input.nextLine();

        System.out.print("Address: ");
        String address = input.nextLine();

        System.out.print("Contact Number: ");
        int contactNumber = input.nextInt();
        input.nextLine();
        
        System.out.println("\nPlease select your Account Type:");
        System.out.println("1. Passenger");
        System.out.println("2. Driver\n");
        System.out.print("Account Type: ");
        int accountType = input.nextInt();
        input.nextLine();

        String category = "";

        if (accountType == 1) {
            System.out.println("\nWelcome to the Ride, Passenger!");
            System.out.println("Final checks: Are you eligible for any special discounts?");
            System.out.println("1. No, I'm a Regular Passenger");
            System.out.println("2. Yes, I'm a Student");
            System.out.println("3. Yes, I'm a Person with Disability (PWD)");
            System.out.println("4. Yes, I'm a Senior Citizen");
            System.out.print("Enter your choice: ");
            int passengerType = input.nextInt();
            input.nextLine();

            switch (passengerType) {
                case 1:
                    category = "Regular";
                    break;
                case 2:
                    category = "Student";
                    break;
                case 3:
                    category = "PWD";
                    break;
                case 4:
                    category = "Senior Citizen";
                    break;
                default:
                    category = "Regular";
                    break;
            }

            if (category != "Regular") {
                System.out.print("\nEnter your Valid ID Number: ");
                String idNumber = input.nextLine();
                // Insert verifying loading bar here
                System.out.println("\n✅ Your " + category + " Valid ID is now verified!");
            } 

            welcomePrompt(input, name);

            Passenger passenger = new Passenger(name, age, address, contactNumber, passengerType, idNumber);
            passenger.homePage(input);
        } else if (accountType == 2) {
            System.out.println("\nHop in, Driver!");
            System.out.println("Final checks: What service will you provide to passengers?");
            System.out.println("1. Standard");
            System.out.println("2. Premium");
            System.out.println("3. Motorcycle");
            System.out.print("Enter your choice: ");
            int serviceType = input.nextInt();
            input.nextLine();

            System.out.println("Let your passengers get to know you better!");
            System.out.println("Set up your Bio (You can always update this later)");
            System.out.print("Bio: ");
            String bio = input.nextLine();

            welcomePrompt(input, name);

            Driver driver = new Driver(name, age, address, contactNumber, serviceType, bio);
            driver.homePage(input);
        }

        input.close();
    } 
}

/*  welcomePrompt(Scanner input, String name) {
    // Loading bar here for transition
    System.out.println("\nAll set! Let's hit the road, " + name + "!");
    System.out.print("Press 'Enter' to go to the Home Page! ");
    input.nextLine();
} */