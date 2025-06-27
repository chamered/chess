package game;

import board.BoardImpl;
import board.Position;
import pieces.Color;
import pieces.Pawn;
import pieces.Piece;
import players.BotPlayer;
import players.HumanPlayer;
import players.Player;

import java.util.Optional;
import java.util.Scanner;

public class GameImpl implements Game {
    private BoardImpl board;
    private Player whitePlayer;
    private Player blackPlayer;
    private Color currentTurn;
    private Move[] lastTwoMoves;
    private GameState gameState;
    private static boolean running;

    // Constructor
    public GameImpl() {
        board = new BoardImpl();
        currentTurn = Color.WHITE;
        lastTwoMoves = new Move[2];
    }

    @Override
    public void start() {
        printWelcomeMessage();
        board = new BoardImpl();
        currentTurn = Color.WHITE;
        lastTwoMoves = new Move[2];
        running = true;
        String mode = InputHandler.selectMode();

        switch (mode) {
            case "1v1" -> {
                whitePlayer = new HumanPlayer("White Player's", Color.WHITE);
                blackPlayer = new HumanPlayer("Black Player's", Color.BLACK);
            }
            case "1vBot" -> {
                String color = InputHandler.chooseColor();
                System.out.println("You will play as " + (color.equals("w") ? "\u001B[33mwhite\u001B[0m" : "\u001B[34mblack\u001B[0m"));
                int[] depth = InputHandler.selectDepth();
                whitePlayer = color.equals("w") ? new HumanPlayer("It's your", Color.WHITE) : new BotPlayer(Color.WHITE, depth[0]);
                blackPlayer = color.equals("b") ? new HumanPlayer("It's your", Color.BLACK) : new BotPlayer(Color.BLACK, depth[0]);
            }
            case "BvB" -> {
                int[] depth = InputHandler.selectDepth();
                whitePlayer = new BotPlayer(Color.WHITE, depth[0]);
                blackPlayer = new BotPlayer(Color.BLACK, depth[1]);
            }
        }

        runGameLoop();
    }

    /**
     * Changes the game's current state, and changes it according to RulesEngine's rules.
     * @return the current GameState
     */
    private GameState checkGameState() {
        if(RulesEngine.isCheckmate(board, Color.BLACK) || RulesEngine.isCheckmate(board, Color.WHITE)) gameState = GameState.CHECKMATE;
        else if(RulesEngine.isStalemate(board, Color.BLACK) || RulesEngine.isStalemate(board, Color.WHITE)) gameState = GameState.STALEMATE;
        else if(RulesEngine.isKingInCheck(board, Color.BLACK) || RulesEngine.isKingInCheck(board, Color.WHITE)) gameState = GameState.CHECK;
        else if(RulesEngine.isThreeFoldRepetition(board)) gameState = GameState.DRAW_BY_THREE_FOLD;
        else if(RulesEngine.isFiftyMoveRule(whitePlayer) || RulesEngine.isFiftyMoveRule(blackPlayer)) gameState = GameState.DRAW_BY_50_MOVES;
        else gameState = GameState.ACTIVE;

        return gameState;
    }

    @Override
    public boolean movePiece(Move move) {
        Piece piece = board.getPieceAt(move.from());
        if (piece == null || piece.getColor() != currentTurn || !RulesEngine.isMoveLegal(board, move, currentTurn)) {
            return false;
        }

        board.setPieceAt(move.from(), null);
        Optional<Piece> possibleEatenPiece = Optional.ofNullable(board.getPieceAt(move.to()));
        board.setPieceAt(move.to(), piece);
        lastTwoMoves[0] = lastTwoMoves[1];
        lastTwoMoves[1] = move;

        if (piece instanceof Pawn) {
            RulesEngine.resetMoveHistory(piece.getColor());
        }
        else if(possibleEatenPiece.isPresent() && piece.eatOtherPiece(possibleEatenPiece.get())){
            RulesEngine.resetMoveHistory(piece.getColor());
        }
        else {
            RulesEngine.incrementCounterFromMoveHistory(piece.getColor());
        }

        switchPlayer();

        return true;
    }

    @Override
    public boolean undoMove(){
        if (lastTwoMoves[0] == null || lastTwoMoves[1] == null) return false;

        board.setPieceAt(lastTwoMoves[0].from(), board.getPieceAt(lastTwoMoves[0].to()));
        board.setPieceAt(lastTwoMoves[0].to(), null);

        board.setPieceAt(lastTwoMoves[1].from(), board.getPieceAt(lastTwoMoves[1].to()));
        board.setPieceAt(lastTwoMoves[1].to(), null);

        lastTwoMoves[0] = null;
        lastTwoMoves[1] = null;

        return true;
    }

