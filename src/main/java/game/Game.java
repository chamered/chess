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
    private GameStateEnum gameState;

    public Game() {
        board = new BoardImpl();
        currentTurn = Color.WHITE;
    }

    //Initializes board and players
    public void start() {
        printWelcomeMessage();

        //board.printBoard();
    }

    //Check, Checkmate, etc.
    private void checkGameState() {
        //TODO
    }

    //Makes the move
    public boolean movePiece(Position from, Position to) {
        Piece piece = board.getPieceAt(from);

        if (piece == null || piece.getCOLOR() != currentTurn) {
            return false;
        }

        if (!isMoveValid(from, to)) {
            System.out.println("Move is invalid.");
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

    public void printWelcomeMessage() {
        System.out.println(
                "=======================\n" +
                "  JAVA TERMINAL CHESS  \n" +
                "=======================\n" +
                "Welcome to the game of Chess!\n" +
                "\nWhite always starts.\n" +
                "To make a move, type coordinates in the format: e2 e4\n" +
                "\nType 'help' for commands or 'exit' to quit.\n" +
                "Let the game begin!"
        );
    }
}
