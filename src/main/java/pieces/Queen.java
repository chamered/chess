package pieces;

import board.ChessBoard;

import java.util.List;

public class Queen extends Piece {

    public Queen(PieceColor color) {
        super(color);
        VALUE = color == PieceColor.WHITE ? 90 : -90;
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
