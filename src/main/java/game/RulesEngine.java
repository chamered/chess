package game;

import board.BoardImpl;
import pieces.Color;

import java.util.List;

public class RulesEngine {

    //Legality validation

    //Check if a move is legal
    public boolean isLegalMove(BoardImpl board, Move move, Color color) {
        //TODO
        return false;
    }

    //Check if the move is going to cause a self check (illegal)
    public boolean wouldCauseSelfCheck(BoardImpl board, Move move, Color color) {
        //TODO
        return false;
    }

    //Returns a list of valid moves (not illegal)
    public List<Move> getAllValidMoves(BoardImpl board, Color color) {
        //TODO
        return List.of();
    }
}
