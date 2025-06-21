package pieces;

import java.util.ArrayList;
import java.util.List;

import board.ChessBoard;
import board.Position;

public class Rook extends Piece {

    // Constructor: set rook value based on color
    public Rook(PieceColor color) {
        super(color == PieceColor.WHITE ? 50 : -50, color);
    }

    /**
     * Check if this rook can capture the other piece.
     * A rook can only capture opponent's pieces.
     */
    @Override
    public boolean eatOtherPiece(Piece piece) {
        return piece != null && piece.color != this.color;
    }

    /**
     * Generate all possible moves for the rook from current position.
     * The rook can move any number of squares vertically or horizontally,
     * until it hits another piece or the edge of the board.
     */
    @Override
    public List<Move> generatePossibleMoves(ChessBoard board, Position currentPos) {
        List<Move> possibleMoves = new ArrayList<>();

        // Rook moves only in 4 directions: up, down, left, right
        int[][] directions = {
                {-1, 0},  // up
                {1, 0},   // down
                {0, -1},  // left
                {0, 1}    // right
        };

        for (int[] direction : directions) {
            int row = currentPos.getRow();
            int col = currentPos.getCol();

            // Move step by step in one direction
            while (true) {
                row += direction[0];
                col += direction[1];

                // Check if new position is inside the board
                if (row < 0 || row > 7 || col < 0 || col > 7) {
                    break; // outside board
                }

                Piece target = board.getPieceAt(row, col); // Assuming this method exists

                if (target == null) {
                    // Square is free, add move
                    possibleMoves.add(new Move(currentPos, new Position(row, col)));
                } else {
                    // Square occupied: can capture only if opponent piece
                    if (eatOtherPiece(target)) {
                        possibleMoves.add(new Move(currentPos, new Position(row, col)));
                    }
                    break; // Can't jump over pieces
                }
            }
        }

        return possibleMoves;
    }
}
