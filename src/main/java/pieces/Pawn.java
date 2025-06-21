package pieces;


import java.util.ArrayList;
import java.util.List;

import board.ChessBoard;
import board.Position;


public class Pawn extends Piece{

    public Pawn(PieceColor color) {

        super(color == PieceColor.WHITE ? 10 : -10, color);
    }

    public boolean isWhite(){
        return this.color == PieceColor.WHITE;
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
    public List<String> generatePossibleMoves(ChessBoard board, Position currentPos) {
        List<String> possibleMoves = new ArrayList<>();

        int row = currentPos.getRow();
        int col = currentPos.getCol();
        Piece[][] boardState = board.board;

        int direction = isWhite() ? -1 : 1;
        int startRow = isWhite() ? 6 : 1;

        // One step forward
        int oneStepRow = row + direction;
        if (isInsideBoard(oneStepRow, col) && boardState[oneStepRow][col] == null) {
            possibleMoves.add(toAlgebraic(oneStepRow, col));

            // Two steps forward from starting row
            int twoStepRow = row + 2 * direction;
            if (row == startRow && boardState[twoStepRow][col] == null) {
                possibleMoves.add(toAlgebraic(twoStepRow, col));
            }
        }

        // Diagonal captures (left and right)
        int[] diagOffsets = {-1, 1};
        for (int dc : diagOffsets) {
            int newCol = col + dc;
            int newRow = row + direction;

            if (isInsideBoard(newRow, newCol)) {
                Piece target = boardState[newRow][newCol];
                if (target != null && eatOtherPiece(target)) {
                    possibleMoves.add(toAlgebraic(newRow, newCol));
                }
            }
        }

        return possibleMoves;
    }
}









