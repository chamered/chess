package pieces;

import java.util.ArrayList;
import java.util.List;

import board.BoardImpl;
import board.Position;
import game.RulesEngine;


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
         public List<String> generatePossibleMoves(BoardImpl b, Position pos) {
        List<String> m = new ArrayList<>();

        /* ------- normal 1-square moves ----------------------------------- */
        for (int dr = -1; dr <= 1; dr++)
            for (int dc = -1; dc <= 1; dc++)
                if (dr != 0 || dc != 0) {
                    Position q = new Position(pos.row() + dr,
                            pos.column() + dc);
                    if (!isInsideBoard(q)) continue;
                    Piece tgt = b.getPieceAt(q);
                    if (tgt == null || eatOtherPiece(tgt))
                        m.add(toAlgebraic(q));
                }

        /* ------- castling ------------------------------------------------- */
        addCastlingMoves(b, pos, m);

        return m;
    }

    private void addCastlingMoves(BoardImpl b, Position kingPos,
                                  List<String> moves) {
        if (hasMoved || RulesEngine.isSquareAttacked(b, kingPos, getColor())) return;

        int row = kingPos.row();
        int[][] sides = {{7, 6, 5}, {0, 2, 3}};  // rook file, kingDest, through

        for (int[] s : sides) {
            Piece rook = b.getPieceAt(new Position(row, s[0]));
            if (!(rook instanceof Rook) || ((Rook) rook).hasMoved()) continue;

            // squares between king and rook must be empty
            boolean pathClear = true;
            for (int c = Math.min(kingPos.column(), s[0]) + 1;
                 c < Math.max(kingPos.column(), s[0]); c++) {
                if (b.getPieceAt(new Position(row, c)) != null) {
                    pathClear = false; break;
                }
            }
            if (!pathClear) continue;

            // king may not pass through attacked squares
            Position through = new Position(row, s[2]);
            Position dest   = new Position(row, s[1]);
            if (RulesEngine.isSquareAttacked(b, through, getColor())
                    || RulesEngine.isSquareAttacked(b, dest, getColor()))
                continue;

            moves.add(toAlgebraic(dest));
        }
    }
}