    @Override
    public void switchPlayer() {
        currentTurn = (currentTurn == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    @Override
    public Player getCurrentPlayer() {
        return whitePlayer.getColor() == currentTurn ? whitePlayer : blackPlayer;
    }

    @Override
    public void runGameLoop() {
        while (running) {
            board.printBoard();
            String textColor = currentTurn == Color.WHITE ? "\u001B[33m" : "\u001B[34m";
            System.out.println(textColor + getCurrentPlayer().getName() + "\u001B[0m turn.");

            Move move;
            if (getCurrentPlayer() instanceof BotPlayer bot) {
                move = bot.chooseMove(board); // Set move to the bot chosen move
            } else {
                System.out.println("Enter your move. [a-h 1-8]:");
                System.out.print("> ");
                String input = InputHandler.readLine().toLowerCase(); // Get the user input

                // If the input is 'restart', exit the loop
                if (input.equals("restart")) break;

                // If the input is 'undo', undo the last move (if possible)
                if (input.equals("undo")) {
                    boolean success = undoMove();
                    if (!success) System.out.println("\u001B[31mYou can not undo now.\u001B[0m");
                    continue;
                }

                // If the input length is 4, adds a space in between (e.g., e2e4 -> e2 e4)
                if (input.length() == 4) input = input.substring(0, 2) + " " + input.substring(2);

                // Split the input by the white spaces
                String[] tokens = input.split("\\s+");

                // If the input is invalid
                if (tokens.length != 2 || !isValidFormat(tokens[0]) || !isValidFormat(tokens[1])) {
                    System.out.println("\u001B[31mInvalid input. Please use format: [a-h 1-8]\u001B[0m"); // Prints a feedback
                    continue; // And ask for a new input
                }

                // Gets the from and to position from the input
                Position from = new Position(Piece.fromAlgebraic(tokens[0]).row(), Piece.fromAlgebraic(tokens[0]).column());
                Position to = new Position(Piece.fromAlgebraic(tokens[1]).row(), Piece.fromAlgebraic(tokens[1]).column());
                move = new Move(from, to); // Set move to user chosen move
            }

            // If the move failed, prints a feedback
            boolean success = movePiece(move);
            if (!success) System.out.println("\u001B[31mInvalid move. Try again.\u001B[0m");

            // After the move handle the game state
            handleGameState(checkGameState());
        }

        // Resets to start a new game
        board = new BoardImpl();
        currentTurn = Color.WHITE;
        start();
    }

    // Returns true if the input string matches the algebraic chess notation
    private static boolean isValidFormat(String input) {
        return input.matches("^[a-h][1-8]$");
    }

    @Override
    public void handleGameState(GameState state) {
        switch (state) {
            case CHECK:
                System.out.println("\u001B[33mCHECK!\u001B[0m Your king is under threat. Defend wisely.");
                break;
            case CHECKMATE:
                System.out.println("\u001B[31mCHECKMATE!\u001B[0m Game over." + getCurrentPlayer().getName() + " wins!");
                endGame();
                break;
            case STALEMATE:
                System.out.println("\u001B[34mSTALEMATE!\u001B[0m The game ends in a draw. No legal move left.");
                endGame();
                break;
            case DRAW_BY_50_MOVES:
                System.out.println("\u001B[36mDRAW!\u001B[0m 50 moves without pawn movement or capture. Draw by 50 moves rule.");
                endGame();
                break;
            case DRAW_BY_THREE_FOLD:
                System.out.println("\u001B[36mDRAW!\u001B[0m The same position has occurred three times. Draw by threefold repetition.");
                endGame();
                break;
        }
    }

    // Handles the end game
    private void endGame() {
        System.out.print("Do you want to start a new game [y/n]? ");
        String res = InputHandler.readLine();

        if (res.equals("y")) restartGame();
        else exitGame();
    }

    @Override
    public void printWelcomeMessage() {
        System.out.println(
                """
                =======================
                  JAVA TERMINAL CHESS \s
                =======================
                Welcome to the game of Chess!
                
                White always starts.
                To make a move, type coordinates in the format: [a-h 1-8]
                (e.g., 'e2 e4')
                
                Type 'undo' during a match to undo your latest move.
                Type 'restart' during a match to start a new game.
                Type 'exit' at any time to quit the program.
                Let the game begin!
                """
        );
    }

    /**
     * Ends the current game and start a new one.
     */
    public static void restartGame() {
        running = false;
        System.out.println("\u001B[35mGame ended.\u001B[0m Starting a new game...\n");
    }

    /**
     * Exists the program with a red message.
     */
    public static void exitGame() {
        System.out.println("\u001B[35mGoodbye! Even the pieces need rest.\u001B[0m");
        System.exit(0);
    }
}