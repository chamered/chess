package pieces;


import java.util.ArrayList;
import java.util.List;

import board.BoardImpl;
import board.Position;


public class Pawn extends Piece{

    public Pawn(Color color) {
        super(color == Color.WHITE ? 10 : -10, 'p', color);
    }

    public boolean isWhite(){
        return this.color == Color.WHITE;
    }

    @Override
    public Piece copy() {
        return new Pawn(this.color);
    }

    @Override
    public boolean eatOtherPiece(Piece piece){
        // A pawn can only capture opposing pieces
        return piece != null && piece.color != this.color;
    }

    /**
     * Generate a list of all possible moves this pawn can make from currentPos on the given board
     */
    @Override
    public List<String> generatePossibleMoves(BoardImpl board, Position currentPos) {
        List<String> possibleMoves = new ArrayList<>();

        int row = currentPos.getRow();
        int col = currentPos.getColumn();
        Piece[][] boardState = board.getBoard();

        int direction = isWhite() ? -1 : 1;
        int startRow = isWhite() ? 6 : 1;

        // One step forward
        Position oneStepRowPos = new Position(row + direction, col);
        if (isInsideBoard(oneStepRowPos) && boardState[oneStepRowPos.getRow()][oneStepRowPos.getColumn()] == null) {
            possibleMoves.add(toAlgebraic(oneStepRowPos));

            // Two steps forward from starting row
            Position twoStepRowPos = new Position(row + 2 * direction, col);
            if (row == startRow && boardState[twoStepRowPos.getRow()][twoStepRowPos.getColumn()] == null) {
                possibleMoves.add(toAlgebraic(twoStepRowPos));
            }
        }

        // Diagonal captures (left and right)
        int[] diagOffsets = {-1, 1};
        for (int dc : diagOffsets) {
            Position pos = new Position( row + direction, col + dc);

            if (isInsideBoard(pos)) {
                Piece target = boardState[pos.getRow()][pos.getColumn()];
                if (target != null && eatOtherPiece(target)) {
                    possibleMoves.add(toAlgebraic(pos));
                }
            }
        }

        return possibleMoves;
    }
}









