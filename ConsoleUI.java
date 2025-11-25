import java.util.Scanner;


public class ConsoleUI {

    private final boolean animations;
    private final String bookingsFile;
    private final Scanner input = new Scanner(System.in);
    private final BookingService bookingService;

    public ConsoleUI(boolean animations, String bookingsFile) {
        this.animations = animations;
        this.bookingsFile = bookingsFile;
        this.bookingService = new BookingService(bookingsFile);
    }

    private void waitAnim(long ms) {
        if (animations) {
            try { Thread.sleep(ms); } catch (Exception ignored) {}
        }
    }

    private void loading(String text, int steps) {
        System.out.println("\n" + text);
        for (int i = 0; i < steps; i++) {
            System.out.print("█");
            waitAnim(50);
        }
        System.out.println(" 100%\n");
    }

    public void start() {

        System.out.println("Hello! Welcome to Grab 🛺");
        System.out.print("Press Enter to sign up...");
        input.nextLine();

        loading("Loading Sign Up Page......", 30);
        System.out.println("Your Journey Starts Here!\n");

        System.out.print("Name: ");
        String name = input.nextLine();

        System.out.print("Age: ");
        int age = Integer.parseInt(input.nextLine());

        System.out.print("Address: ");
        String address = input.nextLine();

        System.out.print("Contact Number: ");
        long contact = Long.parseLong(input.nextLine());

        loading("Setting up your account......", 20);

        MenuHelper.box("1. 👤 Passenger", "2. 👨🏻‍💼 Driver");
        System.out.print("Account Type: ");
        int type = Integer.parseInt(input.nextLine());

        if (type == 1) passengerFlow(name, age, address, contact);
        else driverFlow(name, age, address, contact);
    }

    // ---------------------- PASSENGER SIGNUP -----------------------

    private void passengerFlow(String name, int age, String address, long contact) {

        loading("Loading Passenger Options......", 15);

        System.out.println("\nWelcome to the Ride, Passenger! 🎉");
        MenuHelper.box(
                "1. ❌ Regular Passenger",
                "2. 🎓 Student",
                "3. ♿ PWD",
                "4. 👴 Senior Citizen"
        );
        System.out.print("Enter choice: ");
        int choice = Integer.parseInt(input.nextLine());

        String category = switch (choice) {
            case 2 -> "Student";
            case 3 -> "PWD";
            case 4 -> "Senior";
            default -> "Regular";
        };

        String id = null;
        if (!category.equals("Regular")) {
            System.out.print("Enter valid ID number: ");
            id = input.nextLine();
            System.out.println("✅ ID Verified!");
        }

        loading("Preparing Dashboard...", 30);

        Passenger p = new Passenger(name, age, address, contact, category, id);
        homePassenger(p);
    }

    // ----------------------- DRIVER SIGNUP -----------------------

    private void driverFlow(String name, int age, String address, long contact) {

        System.out.println("\nDriver Setup:");
        MenuHelper.box("1. Standard", "2. Premium", "3. Motorcycle");
        System.out.print("Enter service type: ");
        int service = Integer.parseInt(input.nextLine());

        System.out.print("Set driver bio: ");
        String bio = input.nextLine();

        loading("Creating Driver Profile...", 25);

        Driver d = new Driver(name, age, address, contact, service, bio);
        homeDriver(d);
    }

    // ------------------------- PASSENGER HOME -------------------------

    private void homePassenger(Passenger p) {

        MenuHelper.title("🚘 Welcome to your Grab Dashboard!");
        System.out.println("Thank you for choosing Grab 💚");

        // Add booking menu later…
    }

    // ------------------------- DRIVER HOME -------------------------

    private void homeDriver(Driver d) {

        MenuHelper.title("🚗 Driver Dashboard");
        // Add driver options later…
    }
}
