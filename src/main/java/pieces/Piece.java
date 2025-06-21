package pieces;


import java.util.List;

import board.BoardImpl;
import board.Position; // This is a class that tells where a piece is on the board


public abstract class Piece {
    public enum Color {BLACK, WHITE}

    private final int VALUE;
    private final char SYMBOL;
    final Color COLOR;

    public Piece(int value, char symbol, Color color){
        this.VALUE = value;
        this.SYMBOL = symbol;
        this.COLOR = color;
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
     * @param currentPos Where this piece is right now on the board
     * @return A list of all possible moves this piece can make
     */
    abstract public List<String> generatePossibleMoves(BoardImpl board, Position currentPos);

    public static String toAlgebraic (Position pos) {
        int row = pos.getRow();
        int col = pos.getColumn();

        if(row <0 || row >7 || col < 0 || col > 7 ){
            throw new IllegalArgumentException(
                    "Invalid board coordinates: row =" + row + ", col =" + col + ". Must be in range 0-7."
            );

        }
        char file = (char)('a' + col);
        int rank = 8 - row;

        return "" + file +rank;
    }

    public static int[] fromAlgebraic(String notation) {
        if (notation == null || notation.length() != 2) {
            throw new IllegalArgumentException("Invalid algebraic notation: " + notation);
        }

        char file = notation.charAt(0);       // 'a' to 'h'
        int rank = Character.getNumericValue(notation.charAt(1)); // 1 to 8

        int col = file - 'a';
        int row = 8 - rank;

        if (row < 0 || row > 7 || col < 0 || col > 7) {
            throw new IllegalArgumentException("Algebraic notation out of bounds: " + notation);
        }

        return new int[]{row, col};
    }

    /**
    * Check if given coordinates are inside the chessboard (0-7)
    */
    public static boolean isInsideBoard(Position pos) {
        int row = pos.getRow();
        int col = pos.getColumn();
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public char getSYMBOL() {
        return SYMBOL;
    }
}

