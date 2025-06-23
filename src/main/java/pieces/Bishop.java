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
        return new Bishop(this.color);
    }

    @Override
    public boolean eatOtherPiece(Piece piece) {
        // A bishop can only eat pieces of the opposite color
        return piece != null && piece.color != this.color;
    }

    @Override
    public List<String> generatePossibleMoves(BoardImpl board, Position currentPos) {
        List<String> possibleMoves = new ArrayList<>();

        int[][] directions = {
                {-1, -1}, // up-left
                {-1, 1},  // up-right
                {1, -1},  // down-left
                {1, 1}    // down-right
        };

        Piece[][] boardState = board.getBoard();

        for (int[] dir : directions) {
            Position pos = new Position(currentPos.getRow() + dir[0], currentPos.getColumn() + dir[1]);

            while (isInsideBoard(pos)) {
                int newRow = pos.getRow();
                int newCol = pos.getColumn();

                Piece target = boardState[newRow][newCol];

                if (target == null) {
                    possibleMoves.add(toAlgebraic(pos));
                } else {
                    if (eatOtherPiece(target)) {
                        possibleMoves.add(toAlgebraic(pos));
                    }
                    break; // stop scanning in this direction
                }

                pos.setRow(pos.getRow() + dir[0]);
                pos.setColumn(pos.getColumn() + dir[1]);
            }
        }

        return possibleMoves;
    }

    @Override
    public Piece clone() {
        return new Bishop(this.color);
    }
}