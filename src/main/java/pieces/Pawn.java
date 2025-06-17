package pieces;

public class Pawn extends Piece{
    public Pawn(boolean isWhite) {

        super(isWhite);
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
        // Check if target coordinates are in the playing field
        if (endX < 0 || endX >= 8 || endY < 0 || endY >= 8) {
            return false;
        }



        // Direction of movement: white up (-1), black down (+1)
        int direction = isWhite ? -1 : 1;
        int startRow = isWhite ? 6 : 1;// white pawns start in row 6, black in row 1

        // Normal movement: one square straight forward
        if (startX + direction == endX && startY == endY && board[endX][endY] == null) {
            return true;
        }

        // First move: two spaces forward, only if both spaces are empty
        if (startX == startRow && endX == startX + 2 * direction && startY == endY
                && board[startX + direction][startY] == null && board[endX][endY] == null) {
            return true;
        }

        // Capture: one square diagonally forward with an opponent's piece
        if (startX + direction == endX && Math.abs(startY - endY) == 1
                && board[endX][endY] != null && board[endX][endY].isWhite() != this.isWhite()) {
            return true;
        }

        return false; // all other movements are invalid
    }
}






