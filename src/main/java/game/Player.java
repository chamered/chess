package game;

import pieces.*;

import java.util.Set;

public class Player {
    private String name;
    private Color color;
    private Set<Piece> capturedPieces;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    //add a piece to the set of capturedPieces
    public void addCapturedPiece(Piece piece){
        //TODO
    }

    public Color getColor(){
        return color;
    }

    public Set<Piece> getCapturedPieces(){
        return capturedPieces;
    }

    public String getName(){
        return name;
    }
}