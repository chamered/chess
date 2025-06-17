package pieces;

public class King extends Piece{


        public King(int row, int col, Color color) {
            super(row, col, color);
        }


        @Override
        public void move(){

        }

        public boolean isValidMove(int newRow, int newCol, Piece[][] board) {
            // King may only move one square in each direction
            int rowDiff = Math.abs(newRow - row);
            int colDiff = Math.abs(newCol - col);

            if ((rowDiff <= 1) && (colDiff <= 1)) {

                // Check whether the target field is either empty or contains an opponent's piece
                Piece target = board[newRow][newCol];
                if (target == null || target.getColor() != this.color) {
                    return true;
                }
            }
            return false;
        }
    }







