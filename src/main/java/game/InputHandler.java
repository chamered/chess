package game;

import java.util.Scanner;

public class InputHandler {
    private final Scanner scanner;

    // Constructor
    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Returns a string with the selected mode.
     * @return the selected mode as String
     */
    public String selectMode() {
        System.out.println("Select the game mode [\"1v1\" or \"1vBot\"]:");
        System.out.print("> ");
        String mode = scanner.nextLine();

        return switch (mode) {
            case "1v1" -> "1v1";
            case "1vBot" -> "1vBot";
            default -> {
                System.out.println("\u001B[31mInvalid mode: '" + mode + "'\u001B[0m");
                yield selectMode();
            }
        };
    }

    public String chooseColor() {
        System.out.println("What side would you like to play as [w/b]?");
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

    /**
     * Returns the user input without spaces.
     * @return the user input
     */
    public String readLine() {
        return scanner.nextLine().trim();
    }
}
