package pieces;


import java.util.ArrayList;
import java.util.List;

import board.ChessBoard;
import board.Position;


public class Pawn extends Piece{

    public Pawn(PieceColor color) {

        super(1,color);
    }

    public boolean isWhite(){
        return this.color == PieceColor.WHITE;
    }

    @Override
    public boolean eatOtherPiece(Piece piece){
        // A pawn can only capture opposing pieces
        return piece != null && piece.color != this.color;
    }

    /**
     * Generate a list of all possible moves this pawn can make from currentPos on the given board
     */

    @Override
    public List<String> generatePossibleMoves(ChessBoard board, int row, int col){
        List<String> possibleMoves= new ArrayList<>();

        int direction= isWhite() ? -1 : 1;
        int startRow= isWhite() ? 6: 1;

        // 1 step forward
        int oneStepX = startX + direction;
        if (isInsideBoard(oneStepX, startY) && boardState[oneStepX][startY] == null) {
            possibleMoves.add(new Move(currentPos, new Position(oneStepX, startY)));

            // 2 steps forward (only from start row and if both spaces are free)
            int twoStepX = startX + 2 * direction;
            if (startX == startRow && boardState[twoStepX][startY] == null && boardState[oneStepX][startY] == null) {
                possibleMoves.add(new Move(currentPos, new Position(twoStepX, startY)));
            }
        }

        // Diagonal capture left
        int diagLeftY = startY - 1;
        if (isInsideBoard(oneStepX, diagLeftY)) {
            Piece target = boardState[oneStepX][diagLeftY];
            if (eatOtherPiece(target)) {
                possibleMoves.add(new Move(currentPos, new Position(oneStepX, diagLeftY)));
            }
        }

        // Diagonal capture right
        int diagRightY = startY + 1;
        if (isInsideBoard(oneStepX, diagRightY)) {
            Piece target = boardState[oneStepX][diagRightY];
            if (eatOtherPiece(target)) {
                possibleMoves.add(new Move(currentPos, new Position(oneStepX, diagRightY)));
            }
        }

        return possibleMoves;
    }

    // Helper method to make sure coordinates are on the board (0 to 7)
    private boolean isInsideBoard(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }
}

























