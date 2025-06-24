package pieces;

import java.util.ArrayList;
import java.util.List;

import board.BoardImpl;
import board.Position;

public class Queen extends Piece {

    // Constructor: set queen value based on color
    public Queen(Color color) {
        super(color == Color.WHITE ? 90 : -90, 'Q', color);
    }

    @Override
    public Piece copy() {
        return new Queen(this.color);
    }

    /**
     * Check if this queen can capture the other piece.
     * A queen can only capture opponent's pieces.
     */
    @Override
    public boolean eatOtherPiece(Piece piece) {
        return piece != null && piece.color != this.color;
    }

    /**
     * Generate all possible moves for the queen from current position.
     * The queen can move any number of squares in any direction (horizontal, vertical, diagonal),
     * until it hits another piece or the edge of the board.
     */
    @Override
    public List<String> generatePossibleMoves(BoardImpl board, Position currentPos) {
        List<String> possibleMoves = new ArrayList<>();

        int[][] directions = {
                {-1, 0},  // up
                {1, 0},   // down
                {0, -1},  // left
                {0, 1},   // right
                {-1, -1}, // up-left
                {-1, 1},  // up-right
                {1, -1},  // down-left
                {1, 1}    // down-right
        };

        Piece[][] boardState = board.getBoard();

        for (int[] direction : directions) {
            Position pos = new Position(
                    currentPos.getRow() + direction[0],
                    currentPos.getColumn() + direction[1]
            );

            while (isInsideBoard(pos)) {
                Piece target = boardState[pos.getRow()][pos.getColumn()];

                if (target == null) {
                    possibleMoves.add(toAlgebraic(pos));
                } else {
                    if (eatOtherPiece(target)) {
                        possibleMoves.add(toAlgebraic(pos));
                    }
                    break; // Can't go beyond this piece
                }

                // Move further in the same direction
                pos.setRow(pos.getRow() + direction[0]);
                pos.setColumn(pos.getColumn() + direction[1]);
            }
        }

        return possibleMoves;
    }
}