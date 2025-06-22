package game;

import java.util.Scanner;

public class InputHandler {
    private Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public String selectMode() {
        System.out.println("Select the game mode:\n1v1 or 1vBot");
        System.out.print("> ");
        String mode = scanner.nextLine();

        switch (mode) {
            case "1v1":
                return "1v1";
            case "1vBot":
                System.out.println("1vBot mode is not implemented yet. Defaulting to 1v1.");
                return "1v1";
            default:
                System.out.println("Invalid mode: '" + mode + "'");
                return selectMode();
        }
    }

    public String readLine() {
        return scanner.nextLine().trim();
    }
}
