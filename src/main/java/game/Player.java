package game;

import pieces.*;

import java.util.Set;

public abstract class Player {
    private final String name;
    private final Color color;
    private Set<Piece> capturedPieces;

    // Constructor
    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    //add a piece to the set of capturedPieces
    public void addCapturedPiece(Piece piece){
        //TODO
    }

    /**
     * Returns the color of the player.
     * @return the player's color
     */
    public Color getColor(){
        return color;
    }

    /**
     * Returns all the captured pieces by this player.
     * @return the captured pieces
     */
    public Set<Piece> getCapturedPieces(){
        return capturedPieces;
    }

    /**
     * Returns the name of the player.
     * @return the player's name
     */
    public String getName(){
        return name;
    }
}