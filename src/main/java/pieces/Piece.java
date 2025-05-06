package pieces;

public abstract class Piece {
    int value;
    public Piece(int value){
        this.value = value;
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
