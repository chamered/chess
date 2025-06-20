package pieces;


import java.util.ArrayList;
import java.util.List;

import board.ChessBoard;
import board.Move;
import board.Position;


public class Pawn extends Piece{

    public Pawn(PieceColor color) {

        super(1,color);
    }

    public boolean isWhite(){
        return this.color == PieceColor.WHITE;
    }

    @Override
    public void move(){
    // In your game you could perform the movement here
    System.out.println("Pawn moves one or two squares forward (or captures diagonally)");
    }



    @Override
    public boolean eatOtherPiece(Piece piece){
        // A pawn can only capture opposing pieces
        return piece != null && piece.color != this.color;
    }

    /**
     * Check if a move from (startX, startY) to (endX, endY) is valid on the given board
     * (This method can be useful if you want to check a single move.)
     */

    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
        // Check if target coordinates are in the playing field
        if (endX < 0 || endX >= 8 || endY < 0 || endY >= 8) {
            return false;
        }


        // Direction of movement: white up (-1), black down (+1)
        int direction = isWhite() ? -1 : 1;
        int startRow = isWhite() ? 6 : 1;// white pawns start in row 6, black in row 1


        // 1 space forward (only if free)
        if (endX == startX + direction && endY == startY && board[endX][endY] == null) {
            return true;
        }


        // 2 spaces forward (only on the first move and if both spaces are free)

        if (startX == startRow && endX == startX + 2 * direction && endY == startY &&
                board[startX + direction][startY] == null && board[endX][endY] == null) {
            return true;
        }


        // Strike diagonally (1 square diagonally forward if opponent is present)

        if (endX == startX + direction && Math.abs(endY - startY) == 1) {
            Piece target = board[endX][endY];
            if (target != null && target.color != this.color) {
                return true;
            }
        }

        // Everything else is invalid
        return false;
    }

    /**
     * Generate a list of all possible moves this pawn can make from currentPos on the given board
     */

    @Override
    public List<Move> generatePossibleMoves(ChessBoard board, Position currentPos){
        List<Move> possibleMoves= new ArrayList<>();

        int startX= currentPos.getRow(); // current row
        int startY= currentPos.getCol(); //current column

        int direction= isWhite() ? -1 : 1;
        int startRow= isWhite() ? 6: 1;

        Piece[][] boardState = board.board;

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

























