package game;

import board.BoardImpl;
import board.Position;
import pieces.Color;
import pieces.Piece;
import players.BotPlayer;
import players.HumanPlayer;
import players.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game implements GameFlow {
    private BoardImpl board;
    private Player whitePlayer;
    private Player blackPlayer;
    private Color currentTurn;
    private GameState gameState;

    // Constructor
    public Game() {
        board = new BoardImpl();
        currentTurn = Color.WHITE;
    }

    @Override
    public void start() {
        printWelcomeMessage();
        String mode = InputHandler.selectMode();

        if (mode.equals("1v1")) {
            whitePlayer = new HumanPlayer("White Player's", Color.WHITE);
            blackPlayer = new HumanPlayer("Black Player's", Color.BLACK);
        } else if (mode.equals("1vBot")) {
            String color = InputHandler.chooseColor();
            System.out.println("You will play as " + (color.equals("w") ? "\u001B[33mwhite\u001B[0m" : "\u001B[34mblack\u001B[0m"));
            int depth = InputHandler.selectDepth();
            whitePlayer = color.equals("w") ? new HumanPlayer("It's your", Color.WHITE) : new BotPlayer(Color.WHITE, depth);
            blackPlayer = color.equals("b") ? new HumanPlayer("It's your", Color.BLACK) : new BotPlayer(Color.BLACK, depth);
        } else if (mode.equals("BvB")) {
            int depth = InputHandler.selectDepth();
            whitePlayer = new BotPlayer(Color.WHITE, depth);
            blackPlayer = new BotPlayer(Color.BLACK, depth);
        }

        runGameLoop();
    }

    /**
     * Checks the game state, changing the state of the
     * game depending on various conditions
     */
    private void checkGameState() {
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

    @Override
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
                from = new Position(move.from().getRow(), move.from().getColumn());
                to = new Position(move.to().getRow(), move.to().getColumn());
            } else {
                System.out.println("Enter your move. [e2 e4]:");
                System.out.print("> ");
                String input = InputHandler.readLine().toLowerCase();

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
                
                Type 'help' for commands or 'exit' to quit.
                Let the game begin!
                """
        );
    }

    @Override
    public void exit() {
        System.out.println("\u001B[31mGame ended.\u001B[0m");
        System.exit(0);
    }
}
