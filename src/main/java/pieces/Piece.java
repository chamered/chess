package pieces;


import java.util.List;

import board.BoardImpl;
import board.Position; // This is a class that tells where a piece is on the board


public abstract class Piece {
    private final int value;
    private final char symbol;
    protected final Color color;

    public Piece(int value, char symbol, Color color){
        this.value = value;
        this.symbol = symbol;
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
     * @param currentPos Where this piece is right now on the board
     * @return A list of all possible moves this piece can make
     */
    abstract public List<String> generatePossibleMoves(BoardImpl board, Position currentPos);

    /**
     * Converts row and column to a string representation.
     * @param pos - Position of the cell to convert
     * @return a String pair representing the coordinates of a cell (e.g (e2, e4)).
     */
    public static String toAlgebraic(Position pos) {
        int row = pos.row();
        int col = pos.column();

        if(row <0 || row >7 || col < 0 || col > 7 ){
            throw new IllegalArgumentException(
                    "Invalid board coordinates: row =" + row + ", col =" + col + ". Must be in range 0-7."
            );

        }
        char file = (char)('a' + col);
        int rank = 8 - row;

        return "" + file +rank;
    }

    /**
     * {@link #toAlgebraic(Position)}
     * @param notation String representation of the coordinates
     * @return the Position
     */
    public static Position fromAlgebraic(String notation) {
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

        return new Position(row, col);
    }

    /**
     * Check if given coordinates are inside the chessboard (0-7)
     */
    public static boolean isInsideBoard(Position pos) {
        int row = pos.row();
        int col = pos.column();
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public char getSymbol() {
        return symbol;
    }

    public Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public String getType(){
        return this.getClass().getName().split("\\.")[1];
    }

    /**
     * @return a copy of the piece
     */
    public abstract Piece copy();
}
