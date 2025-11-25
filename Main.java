package ____;

import ___________;

public class Main {
    public static void main(String[] args) {

        boolean animations = true;
        String bookingsFile = "bookings.csv";

        for (String a : args) {
            if ("--no-animations".equalsIgnoreCase(a)) {
                animations = false;
            } else if (a.startsWith("--bookings=")) {
                bookingsFile = a.substring("--bookings=".length());
            }
        }

        ConsoleUI ui = new ConsoleUI(animations, bookingsFile);
        ui.start();
    }
}
