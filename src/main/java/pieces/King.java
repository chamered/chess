package pieces;

import java.util.ArrayList;
import java.util.List;

import board.ChessBoard;
import board.Position;

public class King extends Piece {

    public King(PieceColor color) {
        super(color == PieceColor.WHITE ? 1000 : -1000, color); // Kings are usually very high value (1000+)
    }


    @Override
    public boolean eatOtherPiece(Piece piece) {
        // The king can only capture enemy pieces
        return piece != null && piece.color != this.color;
    }

    @Override
    public List<Position> generatePossibleMoves(ChessBoard board, Position currentPos) {
        List<Position> possibleMoves = new ArrayList<>();

        int row = currentPos.getRow();
        int col = currentPos.getCol();
        Piece[][] boardState = board.board;

        // King can move to 8 surrounding squares (one step in each direction)
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                if (rowOffset == 0 && colOffset == 0) continue; // skip the current position

                int newRow = row + rowOffset;
                int newCol = col + colOffset;

                if (isInsideBoard(newRow, newCol)) {
                    Piece target = boardState[newRow][newCol];
                    if (target == null || eatOtherPiece(target)) {
                        possibleMoves.add(new Position(newRow, newCol));
                    }
                }
            }
        }

        return possibleMoves;
    }

    // Helper method to check if a square is on the board
    private boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}
