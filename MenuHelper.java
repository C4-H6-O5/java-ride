public class MenuHelper {

    public static void title(String text) {
        System.out.println("\n==============================");
        System.out.println(" " + text);
        System.out.println("==============================");
    }

    public static void option(int num, String text) {
        System.out.println(num + ". " + text);
    }

    public static void box(String... rows) {
        System.out.println("┌─────────────────────────────────────┐");
        for (String r : rows) {
            System.out.printf("│ %-37s │\n", r);
        }
        System.out.println("└─────────────────────────────────────┘");
    }
}
