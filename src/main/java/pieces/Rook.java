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
        return new Rook(this.color);
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
                {-1, 0},  // up
                {1, 0},   // down
                {0, -1},  // left
                {0, 1}    // right
        };

        Piece[][] boardState = board.getBoard();

        for (int[] direction : directions) {
            Position pos = new Position(currentPos.getRow() + direction[0], currentPos.getColumn() + direction[1]);

            while (isInsideBoard(pos)) {
                Piece target = boardState[pos.getRow()][pos.getColumn()];

                if (target == null) {
                    possibleMoves.add(toAlgebraic(pos));
                } else {
                    if (eatOtherPiece(target)) {
                        possibleMoves.add(toAlgebraic(pos));
                    }
                    break;
                }

                pos.setRow(pos.getRow() + direction[0]);
                pos.setColumn(pos.getColumn() + direction[1]);
            }
        }
        return possibleMoves;
    }

    @Override
    public Piece clone() {
        return new Rook(this.color);
    }
}
