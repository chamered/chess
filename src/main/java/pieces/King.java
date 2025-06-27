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

    @Override
    public Piece copy() {
        King copy = new King(this.color);
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

    //normal 1-square moves
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
    return m;
    }
}



