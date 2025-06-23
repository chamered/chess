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
        System.out.println("Select the game mode (\"1v1\" or \"1vBot\"):");
        System.out.print("> ");
        String mode = scanner.nextLine();

        switch (mode) {
            case "1v1" -> {
                return "1v1";
            }
            case "1vBot" -> {
                System.out.println("1vBot mode is not implemented yet. Defaulting to 1v1.");
                return "1v1";
            }
            default -> {
                System.out.println("\u001B[31mInvalid mode: '" + mode + "'\u001B[0m");
                return selectMode();
            }
        }
    }

    /**
     * Returns the user input without spaces.
     * @return the user input
     */
    public String readLine() {
        return scanner.nextLine().trim();
    }
}
