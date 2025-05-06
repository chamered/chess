package pieces;

import java.util.List;

public class Pawn extends Piece {

    public Pawn(PieceColor color) {
        super(color);
        VALUE = color == PieceColor.WHITE ? 10 : -10;
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
