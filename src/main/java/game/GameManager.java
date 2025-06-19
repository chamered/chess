package game;

import board.ChessBoard;
import pieces.Piece;

public class GameManager {

    //Checking and applying moves

    private ChessBoard board;

    public GameManager(ChessBoard board) {
        this.board = board;
    }

    //Makes the move
    public boolean makeMove(Move move) {
        //TODO
        return false;
    }

    //Check validity of the move
    public boolean isMoveValid(Move move) {
        //TODO
        return false;
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

    public ChessBoard getBoard() {
        return board;
    }

    public void setBoard(ChessBoard board) {
        this.board = board;
    }
}