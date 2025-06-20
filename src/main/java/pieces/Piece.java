package pieces;


import java.util.List;

import board.ChessBoard;
import game.Move;     // This is a class that holds information about a move
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
     * @param piece The piece that's being allegedly eaten
     * @return true iff this Piece can eat the other piece
     */
    abstract public boolean eatOtherPiece(Piece piece);

    /**
     * Find all possible moves this piece can make from its current position
     *
     * @param board The current game board (so we know which squares are free or taken)
     * @param row The row position of the piece
     * @param col The column position of the Piece
     * @return A list of all possible moves this piece can make
     */
    abstract public List<Move> generatePossibleMoves(ChessBoard board, int row, int col);

    public static String toAlgebraic (int row, int col){
        if(row <0 || row >7 || col < 0 || col > 7 ){
            throw new IllegalArgumentException(
                    "Invalid board coordinates: row =" + row + ", col =" + col + ". Must be in range 0-7."
            );
        }
        char file = (char)('a' + col);
        int rank = 8 - row;

        return"" +file +rank;
    }

    public static int[] fromAlgebraic(String notation) {
        if (notation == null || notation.length() != 2) {
            throw new IllegalArgumentException("Invalid algebraic notation: " + notation);
        }

        char file = notation.charAt(0);  // a to h
        char rankChar = notation.charAt(1); // 1 to 8

        if (file < 'a' || file > 'h' || rankChar < '1' || rankChar > '8') {
            throw new IllegalArgumentException("Algebraic notation out of bounds: " + notation);
        }

        int col = file - 'a';           // 'a' → 0, ..., 'h' → 7
        int row = 8 - Character.getNumericValue(rankChar); // '8' → 0, ..., '1' → 7

        return new int[]{row, col};
    }

}

