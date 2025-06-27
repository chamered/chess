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

public class GameImpl implements Game {
    private BoardImpl board;
    private Player whitePlayer;
    private Player blackPlayer;
    private Color currentTurn;
    private GameState gameState;
    private static boolean running;

    // Constructor
    public GameImpl() {
        board = new BoardImpl();
        currentTurn = Color.WHITE;
    }

    @Override
    public void start() {
        printWelcomeMessage();
        board = new BoardImpl();
        currentTurn = Color.WHITE;
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
        else if(RulesEngine.isThreeFoldRepetition(board)) gameState = GameState.DRAW;
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
        Optional<Piece> possibleEatenPiece = Optional.ofNullable(board.getPieceAt(move.to()));
        board.setPieceAt(move.to(), piece);



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
    public void undoMove(Piece piece, Move move){
        board.setPieceAt(move.to(), null);
        board.setPieceAt(move.from(), piece);
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

            Position from;
            Position to;

            if (getCurrentPlayer() instanceof BotPlayer bot) {
                Move move = bot.chooseMove(board);
                from = new Position(move.from().row(), move.from().column());
                to = new Position(move.to().row(), move.to().column());
            } else {
                System.out.println("Enter your move. [a-h 1-8]:");
                System.out.print("> ");
                String input = InputHandler.readLine().toLowerCase();

                if (input.equals("restart")) break;

                if (input.length() == 4) input = input.substring(0, 2) + " " + input.substring(2);

                String[] tokens = input.split("\\s+"); // Split the input by the white spaces

                if (tokens.length != 2 || !isValidFormat(tokens[0]) || !isValidFormat(tokens[1])) {
                    System.out.println("\u001B[31mInvalid input. Please use format: [a-h 1-8]\u001B[0m");
                    continue;
                }

                from = new Position(Piece.fromAlgebraic(tokens[0]).row(), Piece.fromAlgebraic(tokens[0]).column());
                to = new Position(Piece.fromAlgebraic(tokens[1]).row(), Piece.fromAlgebraic(tokens[1]).column());
            }

            boolean success = movePiece(new Move(from, to));
            if (!success) System.out.println("\u001B[31mInvalid move. Try again.\u001B[0m");

            handleGameState(checkGameState());
        }

        // Resets to start a new game
        board = new BoardImpl();
        currentTurn = Color.WHITE;
        start();
    }

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
                exitGame();
                break;
            case STALEMATE:
                System.out.println("\u001B[34mSTALEMATE!\u001B[0m The game ends in a draw. No legal move left.");
                exitGame();
                break;
            case DRAW:
                System.out.println("\u001B[36mDRAW!\u001B[0m 50 moves without pawn movement or capture. It’s a draw!");
                exitGame();
                break;
        }
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
                Type 'restart' during a match to start a new game.
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