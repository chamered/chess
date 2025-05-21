package pieces;

import java.util.List;

public class Rook extends Piece {

    public Rook(PieceColor color) {
        super(color);
        VALUE = color == PieceColor.WHITE ? 50 : -50;
    }

    @Override
    public void move() {

    }

    @Override
    public boolean eatOtherPiece(Piece piece) {
        return false;
    }

    @Override
    List<String> generatePossibleMoves() {
        return List.of();
    }
}
