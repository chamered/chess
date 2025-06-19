package game;

import pieces.Piece;

import java.util.Set;

public class Player {
    private String name;
    private PlayerTurnEnum turn;
    private Set<Piece> capturedPieces;

    //add a piece to the set of capturedPieces
    public void addCapturedPiece(Piece piece){
        //TODO
    }

    public PlayerTurnEnum getTurn(){
        return turn;
    }

    public Set<Piece> getCapturedPieces(){
        return capturedPieces;
    }

    public String getName(){
        return name;
    }
}
