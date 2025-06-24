package game;

import board.Position;
import pieces.Piece;
import java.util.Objects;

public record Move(Position from, Position to) {

    @Override
    public String toString() {
        return Piece.toAlgebraic(from) + " -> " + Piece.toAlgebraic(to);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        Move move = (Move) o;
        return Objects.equals(from, move.from) &&
                Objects.equals(to, move.to);
    }

}