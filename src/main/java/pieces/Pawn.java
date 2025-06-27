package pieces;


import java.util.ArrayList;
import java.util.List;

import board.BoardImpl;
import board.Position;
import game.Move;


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
    public List<String> generatePossibleMoves(BoardImpl b, Position pos) {
        List<String> moves = new ArrayList<>();
    int direction = (color == Color.WHITE) ? -1 : 1;
    int startRow = (color == Color.WHITE) ? 6 : 1;

        int row = pos.row();
        int col = pos.column();

        //forward 1
        Position oneStep = new Position(row + direction, col);
        if (isInsideBoard(oneStep) && b.getPieceAt(oneStep) == null) {
            maybeAddPromotion(moves, oneStep);

            /* forward 2 (only if 1 empty and on start rank) */
            if ((row == 6 && direction == -1) || (row == 1 && direction == 1)) {
                Position two = new Position(row + 2 * direction, col);
                if (b.getPieceAt(two) == null) moves.add(toAlgebraic(two));
            }
        }

        //captures (x-left / x-right)
        for (int dc : new int[]{-1, 1}) {
            Position diag = new Position(row + direction, col + dc);
            if (!isInsideBoard(diag)) continue;

            Piece target = b.getPieceAt(diag);
            if (target != null && eatOtherPiece(target)) {
                maybeAddPromotion(moves, diag);
            }
        }
        return moves;
    }

    private void maybeAddPromotion(List<String> moves, Position p) {
        if (p.row() == 0 || p.row() == 7)
            moves.add(toAlgebraic(p) + "=Q");
        else
            moves.add(toAlgebraic(p));
    }
}








