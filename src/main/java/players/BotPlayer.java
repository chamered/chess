package players;

import board.BoardImpl;
import game.Move;
import game.RulesEngine;
import pieces.Color;
import pieces.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BotPlayer extends Player implements Bot{

    private final int depth;
    private static final int  REPETITION_PENALTY = 20; // discourage repetition
    private static final int  CHECK_BONUS = 25; // reward giving check
    private static final int CAPTURE_BONUS = 20;   // reward capturing piece
    private static final int  MATE_SCORE = 10_000;

    //Remember already-seen positions in this game (lightweight hash keys).
    private final java.util.Map<String,Integer> positionCounts = new java.util.HashMap<>();

    public BotPlayer(Color color, int depth) {
        super("Juice Bot's", color);
        this.depth = depth;
    }

    @Override
    public Move chooseMove(BoardImpl board) {
        positionCounts.merge(board.zobristKey(), 1, Integer::sum);

        List<Move> legalMoves = RulesEngine.getAllLegalMoves(board, getColor());
        if (legalMoves.isEmpty()) return null;

        Move best = null;
        int alpha = Integer.MIN_VALUE;
        int beta  = Integer.MAX_VALUE;

        for (Move move : legalMoves) {
            BoardImpl copy = board.copy();
            copy.makeMove(move);

            int score = minimax(copy, depth - 1, alpha, beta, false);

            if (score > alpha) {
                alpha = score;
                best  = move;
            }
        }

        RulesEngine.incrementCounterFromMoveHistory(getColor());

        return best;
    }

    /**
     * Computes the opponent's color.
     * @param color the color of the current Player
     * @return the color of the opponent
     */
    private static Color getOpponentColor(Color color){
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private int minimax(BoardImpl board, int depth,
                        int alpha, int beta, boolean maximizing) {

        Color botColor = getColor();
        Color side     = maximizing ? botColor : getOpponentColor(botColor);

        //repetition penalty
        int repeats = positionCounts.getOrDefault(board.zobristKey(), 0);
        int repetitionPenalty = repeats * REPETITION_PENALTY;

        //terminal / leaf
        List<Move> legal = RulesEngine.getAllLegalMoves(board, side);
        if (depth == 0 || legal.isEmpty()) {
            int score = evaluateBoard(board, botColor);

            if (RulesEngine.isCheckmate(board, side))
                score = -MATE_SCORE;                       // side-to-move is mated
            else if (RulesEngine.isKingInCheck(board, side))
                score += maximizing ? CHECK_BONUS : -CHECK_BONUS;

            return maximizing ? score - repetitionPenalty
                    : score + repetitionPenalty;
        }

        //Recursive search
        if (maximizing) {
            int best = Integer.MIN_VALUE;

            for (Move mv : legal) {
                BoardImpl child = board.copy();
                child.makeMove(mv);

                int capBonus = captureDelta(board, mv, side) > 0 ? CAPTURE_BONUS : 0;

                positionCounts.merge(child.zobristKey(), 1, Integer::sum);
                int val = minimax(child, depth - 1, alpha, beta, false) + capBonus;
                positionCounts.merge(child.zobristKey(), -1, Integer::sum);

                best  = Math.max(best, val);
                alpha = Math.max(alpha, best);
                if (beta <= alpha) break;                  // β-cut
            }
            return best - repetitionPenalty;

        } else {  /* minimising branch (opponent) */
            int best = Integer.MAX_VALUE;

            for (Move mv : legal) {
                BoardImpl child = board.copy();
                child.makeMove(mv);

                int capPenalty = captureDelta(board, mv, side) > 0 ? CAPTURE_BONUS : 0;

                positionCounts.merge(child.zobristKey(), 1, Integer::sum);
                int val = minimax(child, depth - 1, alpha, beta, true) - capPenalty;
                positionCounts.merge(child.zobristKey(), -1, Integer::sum);

                best  = Math.min(best, val);
                beta  = Math.min(beta, best);
                if (beta <= alpha) break;                  // α-cut
            }
            return best + repetitionPenalty;
        }
    }

    //Wrapper - so that al precedent calls remain the same and automatically assign known values
    @Override
    public int minimax(BoardImpl board, int depth, boolean maximizingPlayer) {
        return minimax(board, depth,
                Integer.MIN_VALUE, Integer.MAX_VALUE,
                maximizingPlayer);
    }

    public int evaluateBoard(BoardImpl board, Color botColor) {
        int material = 0;
        for (Piece[] row : board.getBoard())
            for (Piece p : row)
                if (p != null)
                    material += (p.getColor() == botColor)
                            ?  Math.abs(p.getValue())
                            : -Math.abs(p.getValue());
        return material;
    }

    //Returns the material swing (positive if sideToMove captures).
    private static int captureDelta(BoardImpl board, Move m, Color sideToMove) {
        Piece target = board.getPieceAt(m.to());
        if (target == null) return 0;
        int v = Math.abs(target.getValue());
        return (target.getColor() != sideToMove) ? v : 0; // cannot capture own piece
    }


}
