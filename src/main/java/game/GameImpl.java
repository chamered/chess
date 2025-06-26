package game;

import board.BoardImpl;
import board.Position;
import pieces.Color;
import pieces.Pawn;
import pieces.Piece;
import players.BotPlayer;
import players.HumanPlayer;
import players.Player;

import java.util.Collections;
import java.util.List;

public class GameImpl implements Game {
    private BoardImpl board;
    private Player whitePlayer;
    private Player blackPlayer;
    private Color currentTurn;
    private GameState gameState;

    // Constructor
    public GameImpl() {
        board = new BoardImpl();
        currentTurn = Color.WHITE;
    }

    @Override
    public void start() {
        printWelcomeMessage();
        String mode = InputHandler.selectMode();

        switch (mode) {
            case "1v1" -> {
                whitePlayer = new HumanPlayer("White Player's", Color.WHITE);
                blackPlayer = new HumanPlayer("Black Player's", Color.BLACK);
            }
            case "1vBot" -> {
                String color = InputHandler.chooseColor();
                System.out.println("You will play as " + (color.equals("w") ? "\u001B[33mwhite\u001B[0m" : "\u001B[34mblack\u001B[0m"));
                int depth = InputHandler.selectDepth();
                whitePlayer = color.equals("w") ? new HumanPlayer("It's your", Color.WHITE) : new BotPlayer(Color.WHITE, depth);
                blackPlayer = color.equals("b") ? new HumanPlayer("It's your", Color.BLACK) : new BotPlayer(Color.BLACK, depth);
            }
            case "BvB" -> {
                int depth = InputHandler.selectDepth();
                whitePlayer = new BotPlayer(Color.WHITE, depth);
                blackPlayer = new BotPlayer(Color.BLACK, depth);
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
        else if(RulesEngine.isFiftyMoveRule(whitePlayer) || RulesEngine.isFiftyMoveRule(blackPlayer)) gameState = GameState.DRAW;
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
        board.setPieceAt(move.to(), piece);

        if (piece instanceof Pawn) {
            RulesEngine.resetMoveHistory(piece.getColor());
        } else {
            RulesEngine.incrementCounterFromMoveHistory(piece.getColor());
        }

        switchPlayer();

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
    public List<String> getUnfilteredPossibleMoves(Position from) {
        Piece piece = board.getPieceAt(from);

        if (piece == null) {
            System.out.println("No piece at given position.");
            return Collections.emptyList();
        }

        return piece.generatePossibleMoves(board, from);
    }

    @Override
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
                from = new Position(move.from().row(), move.from().column());
                to = new Position(move.to().row(), move.to().column());
            } else {
                System.out.println("Enter your move. [e2 e4]:");
                System.out.print("> ");
                String input = InputHandler.readLine().toLowerCase();

                String[] tokens = input.split("\\s+"); // Split the input by the white spaces
                if (tokens.length != 2) {
                    System.out.println("\u001B[31mInvalid input. Please use format: e2 e4\u001B[0m");
                    continue;
                }

                from = new Position(Piece.fromAlgebraic(tokens[0]).row(), Piece.fromAlgebraic(tokens[0]).column());
                to = new Position(Piece.fromAlgebraic(tokens[1]).row(), Piece.fromAlgebraic(tokens[1]).column());
            }

            try {
                boolean success = movePiece(new Move(from, to));
                if (!success) {
                    System.out.println("\u001B[31mInvalid move. Try again.\u001B[0m");
                }
                handleGameState(checkGameState());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid coordinates. Please use format: e2 e4");
            }
        }
    }

    @Override
    public void handleGameState(GameState state) {
        String strState;
        switch (state) {
            case CHECK -> strState = "CHECK";
            case CHECKMATE -> strState = "CHECKMATE";
            case STALEMATE -> strState = "STALEMATE";
            case DRAW -> strState = "DRAW";
            default -> strState = "ACTIVE";
        }

        if (strState.equals("ACTIVE")) return;

        System.out.println(strState);
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
                To make a move, type coordinates in the format: e2 e4
                
                Type 'exit' at any time to quit the program.
                Let the game begin!
                """
        );
    }

    /**
     * Exists the program with a red message.
     */
    public static void exitGame() {
        System.out.println("\u001B[35mGoodbye! Even the pieces need rest.\u001B[0m");
        System.exit(0);
    }
}