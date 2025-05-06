package pieces;

public class Queen extends Piece {

    public Queen(PieceColor color) {
        super(color);
        VALUE = color == PieceColor.WHITE ? 90 : -90;
    }

    @Override
    public void move() {

    }

    @Override
    public boolean eatOtherPiece(Piece piece) {
        return false;
    }
}
