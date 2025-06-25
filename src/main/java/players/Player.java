package players;

import org.jetbrains.annotations.ApiStatus;
import pieces.*;

import java.util.HashSet;
import java.util.Set;

public abstract class Player {
    private final String name;
    private final Color color;
    private final Set<Piece> capturedPieces = new HashSet<>();

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    /**
     * Adds the captured piece to the list.
     * @param piece captured piece
     */
    public void addCapturedPiece(Piece piece){
        capturedPieces.add(piece);
    }

    /**
     * Getter method
     * @return the player's color
     */
    public Color getColor(){
        return color;
    }

    /**
     * Returns a set with all the pieces captured by this player.
     * @return a set with all captured pieces.
     */
    public Set<Piece> getCapturedPieces(){
        return capturedPieces;
    }

    /**
     * Returns the Player's name
     * @return player's name
     */
    public String getName(){
        return name;
    }
}