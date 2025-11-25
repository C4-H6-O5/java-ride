import java.util.Scanner;

public class ConsoleUI {

    private final boolean animations;
    private final String bookingsFile;
    private final Scanner input = new Scanner(System.in);
    private final BookingService bookingService;

    public static class Color {
        public static final String RESET = "\u001B[0m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
        public static final String PURPLE = "\u001B[35m";
        public static final String CYAN = "\u001B[36m";
        public static final String WHITE = "\u001B[37m";
    }

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
        System.out.println("\n" + Color.CYAN + text + Color.RESET);
        for (int i = 0; i < steps; i++) {
            System.out.print(Color.CYAN + "█" + Color.RESET);
            waitAnim(40);
        }
        System.out.println(" " + Color.GREEN + "100%" + Color.RESET + "\n");
    }

    public void start() {

        System.out.println(Color.GREEN + "Hello! Welcome to Grab 🛺" + Color.RESET);
        System.out.print(Color.YELLOW + "Press Enter to sign up..." + Color.RESET);
        input.nextLine();

        loading("Loading Sign Up Page......", 30);
        System.out.println(Color.CYAN + "Your Journey Starts Here!" + Color.RESET + "\n");

        System.out.print(Color.YELLOW + "Name: " + Color.RESET);
        String name = input.nextLine();

        System.out.print(Color.YELLOW + "Age: " + Color.RESET);
        int age = Integer.parseInt(input.nextLine());

        System.out.print(Color.YELLOW + "Address: " + Color.RESET);
        String address = input.nextLine();

        System.out.print(Color.YELLOW + "Contact Number: " + Color.RESET);
        long contact = Long.parseLong(input.nextLine());

        loading("Setting up your account......", 20);

        MenuHelper.box(
                "1. 👤 Passenger",
                "2. 👨🏻‍💼 Driver"
        );

        System.out.print(Color.YELLOW + "Account Type: " + Color.RESET);
        int type = Integer.parseInt(input.nextLine());

        if (type == 1) passengerFlow(name, age, address, contact);
        else driverFlow(name, age, address, contact);
    }

    // ---------------------- PASSENGER SIGNUP -----------------------

    private void passengerFlow(String name, int age, String address, long contact) {

        loading("Loading Passenger Options......", 15);

        System.out.println(Color.GREEN + "\nWelcome to the Ride, Passenger! 🎉" + Color.RESET);
        MenuHelper.box(
                "1. ❌ Regular Passenger",
                "2. 🎓 Student",
                "3. ♿ PWD",
                "4. 👴 Senior Citizen"
        );
        System.out.print(Color.YELLOW + "Enter choice: " + Color.RESET);
        int choice = Integer.parseInt(input.nextLine());

        String category = switch (choice) {
            case 2 -> "Student";
            case 3 -> "PWD";
            case 4 -> "Senior";
            default -> "Regular";
        };

        String id = null;
        if (!category.equals("Regular")) {
            System.out.print(Color.YELLOW + "Enter valid ID number: " + Color.RESET);
            id = input.nextLine();
            System.out.println(Color.GREEN + "✅ ID Verified!" + Color.RESET);
        }

        loading("Preparing Dashboard...", 30);

        Passenger p = new Passenger(name, age, address, contact, category, id);
        homePassenger(p);
    }

    // ----------------------- DRIVER SIGNUP -----------------------

    private void driverFlow(String name, int age, String address, long contact) {

        System.out.println(Color.BLUE + "\nDriver Setup:" + Color.RESET);
        MenuHelper.box("1. Standard", "2. Premium", "3. Motorcycle");

        System.out.print(Color.YELLOW + "Enter service type: " + Color.RESET);
        int service = Integer.parseInt(input.nextLine());

        System.out.print(Color.YELLOW + "Set driver bio: " + Color.RESET);
        String bio = input.nextLine();

        loading("Creating Driver Profile...", 25);

        Driver d = new Driver(name, age, address, contact, service, bio);
        homeDriver(d);
    }

    // ------------------------- PASSENGER HOME -------------------------

    private void homePassenger(Passenger p) {

        MenuHelper.title(Color.GREEN + "🚘 Welcome to your Grab Dashboard!" + Color.RESET);
        System.out.println(Color.CYAN + "Thank you for choosing Grab 💚" + Color.RESET);
    }

    // ------------------------- DRIVER HOME -------------------------

    private void homeDriver(Driver d) {

        MenuHelper.title(Color.GREEN + "🚗 Driver Dashboard" + Color.RESET);
        System.out.println(Color.CYAN + "Welcome, Driver!" + Color.RESET);
    }
}
