package pieces;

import java.util.ArrayList;
import java.util.List;

import board.BoardImpl;
import board.Position;

public class Knight extends Piece {

    // Constructor: set knight value depending on color
    public Knight(Color color) {
        super(color == Color.WHITE ? 30 : -30, 'N', color);
    }


    @Override
    public Piece copy() {
        return new Knight(this.color);
    }

    @Override
    public boolean eatOtherPiece(Piece piece) {
        // Knight can only capture opponent's pieces (not null and different color)
        return piece != null && piece.color != this.color;
    }

    @Override
    public List<String> generatePossibleMoves(BoardImpl board, Position currentPos) {
        List<String> possibleMoves = new ArrayList<>();

        int[][] moveOffsets = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        int currentRow = currentPos.getRow();
        int currentCol = currentPos.getColumn();

        // Check all possible Knight moves from the current position
        for (int[] offset : moveOffsets) {
            Position pos = new Position(currentRow + offset[0], currentCol + offset[1]);

            // Check if new position is inside the board
            if (isInsideBoard(pos)) {
                Piece targetPiece = board.getPieceAt(pos);

                // Knight can move if target square is empty or contains opponent's piece
                if (targetPiece == null || eatOtherPiece(targetPiece)) {
                    possibleMoves.add(toAlgebraic(pos));
                }
            }
        }

        return possibleMoves;
    }

    @Override
    public Piece clone() {
        return new Knight(this.color);
    }
}
