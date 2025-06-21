package pieces;

import java.util.ArrayList;
import java.util.List;

import board.BoardImpl;
import board.Position;

public class King extends Piece {

    public King(Color color) {
        super(color == Color.WHITE ? 1000 : -1000, 'K', color); // Kings are usually very high value (1000+)
    }


    @Override
    public Piece copy() {
        return new King(this.COLOR);
    }

    @Override
    public boolean eatOtherPiece(Piece piece) {
        // The king can only capture enemy pieces
        return piece != null && piece.COLOR != this.COLOR;
    }

    @Override
    public List<String> generatePossibleMoves(BoardImpl board, Position currentPos) {
        List<String> possibleMoves = new ArrayList<>();

        int row = currentPos.getRow();
        int col = currentPos.getColumn();
        Piece[][] boardState = board.getBoard();

        // King can move to 8 surrounding squares (one step in each direction)
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                if (rowOffset == 0 && colOffset == 0) continue; // skip the current position

                Position pos = new Position(row + rowOffset, col + colOffset);

                if (isInsideBoard(pos)) {
                    Piece target = boardState[pos.getRow()][pos.getColumn()];

                    if (target == null || eatOtherPiece(target)) {
                        possibleMoves.add(toAlgebraic(pos));
                    }
                }
            }
        }

        return possibleMoves;
    }
}
