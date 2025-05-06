package board;
import game.PlayerTurnEnum;
import pieces.Knight;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

import java.util.Map;
import java.util.Set;

public class ChessBoard implements ChessBoardInterface {
    public Piece[][] board;

    public ChessBoard(){
        board = new Piece[8][8];
    }

    @Override
    public void setupBoard() {
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                board[i][j] = new Queen(10, Piece.PieceColor.BLACK);
            }
        }
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

    public void printBoard(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                System.out.print(board[i][j].getColor() == Piece.PieceColor.BLACK ? "*" : "\u001B[37m*\u001B[0m" );
            }
            System.out.println();
        }
    }
}