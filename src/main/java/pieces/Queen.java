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
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        Piece[][] boardState = board.getBoard();

        for (int[] dir : directions) {
            int row = currentPos.getRow() + dir[0];
            int col = currentPos.getColumn() + dir[1];

            while (isInsideBoard(new Position(row, col))) {
                Position pos = new Position(row, col);
                Piece target = boardState[row][col];

                if (target == null) {
                    possibleMoves.add(toAlgebraic(pos));
                } else {
                    if (eatOtherPiece(target)) {
                        possibleMoves.add(toAlgebraic(pos));
                    }
                    break;
                }

                row += dir[0];
                col += dir[1];
            }
        }

        return possibleMoves;
    }
}