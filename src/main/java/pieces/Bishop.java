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
                {-1, -1}, {-1, 1},
                {1, -1}, {1, 1}
        };

        Piece[][] boardState = board.getBoard();

        for (int[] dir : directions) {
            int row = currentPos.row() + dir[0];
            int col = currentPos.column() + dir[1];

            while (isInsideBoard(new Position(row, col))) {
                Piece target = boardState[row][col];
                Position pos = new Position(row, col);

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