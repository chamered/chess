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
    private final InputHandler inputHandler;

    // Constructor
    public Game() {
        board = new BoardImpl();
        currentTurn = Color.WHITE;
        inputHandler = new InputHandler();
    }

    /**
     * Start the game.
     */
    public void start() {
        printWelcomeMessage();
        String mode = inputHandler.selectMode();

        if (mode.equals("1v1")) {
            whitePlayer = new HumanPlayer("White Player's", Color.WHITE);
            blackPlayer = new HumanPlayer("Black Player's", Color.BLACK);
        } else if (mode.equals("1vBot")) {
            String color = inputHandler.chooseColor();
            whitePlayer = color.equals("w") ? new HumanPlayer("It's your", Color.WHITE) : new BotPlayer(Color.WHITE);
            blackPlayer = color.equals("b") ? new HumanPlayer("It's your", Color.BLACK) : new BotPlayer(Color.BLACK);
            System.out.println("You will play as " + (color.equals("w") ? "white" : "black"));
        }

        runGameLoop();
    }

    /**
     * Checks the game state, providing meaningful messages
     */
    private void checkGameState(Player p) {
        if(RulesEngine.isKingInCheck(Color.BLACK, board) || RulesEngine.isKingInCheck(Color.WHITE, board)) gameState = GameState.CHECK;
        if(RulesEngine.isCheckmate(Color.BLACK, board) || RulesEngine.isCheckmate(Color.WHITE, board)) gameState = GameState.CHECKMATE;
        if(RulesEngine.isStalemate(Color.BLACK, board) || RulesEngine.isStalemate(Color.WHITE, board)) gameState = GameState.STALEMATE;
        if(RulesEngine.isFiftyMoveRule(whitePlayer) || RulesEngine.isFiftyMoveRule(blackPlayer)) gameState = GameState.DRAW;

        switch(gameState){
            case CHECK -> System.out.println("CHECK");
            case CHECKMATE -> System.out.println("CHECKMATE");
            case DRAW -> System.out.println("DRAW!");
            case STALEMATE -> System.out.println("STALEMATE");
        }
    }

    /**
     * Attempts to move a piece.
     * @param move the move to make
     * @return true if the move is valid and was executed, false otherwise
     */
    public boolean movePiece(Move move) {
        Piece piece = board.getPieceAt(move.from());

        if (piece == null || piece.getColor() != currentTurn || !RulesEngine.isMoveLegal(board, move, currentTurn)) {
            return false;
        }

        board.setPieceAt(move.from(), null);
        Piece pieceTo = board.getPieceAt(move.to());

        if(piece.eatOtherPiece(pieceTo)){
            Move.moveHistory.put(piece.getColor(), new ArrayList<>(Collections.singleton(move)));
        }

        board.setPieceAt(move.to(), piece);

        switchPlayer();

        return true;
    }

    /**
     * Switches the turn to the next player.
     */
    public void switchPlayer() {
        currentTurn = (currentTurn == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    /**
     * Returns the current player.
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return whitePlayer.getColor() == currentTurn ? whitePlayer : blackPlayer;
    }

    /**
     *
     * @param from the initial position
     * @return a List of Strings containing all the possibles moves for the given Piece in the position
     */
    public List<String> getUnfilteredPossibleMoves(Position from) {
        Piece piece = board.getPieceAt(from);

        if (piece == null) {
            System.out.println("No piece at given position.");
            return Collections.emptyList();
        }

        return piece.generatePossibleMoves(board, from);
    }

    /**
     * Starts main game loop for chess match.
     * Alternates turns between players, prints updated state of the board,
     * reads player input from console, attempts to perform the specified move.
     * Continues until one player types "exit" or the game ends by other rules
     * (e.g., checkmate or stalemate).
     * Invalid input formats and illegal moves are detected and handled with appropriate messages.
     */
    public void runGameLoop() {
        boolean running = true;

        while (running) {
            board.printBoard();
            String textColor = currentTurn == Color.WHITE ? "\u001B[33m" : "\u001B[34m";
            System.out.println(textColor + getCurrentPlayer().getName() + "\u001B[0m turn.");

            Position from;
            Position to;

            if (getCurrentPlayer() instanceof BotPlayer bot) {
                Move move = bot.chooseMove(board);
                from = new Position(move.from().getRow(), move.from().getColumn());
                to = new Position(move.to().getRow(), move.to().getColumn());
            } else {
                System.out.println("Enter your move [e2 e4]:");
                System.out.print("> ");
                String input = inputHandler.readLine().toLowerCase();

                if (input.equalsIgnoreCase("exit")) exit();

                String[] tokens = input.split("\\s+"); // Split the input by the white spaces
                if (tokens.length != 2) {
                    System.out.println("\u001B[31mInvalid input. Please use format: e2 e4\u001B[0m");
                    continue;
                }

                from = new Position(Piece.fromAlgebraic(tokens[0]).getRow(), Piece.fromAlgebraic(tokens[0]).getColumn());
                to = new Position(Piece.fromAlgebraic(tokens[1]).getRow(), Piece.fromAlgebraic(tokens[1]).getColumn());
            }

            try {
                boolean success = movePiece(new Move(from, to));
                if (!success) {
                    System.out.println("\u001B[31mInvalid move. Try again.\u001B[0m");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid coordinates. Please use format: e2 e4");
            }
        }
    }

    /**
     * Prints the welcome message when the program is executed.
     */
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

    public void exit() {
        System.out.println("\u001B[31mGame ended.\u001B[0m");
        System.exit(0);
    }
}
