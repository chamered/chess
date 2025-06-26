package game;

import java.util.Scanner;

public class InputHandler {
    /**
     * Returns a string with the selected mode.
     * @return the selected mode as String
     */
    public static String selectMode() {
        System.out.println("Select the game mode. [\"1v1\" / \"1vBot\" / \"BvB\"(\u001B[33mBeta\u001B[0m)]:");
        System.out.print("> ");
        String mode = readLine();

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
     * Returns a string representing the color selected by the user.
     * @return the chosen color
     */
    public static String chooseColor() {
        System.out.println("What side would you like to play as. [w/b]:");
        System.out.print("> ");
        String color = readLine().toLowerCase();

        return switch (color) {
            case "w" -> "w";
            case "b" -> "b";
            default -> {
                System.out.println("\u001B[31m" + color + " is not a side.\u001B[0m");
                yield chooseColor();
            }
        };
    }

    /**
     * Returns an integer representing the depth of the bot selected by the user.
     * @return the chosen depth
     */
    public static int[] selectDepth() {
        System.out.println("How deep should the BOT look for moves?");
        System.out.println("\u001B[31mWarning\u001B[0m: values above 3 will be very slow. [n]?");
        System.out.print("> ");

        String[] depth = readLine().split(" ");
        int depth1;
        int depth2;
        try {
            depth1 = Integer.parseInt(depth[0]);
            depth2 = depth.length == 1 ? 1 : Integer.parseInt(depth[1]);
        } catch (NumberFormatException e) {
            System.out.println("\u001B[31mInvalid number.\u001B[0m");
            return selectDepth();
        }

        if (depth1 < 1 || depth2 < 1) {
            System.out.println("\u001B[31mThe depth must be more than 0.\u001B[0m");
            return selectDepth();
        }
        return new int[]{depth1, depth2};
    }

    /**
     * Returns the user input without spaces.
     * @return the user input
     */
    public static String readLine() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();

        if (input.equals("exit")) GameImpl.exitGame();
        else if (input.equals("restart")) GameImpl.restartGame();

        return input;
    }
}
