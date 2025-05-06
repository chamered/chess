package board;
import game.PlayerTurnEnum;
import pieces.Piece;

import java.util.Map;
import java.util.Set;

public class ChessBoard implements ChessBoardInterface {
    public Piece[][] board;

    public ChessBoard(Piece[][] board){
        this.board = board;
    }
    @Override
    public Set<Piece> getPiecesOnBoard() {
        return Set.of();
    }

    @Override
    public Set<Piece> getPiecesByPlayer(PlayerTurnEnum playerTurnEnum) {
        return Set.of();
    }

    @Override
    public Map<PlayerTurnEnum, Set<Piece>> getPiecesBelongingToPlayer() {
        return Map.of();
    }

    @Override
    public Set<Piece> getPiecesByType(Piece piece) {
        return Set.of();
    }

    @Override
    public Map<Piece, Integer> getPiecesCountByType() {
        return Map.of();
    }

    @Override
    public boolean checkMate() {
        return false;
    }
}
