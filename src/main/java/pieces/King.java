package pieces;

import board.ChessBoard;
import board.Position;
import game.Move;

import java.util.List;

public class King extends Piece{

    public King(int row, int col, Color color) {
            super(row, col, color);
        }

    @Override
    public List<String> generatePossibleMoves(ChessBoard board, Position pos) {
        return List.of();
    }

    @Override
    public boolean eatOtherPiece(Piece piece) {
        return false;
    }

}







