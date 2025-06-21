package game;

import board.BoardImpl;
import board.Position;
import pieces.Color;
import pieces.Piece;

public class GameManager {

    private BoardImpl board;
    private Color currentTurn = Color.WHITE;

    public GameManager(BoardImpl board) {
        this.board = board;
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
    public boolean isCheck(PlayerTurnEnum turn) {
        //TODO
        return false;
    }

    //Check if it is checkmate
    public boolean isCheckmate(PlayerTurnEnum turn) {
        //TODO
        return false;
    }

    //Check for stalemate
    public boolean isStalemate(PlayerTurnEnum turn) {
        //TODO
        return false;
    }

    public BoardImpl getBoard() {
        return board;
    }

    public void setBoard(BoardImpl board) {
        this.board = board;
    }
}