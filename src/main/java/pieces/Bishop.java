package pieces;

import java.util.ArrayList;
import java.util.List;

import board.BoardImpl;
import board.Position;

public class Bishop extends Piece {

    // Constructor: set bishop value based on color
    public Bishop(Color color) {
        super(color == Color.WHITE ? 30 : -30, 'B', color);
    }

    @Override
    public Piece copy() {
        return new Bishop(this.COLOR);
    }

    @Override
    public boolean eatOtherPiece(Piece piece) {
        // A bishop can only eat pieces of the opposite color
        return piece != null && piece.COLOR != this.COLOR;
    }

    @Override
    public List<String> generatePossibleMoves(BoardImpl board, Position currentPos) {
        List<String> possibleMoves = new ArrayList<>();

        int row = currentPos.getRow();
        int col = currentPos.getColumn();
        Piece[][] boardState = board.getBoard();

        // A bishop moves diagonally in all 4 directions
        int[][] directions = {
                {-1, -1}, // up-left
                {-1, 1},  // up-right
                {1, -1},  // down-left
                {1, 1}    // down-right
        };

        for (int[] dir : directions) {
            Position pos = new Position(row + dir[0], col + dir[1]);
            int newRow = pos.getRow();
            int newCol = pos.getColumn();

            // Keep going in the same direction until we hit the edge or something
            while (isInsideBoard(pos)) {
                Piece target = boardState[newRow][newCol];

                if (target == null) {
                    // The square is empty – valid move
                    possibleMoves.add(toAlgebraic(pos));
                } else {
                    // There's a piece there – can we capture it?
                    if (eatOtherPiece(target)) {
                        possibleMoves.add(toAlgebraic(pos));
                    }
                    break; // can't go past another piece
                }

                // Move one step further in the same direction
                pos.setRow(row + dir[0]);
                pos.setColumn(col + dir[1]);
            }
        }

        return possibleMoves;
    }
}
