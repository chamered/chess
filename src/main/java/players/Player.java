package players;

import org.jetbrains.annotations.ApiStatus;
import pieces.*;

import java.util.HashSet;
import java.util.Set;

public abstract class Player implements PlayerInterface {
    private final String name;
    private final Color color;
    private final Set<Piece> capturedPieces = new HashSet<>();

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public void addCapturedPiece(Piece piece){
        capturedPieces.add(piece);
    }

    @Override
    public Color getColor(){
        return color;
    }

    @Override
    public Set<Piece> getCapturedPieces(){
        return capturedPieces;
    }

    @Override
    public String getName(){
        return name;
    }
}