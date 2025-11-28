import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public final class Utility {
    private static final Random random = new Random();
    private Utility() {}

    public static int getIntInput(Scanner input) {
        while (true) {
            try {
                return Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

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
            // Handle exceptions, e.g., by printing new lines as a fallback
            for (int i = 0; i < 50; ++i) System.out.println();
        }
    }

    public static void welcomePrompt(Scanner input, String name) {
        System.out.println("\nAll set! Let's hit the road, " + name + "!");
        System.out.print("Press 'Enter' to go to the Home Page! ");
        input.nextLine();
    }

    public static UserType selectUserType(Scanner input) {
        UserType[] types = UserType.values();
        System.out.println("Select User Type:");
        for (int i = 0; i < types.length; i++) {
            System.out.printf("[%d] %s\n", i + 1, types[i]);
        }
        while (true) {
            System.out.print("Enter number for type: ");
            int choice = getIntInput(input);
            if (choice > 0 && choice <= types.length) {
                return types[choice - 1];
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    public static LocationManager.Location selectLocation(Scanner input, LocationManager locationManager, LocationManager.Location locationToExclude) {
        List<LocationManager.Location> availableLocations = new ArrayList<>(locationManager.getLocations());
        if (locationToExclude != null) {
            availableLocations.remove(locationToExclude);
        }

        for (int i = 0; i < availableLocations.size(); i++) {
            System.out.printf("[%d] %s\n", i + 1, availableLocations.get(i).getName());
        }

        while (true) {
            System.out.print("Enter the number for your choice: ");
            int choice = getIntInput(input);
            if (choice > 0 && choice <= availableLocations.size()) {
                return availableLocations.get(choice - 1);
            } else {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    public static VehicleType selectVehicleType(Scanner input) {
        VehicleType[] types = VehicleType.values();
        for (int i = 0; i < types.length; i++) {
            System.out.printf("[%d] %s\n", i + 1, types[i]);
        }
        while (true) {
            System.out.print("Enter number for type: ");
            int choice = getIntInput(input);
            if (choice > 0 && choice <= types.length) {
                return types[choice - 1];
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    public static Driver findRandomDriver(VehicleType type, List<Driver> driverAccounts, List<Driver> driversToExclude) {
        List<Driver> suitableDrivers = driverAccounts.stream()
            .filter(d -> d.getVehicle().getVehicleType() == type)
            .filter(d -> driversToExclude == null || !driversToExclude.contains(d))
            .collect(Collectors.toList());

        if (suitableDrivers.isEmpty()) {
            return null;
        }
        return suitableDrivers.get(random.nextInt(suitableDrivers.size()));
    }

    public static void showLoading(String task, int duration) {
        String GREEN = "\u001B[32m";
        String RESET = "\u001B[0m";
        
        // SOLID BLOCKS
        char FILL = '█'; 
        char EMPTY = '░'; 
        
        int barLength = 30;
        long sleepTime = (duration * 1000) / 100;

        System.out.println(task);

        try {
            for (int i = 0; i <= 100; i++) {
                int filled = (i * barLength) / 100;
                
                StringBuilder bar = new StringBuilder();
                
                for (int j = 0; j < barLength; j++) {
                    if (j < filled) {
                        bar.append(FILL);
                    } else {
                        bar.append(EMPTY);
                    }
                }

                System.out.print("\r" + GREEN + bar.toString() + " " + i + "% " + RESET);
                
                Thread.sleep(sleepTime);
            }
            System.out.println("\n");
        } catch (InterruptedException e) {
            System.out.println("Interrupted.");
        }
    }
}