package pieces;


import java.util.List;

import board.ChessBoard;
import board.Move;     // This is a class that holds information about a move
import board.Position; // This is a class that tells where a piece is on the board


public abstract class Piece {
    public enum PieceColor {BLACK, WHITE}

    int value;
    PieceColor color;

    public Piece(int value, PieceColor color){
        this.value = value;
        this.color = color;
    }

    /**
     *  Moves the piece according to it's capabilities
     */
    abstract public void move();

    /**
     * @param piece The piece that's being allegedly eaten
     * @return true iff this Piece can eat the other piece
     */
    abstract public boolean eatOtherPiece(Piece piece);

/**
 * Find all possible moves this piece can make from its current position
 *
 * @param board The current game board (so we know which squares are free or taken)
 * @param currentPos Where this piece is right now on the board
 * @return A list of all possible moves this piece can make
 */
abstract public List<Move> generatePossibleMoves(ChessBoard board, Position currentPos);
}
