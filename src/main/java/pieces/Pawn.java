package pieces;

public class Pawn extends Piece {

    public Pawn(PieceColor color) {
        super(color);
        VALUE = color == PieceColor.WHITE ? 10 : -10;
        currentCell = color == PieceColor.WHITE ? 10 : 60;
    }

    @Override
    public void move() {

    }

    @Override
    public boolean eatOtherPiece(Piece piece) {
        return false;
    }
}
