package game;

import board.BoardImpl;
import board.Position;
import pieces.Color;
import pieces.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    private static BoardImpl board;
    private Player whitePlayer;
    private Player blackPlayer;
    private Color currentTurn;
    private GameState gameState;
    private InputHandler inputHandler;

    public Game() {
        board = new BoardImpl();
        currentTurn = Color.WHITE;
        inputHandler = new InputHandler();
    }

    //Initializes board and players
    public void start() {
        printWelcomeMessage();
        String mode = inputHandler.selectMode();

        if (mode.equals("1v1")) {
            whitePlayer = new Player("White Player", Color.WHITE);
            blackPlayer = new Player("Black Player", Color.BLACK);

            runGameLoop();
        }
    }

    //Check, Checkmate, etc.
    private void checkGameState() {
        //TODO
    }

    //Makes the move
    public boolean movePiece(Position from, Position to) {
        Piece piece = board.getPieceAt(from);

        if (piece == null || piece.getColor() != currentTurn || !isMoveValid(from, to)) {
            return false;
        }

        board.setPieceAt(from, null);
        board.setPieceAt(to, piece);

        switchPlayer();

        return true;
    }

    //Check validity of the move
    public boolean isMoveValid(Position from, Position to) {
        Move move = new Move(from, to);
        RulesEngine engine = new RulesEngine();
        return engine.isLegalMove(board, move, currentTurn);
    }

    //Changes the turn
    public void switchPlayer() {
        currentTurn = (currentTurn == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    // Returns the current player turn
    public Player getCurrentPlayer() {
        return whitePlayer.getColor() == currentTurn ? whitePlayer : blackPlayer;
    }

    //Used in getLegalMoves
    public List<String> getUnfilteredPossibleMoves(Position from) {
        Piece piece = board.getPieceAt(from);

        if (piece == null) {
            System.out.println("No piece at given position.");
            return Collections.emptyList();
        }

        return piece.generatePossibleMoves(board, from);
    }

    //Could be helpful for Moves Generation
    public List<String> getLegalMoves(Position from) {
        List<String> legalMoves = new ArrayList<>();
        List<String> possibleMoves = getUnfilteredPossibleMoves(from);
        for (String move : possibleMoves) {
            int[] coordinates = Piece.fromAlgebraic(move);
            Position to = new Position(coordinates[0], coordinates[1]);
            if (isMoveValid(from, to)) {
                legalMoves.add(move);
            }
        }
        return legalMoves;
    }

    public void runGameLoop() {
        boolean running = true;

        while (running) {
            board.printBoard();
            System.out.println(getCurrentPlayer().getName() + "'s turn");

            System.out.println("Enter your move (e.g., e2 e4)");
            System.out.print("> ");
            String input = inputHandler.readLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Game ended.");
                break;
            }

            String[] tokens = input.split("\\s+"); // Split the input by the white spaces
            if (tokens.length != 2) {
                System.out.println("Invalid input. Please use format: e2 e4");
                continue;
            }

            try {
                Position from = new Position(Piece.fromAlgebraic(tokens[0])[0], Piece.fromAlgebraic(tokens[0])[1]);
                Position to = new Position(Piece.fromAlgebraic(tokens[1])[0], Piece.fromAlgebraic(tokens[1])[1]);

                boolean success = movePiece(from, to);
                if (!success) {
                    System.out.println("Invalid move. Try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid coordinates. Please use format: e2 e4");
            }
        }
    }

    public void printWelcomeMessage() {
        System.out.println(
                """
                =======================
                  JAVA TERMINAL CHESS \s
                =======================
                Welcome to the game of Chess!
                
                White always starts.
                To make a move, type coordinates in the format: e2 e4
                
                Type 'help' for commands or 'exit' to quit.
                Let the game begin!
                """
        );
    }
}
