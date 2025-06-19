package game;

import board.ChessBoard;
import pieces.Piece;

import java.util.List;

public class RulesEngine {

    //Legality validation

    //Check if a move is legal
    public boolean isLegalMove(ChessBoard board, Move move, PlayerTurnEnum turn) {
        //TODO
        return false;
    }

    //Check if the move is going to cause a self check (illegal)
    public boolean wouldCauseSelfCheck(ChessBoard board, Move move, PlayerTurnEnum turn) {
        //TODO
        return false;
    }

    //Returns a list of valid moves (not illegal)
    public List<Move> getAllValidMoves(ChessBoard board, PlayerTurnEnum turn) {
        //TODO
        return List.of();
    }
}
