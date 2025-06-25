package players;

import pieces.Color;
import pieces.Piece;

import java.util.Set;

public interface PlayerInterface {
    /**
     * Adds the captured piece to the list.
     * @param piece captured piece
     */
    void addCapturedPiece(Piece piece);

    /**
     * Getter method
     * @return the player's color
     */
    Color getColor();

    /**
     * Returns a set with all the pieces captured by this player.
     * @return a set with all captured pieces.
     */
    Set<Piece> getCapturedPieces();

    /**
     * Returns the Player's name
     * @return player's name
     */
    String getName();
}
