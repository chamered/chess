package pieces;

import board.ChessBoard;
import board.Position;

import java.util.List;

public class Rook extends Piece {

    public Rook(PieceColor color) {
        super(color);
        VALUE = color == PieceColor.WHITE ? 50 : -50;
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
