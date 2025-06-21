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
     * @param currentPos Where this piece is right now on the board
     * @return A list of all possible moves this piece can make
     */
    abstract public List<String> generatePossibleMoves(ChessBoard board, Position currentPos);

    public static String toAlgebraic (int row, int col){
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
        public static boolean isInsideBoard(int row, int col){
            return row >= 0 && row < 8 && col >= 0 && col < 8;
        }


    }

