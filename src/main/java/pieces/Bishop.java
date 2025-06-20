package pieces;

import board.ChessBoard;

import java.util.List;

public class Bishop extends Piece {

    public Bishop(PieceColor color) {
        super(color);
        VALUE = color == PieceColor.WHITE ? 30 : -30;
    }

    @Override
    public boolean eatOtherPiece(Piece piece) {
        return false;
    }

    @Override
    List<String> generatePossibleMoves(ChessBoard board, int row, int col) {
        return List.of();
    }
}
