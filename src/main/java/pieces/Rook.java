package pieces;

import java.util.ArrayList;
import java.util.List;

import board.BoardImpl;
import board.Position;

public class Rook extends Piece {

    // Constructor: set rook value based on color
    public Rook(Color color) {
        super(color == Color.WHITE ? 50 : -50, 'R', color);
    }

    @Override
    public Piece copy() {
        Rook copy = new Rook(this.color);
        return copy;
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
    public List<String> generatePossibleMoves(BoardImpl board, Position currentPos) {
        List<String> possibleMoves = new ArrayList<>();
        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };

        Piece[][] boardState = board.getBoard();

        for (int[] dir : directions) {
            int row = currentPos.row() + dir[0];
            int col = currentPos.column() + dir[1];

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