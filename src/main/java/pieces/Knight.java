package pieces;

import java.util.ArrayList;
import java.util.List;

import board.ChessBoard;
import board.Position;

public class Knight extends Piece {

    // Constructor: set knight value depending on color
    public Knight(PieceColor color) {
        super(color == PieceColor.WHITE ? 30 : -30, color);
    }



    @Override
    public boolean eatOtherPiece(Piece piece) {
        // Knight can only capture opponent's pieces (not null and different color)
        return piece != null && piece.color != this.color;
    }

    @Override
    public List<String> generatePossibleMoves(ChessBoard board, Position currentPos) {
        List<String> possibleMoves = new ArrayList<>();

        int[][] moveOffsets = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        int currentRow = currentPos.getRow();
        int currentCol = currentPos.getCol();

        // Check all possible Knight moves from the current position
        for (int[] offset : moveOffsets) {
            int newRow = currentRow + offset[0];
            int newCol = currentCol + offset[1];

            // Check if new position is inside the board
            if (isInsideBoard(newRow, newCol)) {
                Piece targetPiece = board.getPieceAt(newRow, newCol);

                // Knight can move if target square is empty or contains opponent's piece
                if (targetPiece == null || eatOtherPiece(targetPiece)) {
                    possibleMoves.add(toAlgebraic(newRow, newCol));
                }
            }
        }

        return possibleMoves;
    }
}
