package pieces;

public abstract class Piece {
    public enum PieceColor {BLACK, WHITE}

    int VALUE;
    PieceColor COLOR;
    int currentCell;

    public Piece(PieceColor color) {
        this.COLOR = color;
    }

    /**
     *  Moves the piece according to it's capabilities
     */
    abstract public void move();

    /**
     * @param piece The piece that's being allegedly eaten
     * @return true iff this Piece can eat the other piece
     */
    abstract public boolean eatOtherPiece(Piece piece);
}
