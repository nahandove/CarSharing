package carsharing;

import java.util.Scanner;

public class ConsoleHelper {
    static Scanner scanner = new Scanner(System.in);
    public static String readString() {
        return scanner.nextLine();
    }
    public static void writeMessage(String message) {
        System.out.println(message);
    }
}
