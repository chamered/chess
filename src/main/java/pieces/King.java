package pieces;

import java.util.List;

public class King extends Piece {

    public King(PieceColor color) {
        super(color);
        VALUE = color == PieceColor.WHITE ? 900 : -900;
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
