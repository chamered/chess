package pieces;

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
}



