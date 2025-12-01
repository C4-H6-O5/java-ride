import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public final class Utility {

    private static final Random random = new Random();

    private Utility() {}

    // ======================================================
    // ANSI COLORS (COLOR CODES)
    // ======================================================
    public static final String RESET       = "\u001B[0m";
    public static final String BOLD        = "\u001B[1m";

    public static final String GREEN         = "\u001B[32m";     // Grab green
    public static final String BRIGHT_GREEN  = "\u001B[92m";     // Accent
    public static final String WHITE         = "\u001B[97m";
    public static final String GRAY          = "\u001B[90m";
    public static final String RED           = "\u001B[31m";
    public static final String YELLOW        = "\u001B[33m";
    public static final String BLUE          = "\u001B[34m";
    public static final String CYAN          = "\u001B[36m";


    // ======================================================
    // INPUT HANDLING
    // ======================================================
    public static int getIntInput(Scanner input) {
        while (true) {
            try {
                return Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(RED + "[ERROR] Invalid input! " + RESET );
                System.out.print("Please enter a valid number: ");
            }
        }
    }


    // ======================================================
    // CLEAR SCREEN
    // ======================================================
    public static void clearConsole() {
        try {
            String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }


    // ======================================================
    // WELCOME PROMPT 
    // ======================================================
    public static void welcomePrompt(Scanner input, String name) {
        System.out.println(GREEN + BOLD + "\n --- All set! Let's hit the road, " + name + "!---" + RESET);
        System.out.println(YELLOW + "> Loading your dashboard..." + RESET);
        showLoading("Initializing Dashboard", 2);
        System.out.println(GREEN + BOLD + "============================================ " + RESET);
        System.out.println( BOLD + "\nHello, " + BOLD + CYAN + name + RESET +  BOLD + "! Your setup is now complete." + RESET);
        System.out.println("");
        System.out.print(BOLD + "Press " + BRIGHT_GREEN + "[ENTER]" + RESET + BOLD +" to continue to the Home Page... " + RESET);
        input.nextLine();
    }


    // ======================================================
    // SELECT USER TYPE
    // ======================================================
    public static UserType selectUserType(Scanner input) {
        clearConsole();
        boxTitle("SELECT USER TYPE");

        UserType[] types = UserType.values();

        for (int i = 0; i < types.length; i++) {
            System.out.println(BLUE + BOLD + "[" + (i + 1) + "] " + RESET + WHITE + types[i] + RESET);
        }

        while (true) {
            System.out.println(" ");
            System.out.print(WHITE + BOLD +"Enter number for type: " + RESET);
            int choice = getIntInput(input);

            if (choice > 0 && choice <= types.length) return types[choice - 1];

            System.out.println(RED + "Invalid choice. Try again." + RESET);
        }
    }


    // ======================================================
    // LOCATION SELECTION
    // ======================================================
    public static LocationManager.Location selectLocation(
            Scanner input,
            LocationManager locationManager,
            LocationManager.Location exclude
    ) 
    
    {
        clearConsole();
        boxTitle("CHOOSE LOCATION (FROM - TO)");

        List<LocationManager.Location> available = new ArrayList<>(locationManager.getLocations());
        if (exclude != null) available.remove(exclude);

        for (int i = 0; i < available.size(); i++) {
            System.out.println(RED + BOLD + "[" + (i + 1) + "] " + RESET + WHITE + available.get(i).getName() + RESET);
        }

        while (true) {
            System.out.print(BOLD + "\nEnter your choice: " + RESET);
            int choice = getIntInput(input);

            if (choice > 0 && choice <= available.size()) return available.get(choice - 1);

            System.out.println(RED + "Invalid selection. Please try again." + RESET);
        }
    }


    // ======================================================
    // VEHICLE TYPE
    // ======================================================
    public static VehicleType selectVehicleType(Scanner input) {
        clearConsole();
        boxTitle("SELECT VEHICLE TYPE");

        VehicleType[] types = VehicleType.values();

        for (int i = 0; i < types.length; i++) {
            System.out.println(YELLOW + BOLD + "[" + (i + 1) + "] " + RESET + WHITE + types[i] + RESET);
        }

        while (true) {
            System.out.print(BOLD + "\nEnter type: " + RESET);
            int choice = getIntInput(input);

            if (choice > 0 && choice <= types.length) return types[choice - 1];

            System.out.println(RED + "Invalid choice." + RESET);
        }
    }


    // ======================================================
    // RANDOM DRIVER
    // ======================================================
    public static Driver findRandomDriver(
            VehicleType type,
            List<Driver> drivers,
            List<Driver> exclude
    ) {
        List<Driver> matched = drivers.stream()
                .filter(d -> d.getVehicle().getVehicleType() == type)
                .filter(d -> exclude == null || !exclude.contains(d))
                .collect(Collectors.toList());

        if (matched.isEmpty()) return null;

        return matched.get(random.nextInt(matched.size()));
    }


    // ======================================================
    //  LOADING BAR
    // ======================================================
    public static void showLoading(String task, int durationSec) {
        char FILL = '\u2588';
        char EMPTY = '\u2591';

        int barLength = 30;
        long sleepTime = (durationSec * 1000L) / 100;

        System.out.println(GREEN + task + RESET);

        try {
            for (int i = 0; i <= 100; i++) {
                int filled = (i * barLength) / 100;

                StringBuilder bar = new StringBuilder();

                for (int j = 0; j < barLength; j++) {
                    bar.append(j < filled ? FILL : EMPTY);
                }

                System.out.print("\r" + BRIGHT_GREEN + bar + " " + i + "%" + RESET);

                Thread.sleep(sleepTime);
            }
            System.out.println("\n");

        } catch (InterruptedException e) {
            System.out.println("Loading interrupted.");
        }
    }


    // ======================================================
    // BOX TITLE
    // ======================================================
    public static void boxTitle(String title) {
        String line = "+=============================================================+";
        System.out.println(
            GREEN + BOLD + line + RESET + "\n" +
            GREEN + BOLD + "|  " + center(title, 59) + "|" + RESET + "\n" +
            GREEN + BOLD + line + RESET
        );
    }

    private static String center(String text, int width) {
        int len = text.length();
        if (len >= width) return text;

        int left = (width - len) / 2;
        int right = width - len - left;

        return " ".repeat(left) + text + " ".repeat(right);
    }


    // ======================================================
    // GRAB LOGO (Professional Console Style)
    // ======================================================
    public static void displayGrabLogo() {
        String[] logo = {
        "           )))                                               ",
        "         (((                                       ____________",
        "           )))                                _____//___||___\\\\_____",
        "          ____                               (______    __        __)",
        "         /   /_____ ___ ___    ___  _____ __ /  ___ \\  (__)______/ /____",
        "  ____  /   //  ___ ` / \\  \\  /  //  __  ` //  /__/ / /  //  ___  //  _ \\",
        " /   /_/   //  /_ /  /   \\  \\/  //  /__/  //  __,__/ /  //  /__/ //  __/ ",
        " \\_______ / \\____,__/     \\____/ \\____,__//__/  \\__\\/__/ \\___,__/ \\____/ ",
        "",
        "------------------------------------------------------------------------",
        YELLOW +"                      Welcome to JavaRide                           " + RESET,
        GREEN + "                  Brewing Better Rides For You!                        " + RESET,
        CYAN + BOLD + "------------------------------------------------------------------------" + RESET,
      };
      
      System.out.println(CYAN + BOLD);
      for (String line : logo) System.out.println(line);
      System.out.print(RESET);
      Scanner input = new Scanner(System.in);
    }
}
