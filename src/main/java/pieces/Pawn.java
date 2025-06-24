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
        List<String> moves = new ArrayList<>();

        int direction = (color == Color.WHITE) ? -1 : 1;
        int startRow = (color == Color.WHITE) ? 6 : 1;
        int row = currentPos.getRow();
        int col = currentPos.getColumn();

        // One square forward
        Position oneStep = new Position(row + direction, col);
        if (isInsideBoard(oneStep) && board.getPieceAt(oneStep) == null) {
            moves.add(toAlgebraic(oneStep));

            // Two squares forward from starting position
            if (row == startRow) {
                Position twoStep = new Position(row + 2 * direction, col);
                if (isInsideBoard(twoStep) && board.getPieceAt(twoStep) == null) {
                    moves.add(toAlgebraic(twoStep));
                }
            }
        }

        // Capture diagonally
        for (int dCol : new int[]{-1, 1}) {
            Position diag = new Position(row + direction, col + dCol);
            if (isInsideBoard(diag)) {
                Piece target = board.getPieceAt(diag);
                if (eatOtherPiece(target)) {
                    moves.add(toAlgebraic(diag));
                }
            }
        }

        // En Passant - pseudo valid, actual check is done in Game.java
        for (int dCol : new int[]{-1, 1}) {
            int newCol = col + dCol;
            Position side = new Position(row, newCol);
            if (isInsideBoard(side)) {
                Piece adjacent = board.getPieceAt(side);
                if (adjacent instanceof Pawn && adjacent.getColor() != this.color) {
                    Position enPassantTarget = new Position(row + direction, newCol);
                    moves.add(toAlgebraic(enPassantTarget));
                }
            }
        }

        return moves;
    }
}










