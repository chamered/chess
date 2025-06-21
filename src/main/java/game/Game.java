package game;

import board.BoardImpl;
import board.Position;
import pieces.Color;
import pieces.Piece;

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
        //TODO
        return false;
    }

    //Changes the turn
    public void switchPlayer() {
        currentTurn = (currentTurn == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    //Checks if the king is in check
    public boolean isInCheck(Color color) {
        //TODO
        return false;
    }

    //Check if it is checkmate
    public boolean isCheckmate(Color color) {
        //TODO
        return false;
    }

    //Check for stalemate
    public boolean isStalemate(Color color) {
        //TODO
        return false;
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
