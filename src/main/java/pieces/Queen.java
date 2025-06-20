package pieces;

import board.ChessBoard;
import board.Position;

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
    public List<String> generatePossibleMoves(ChessBoard board, Position pos) {
        return List.of();
    }
}
