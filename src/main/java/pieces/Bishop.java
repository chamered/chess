package pieces;

import java.util.ArrayList;
import java.util.List;

import board.ChessBoard;
import board.Position;

public class Bishop extends Piece {

    // Constructor: set bishop value based on color
    public Bishop(PieceColor color) {
        super(color == PieceColor.WHITE ? 30 : -30, color);
    }



    @Override
    public boolean eatOtherPiece(Piece piece) {
        // A bishop can only eat pieces of the opposite color
        return piece != null && piece.color != this.color;
    }

    @Override
    public List<Position> generatePossibleMoves(ChessBoard board, Position currentPos) {
        List<Position> possibleMoves = new ArrayList<>();

        int row = currentPos.getRow();
        int col = currentPos.getCol();
        Piece[][] boardState = board.board;

        // A bishop moves diagonally in all 4 directions
        int[][] directions = {
                {-1, -1}, // up-left
                {-1, 1},  // up-right
                {1, -1},  // down-left
                {1, 1}    // down-right
        };

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            // Keep going in the same direction until we hit the edge or something
            while (isInsideBoard(newRow, newCol)) {
                Piece target = boardState[newRow][newCol];

                if (target == null) {
                    // The square is empty – valid move
                    possibleMoves.add(new Position(newRow, newCol));
                } else {
                    // There's a piece there – can we capture it?
                    if (eatOtherPiece(target)) {
                        possibleMoves.add(new Position(newRow, newCol));
                    }
                    break; // can't go past another piece
                }

                // Move one step further in the same direction
                newRow += dir[0];
                newCol += dir[1];
            }
        }

        return possibleMoves;
    }

    // Check if the given coordinates are within the 8x8 board
    private boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}
