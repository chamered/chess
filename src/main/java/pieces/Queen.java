package pieces;

import java.util.ArrayList;
import java.util.List;

import board.ChessBoard;
import board.Position;

public class Queen extends Piece {

    // Constructor: set queen value based on color
    public Queen(PieceColor color) {
        super(color == PieceColor.WHITE ? 90 : -90, color);
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
    public List<String> generatePossibleMoves(ChessBoard board, Position currentPos) {
        List<String> possibleMoves = new ArrayList<>();

        int[][] directions = {
                {-1, 0},  // up
                {1, 0},   // down
                {0, -1},  // left
                {0, 1},   // right
                {-1, -1}, // up-left diagonal
                {-1, 1},  // up-right diagonal
                {1, -1},  // down-left diagonal
                {1, 1}    // down-right diagonal
        };

        int startRow = currentPos.getRow();
        int startCol = currentPos.getCol();
        Piece[][] boardState = board.board;

        for (int[] direction : directions) {
            int row = startRow + direction[0];
            int col = startCol + direction[1];

            // Move step by step in one direction
            while (isInsideBoard(row, col)) {
                Piece target = boardState[row][col];

                if (target == null) {
                    possibleMoves.add(toAlgebraic(row, col));
                } else {
                    if (eatOtherPiece(target)) {
                        possibleMoves.add(toAlgebraic(row, col));
                    }
                    break; // Stop at first piece
                }

                row += direction[0];
                col += direction[1];
            }
        }
        return possibleMoves;
    }
}
