package pieces;

import java.util.ArrayList;
import java.util.List;

import board.BoardImpl;
import board.Position;

public class King extends Piece {

    public King(Color color) {
        super(color == Color.WHITE ? 1000 : -1000, 'K', color); // Kings are usually very high value (1000+)
    }

    private boolean hasMoved = false;

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean moved) {
        this.hasMoved = moved;
    }




    @Override
    public Piece copy() {
        King copy = new King(this.color);
        copy.setHasMoved(this.hasMoved);
        return copy;

    }

    @Override
    public boolean eatOtherPiece(Piece piece) {
        // The king can only capture enemy pieces
        return piece != null && piece.color != this.color;
    }


        @Override
        public List<String> generatePossibleMoves(BoardImpl board, Position currentPos) {
            List<String> moves = new ArrayList<>();

            int row = currentPos.getRow();
            int col = currentPos.getColumn();
            Piece[][] boardState = board.getBoard();

            // Standard 8 surrounding squares
            for (int r = -1; r <= 1; r++) {
                for (int c = -1; c <= 1; c++) {
                    if (r == 0 && c == 0) continue;
                    Position pos = new Position(row + r, col + c);
                    if (isInsideBoard(pos)) {
                        Piece target = boardState[pos.getRow()][pos.getColumn()];
                        if (target == null || eatOtherPiece(target)) {
                            moves.add(toAlgebraic(pos));
                        }
                    }
                }
            }

            // === CASTLING (basic version) ===
            if (!hasMoved) {
                // Short Castling (kingside)
                if (canCastle(board, row, 5, 6, 7)) {
                    moves.add(toAlgebraic(new Position(row, 6)));
                }

                // Long Castling (queenside)
                if (canCastle(board, row, 1, 2, 3, 0)) {
                    moves.add(toAlgebraic(new Position(row, 2)));
                }
            }

            return moves;
        }

        // Simple castling check: all between squares are empty & rook exists and didn't move
        private boolean canCastle(BoardImpl board, int row, int... cols) {
            // Check if all squares between the king and the rook are empty
            for (int i = 0; i < cols.length - 1; i++) {
                // If any square is not empty, castling is not allowed
                if (board.getPieceAt(new Position(row, cols[i])) != null) return false;
            }
            // Check the final square (expected position of the rook)
            Piece rook = board.getPieceAt(new Position(row, cols[cols.length - 1]));

            // Return true if it's a rook and it hasn't moved yet

            return (rook instanceof Rook r && !r.hasMoved());
        }
    }
