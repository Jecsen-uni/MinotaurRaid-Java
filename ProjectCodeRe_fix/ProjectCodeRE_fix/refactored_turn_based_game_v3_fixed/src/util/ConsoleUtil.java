package util;

public final class ConsoleUtil {
    private ConsoleUtil() {}

    public static void clearConsole() {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
