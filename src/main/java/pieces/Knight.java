package pieces;

import java.util.List;

public class Knight extends Piece {

    public Knight(PieceColor color) {
        super(color);
        VALUE = color == PieceColor.WHITE ? 30 : -30;
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
