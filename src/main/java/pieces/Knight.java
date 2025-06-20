package pieces;

import board.ChessBoard;
import board.Position;

import java.util.List;

public class Knight extends Piece {

    public Knight(PieceColor color) {
        super(color);
        VALUE = color == PieceColor.WHITE ? 30 : -30;
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
