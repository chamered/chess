package game;

import java.util.Scanner;

public class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Returns a string with the selected mode.
     * @return the selected mode as String
     */
    public static String selectMode() {
        System.out.println("Select the game mode. [\"1v1\" / \"1vBot\" / \"BvB\"(\u001B[33mBeta\u001B[0m)]:");
        System.out.print("> ");
        String mode = scanner.nextLine();

        return switch (mode) {
            case "1v1" -> "1v1";
            case "1vBot" -> "1vBot";
            case "BvB" -> "BvB";
            default -> {
                System.out.println("\u001B[31mInvalid mode: '" + mode + "'\u001B[0m");
                yield selectMode();
            }
        };
    }

    /**
     * Returns a string with the color selected by the user.
     * @return the chosen color
     */
    public static String chooseColor() {
        System.out.println("What side would you like to play as. [w/b]:");
        System.out.print("> ");
        String color = scanner.nextLine().toLowerCase();

        return switch (color) {
            case "w" -> "w";
            case "b" -> "b";
            case "exit" -> "exit";
            default -> {
                System.out.println("\u001B[31m" + color + " is not a side.\u001B[0m");
                yield chooseColor();
            }
        };
    }

    public static int selectDepth() {
        System.out.println("How deep should the BOT look for moves?");
        System.out.println("\u001B[31mWarning\u001B[0m: values above 3 will be very slow. [n]?");
        System.out.print("> ");
        int depth = scanner.nextInt();
        scanner.nextLine();
        return depth;
    }

    /**
     * Returns the user input without spaces.
     * @return the user input
     */
    public static String readLine() {
        return scanner.nextLine().trim();
    }
}
