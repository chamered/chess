package pieces;

import java.util.List;

public abstract class Piece {
    public enum PieceColor {BLACK, WHITE}

    protected int VALUE;
    protected PieceColor COLOR;
    protected String currentCell;

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

    /**
     * Generate all the possible moves that this piece can do.
     * @return a list with all the possible moves
     */
    abstract List<String> generatePossibleMoves();

    public PieceColor getColor(){
        return COLOR;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "VALUE=" + VALUE +
                ", color=" + COLOR +
                '}';
    }
}
