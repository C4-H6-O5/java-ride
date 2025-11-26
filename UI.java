import java.util.Scanner;

public class UI {

    // 🎨 ANSI Color Codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String PURPLE = "\u001B[35m";
    public static final String WHITE = "\u001B[37m";
    public static final String BOLD = "\u001B[1m";

    // 🚗 ASCII Grab Logo + Welcome Message
    public static void displayGrabLogo() {
        String[] logo = {
            "           )))                                               ",
            "         (((                                           ____________",
            "           )))                                    ____//____||____\\____",
            "            __                                  ( ______    __       ___)",
            "          /   / ____ _ __ ___    ___  ____ _ __ /  ___  \   (_) ___ /  / ____ ",
            "   __    /   //   __ `  / \  \  /  //   __ `  //  /__ / /  / //  __   / /  _ \",
            " /   /__/   //  /__ /  /   \  \/  //  /__ /  //  __ ,__\  / //  /__/ / /  __ /",
            " \_______ /  \ ____,__/     \ __/  \ ____,__//__/    \__\/_/ \___,_ /  \____/ ",
            "───────────────────────────────────────────────────────────────────────────────",
            "               🚗  Welcome to JavaRide — Your Journey Starts Here!  🛺",
            "───────────────────────────────────────────────────────────────────────────────",
            "",
            YELLOW + "Press 'Enter' to continue..." + RESET
        };

        System.out.println(CYAN + BOLD);
        for (String line : logo) {
            System.out.println(line);
        }
        System.out.println(RESET);
    }

    // 🌀 Fade Transition Animation
    public static void fadeTransition(String message) {
        System.out.print(YELLOW + "\n" + message);
        for (int i = 0; i < 3; i++) {
            System.out.print(".");
            sleep(500);
        }
        System.out.print(RESET);
        clearScreen();
        sleep(400);
    }

    // 🧭 Welcome Prompt + Progress Animation
    public static void welcomePrompt(Scanner input, String name) {
        System.out.println(GREEN + "\nAll set, " + name + "! Let's hit the road! 🛣️" + RESET);
        System.out.println(YELLOW + "Loading your dashboard..." + RESET);
        progressBar();
    }

    // ⏳ Progress Bar Animation
    public static void progressBar() {
        int total = 30;
        System.out.print("[");
        for (int i = 0; i <= total; i++) {
            int percent = (i * 100) / total;
            String bar = "🚗".repeat(i) + " ".repeat(total - i);
            System.out.print(GREEN + "\r[" + bar + "] " + percent + "%" + RESET);
            sleep(80);
        }
        System.out.println();
    }

    // 🏠 Home Page
    public static void showHomePage() {
        System.out.println(CYAN + BOLD + "🚘 Welcome to your Grab Dashboard!" + RESET);
        System.out.println(YELLOW + "You can now book rides, update your profile, or explore promotions!" + RESET);
        System.out.println(GREEN + "\nThank you for choosing Grab 💚" + RESET);
    }

    // 🧹 Clear Console
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // 📋 Draw Menu Box
    public static void drawMenuBox(String[] options) {
        System.out.println(BLUE + "┌─────────────────────────────────────┐" + RESET);
        for (String option : options) {
            int padding = 37 - option.length();
            if (padding < 0) padding = 0;
            System.out.println(BLUE + "│ " + WHITE + option + " ".repeat(padding) + BLUE + "│" + RESET);
        }
        System.out.println(BLUE + "└─────────────────────────────────────┘" + RESET);
    }

    // 💤 Sleep Helper
    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
