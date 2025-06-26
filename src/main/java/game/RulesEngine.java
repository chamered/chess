package game;

import board.BoardImpl;
import board.Position;
import pieces.*;
import players.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RulesEngine {

     public static final Map<Color, Integer> playersMoves = new HashMap<>(){{
         put(Color.WHITE, 0);
         put(Color.BLACK, 0);
     }};

    /**
     * Checks if a move is legal.
     * @param board the board to check the move on
     * @param move the move to check
     * @param color the player's color
     * @return true if the move is legal, false otherwise
     */
    public static boolean isMoveLegal(BoardImpl board, Move move, Color color) {

        /* ---------- 1. piece ownership ------------------------------------ */
        Piece piece = board.getPieceAt(move.from());
        if (piece == null || piece.getColor() != color) return false;

        /* ---------- 2. destination among pseudo-legal moves --------------- */
        List<String> pseudoMoves = piece.generatePossibleMoves(board, move.from());
        String destAlg = Piece.toAlgebraic(move.to());

        boolean destinationFound = false;
        for (String s : pseudoMoves) {
            /* strip “=Q”, “=R”, … so promotions match as well               */
            String core = (s.indexOf('=') >= 0) ? s.substring(0, s.indexOf('=')) : s;
            if (core.equals(destAlg)) {
                destinationFound = true;
                break;
            }
        }
        if (!destinationFound) return false;

        /* ---------- 3. no self-check after making the move ---------------- */
        BoardImpl simulated = board.copy();
        simulated.makeMove(move);
        return !isKingInCheck(simulated, color);
    }

    /**
     * Takes in the board and checks with a copy of the current board, if a move may cause a self check (Illegal).
     * @param board the current board
     * @param move the movement
     * @param color the color of the Player
     * @return true iff the movement causes the king to be in check
     */
    public static boolean wouldCauseSelfCheck(BoardImpl board, Move move, Color color) {
        BoardImpl simulatedBoard = board.copy();
        simulatedBoard.makeMove(move);
        return isKingInCheck(simulatedBoard, color);
    }

    /**
     *
     * @param board the current board
     * @param color the current player
     * @return a List of moves containing all the valid moves
     */
    public static List<Move> getAllLegalMoves(BoardImpl board, Color color) {
        List<Move> allLegal = new ArrayList<>();

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Position from = new Position(r, c);
                Piece p = board.getPieceAt(from);
                if (p == null || p.getColor() != color) continue;

                for (String targetStr : p.generatePossibleMoves(board, from)) {
                    Position to = Piece.fromAlgebraic(targetStr.replace("=Q", ""));
                    Move m = new Move(from, to);
                    if (isMoveLegal(board, m, color)) {
                        allLegal.add(m);
                    }
                }
            }
        }

        return allLegal;
    }

    /**
     *
     * @param color the current player playing
     * @param board the current board
     * @return true iff the King is in check
     */
    public static boolean isKingInCheck(BoardImpl board, Color color) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = board.getPieceAt(new Position(r, c));
                if (p instanceof King && p.getColor() == color) {
                    return isSquareAttacked(board, new Position(r, c), color);
                }
            }
        }
        return false; // Should never happen unless the king is missing
    }

    /**
     * Returns {@code true} iff the square {@code sq} is attacked by *any* piece of the
     * side opposite to {@code friendly}.  “Attacked” means that, ignoring whose turn
     * it is and whether the attacking king would be in check itself, the enemy could
     * legally capture a piece standing on {@code sq}.
     *
     * This method is intentionally self-contained – it does *not* call
     * {@code generatePossibleMoves} for the hostile pieces (that could recurse back
     * into us, especially for king castling).  Instead it hard-codes the attack
     * patterns for each piece type.
     */
    public static boolean isSquareAttacked(BoardImpl b, Position sq, Color friendly) {

        final Color enemy = (friendly == Color.WHITE) ? Color.BLACK : Color.WHITE;
        final int r = sq.row(), c = sq.column();
        final Piece[][] board = b.getBoard();

        /*----------------------------------------------------------------------
         * 1.  Pawn attacks
         *--------------------------------------------------------------------*/
        int pawnDir = (enemy == Color.WHITE) ? -1 : 1;          // row change of a pawn *from* its square *to* target
        int pawnRow = r + pawnDir;
        if (pawnRow >= 0 && pawnRow < 8) {
            for (int dc : new int[]{-1, 1}) {
                int pc = c + dc;
                if (pc >= 0 && pc < 8) {
                    Piece p = board[pawnRow][pc];
                    if (p instanceof Pawn && p.getColor() == enemy) return true;
                }
            }
        }

        /*----------------------------------------------------------------------
         * 2.  Knight attacks
         *--------------------------------------------------------------------*/
        int[][] knightDeltas = {
                {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
                { 1, -2}, { 1, 2}, { 2, -1}, { 2, 1}
        };
        for (int[] d : knightDeltas) {
            int nr = r + d[0], nc = c + d[1];
            if (nr >= 0 && nr < 8 && nc >= 0 && nc < 8) {
                Piece p = board[nr][nc];
                if (p instanceof Knight && p.getColor() == enemy) return true;
            }
        }

        /*----------------------------------------------------------------------
         * 3.  Bishop / Queen (diagonals)
         *--------------------------------------------------------------------*/
        int[][] diagDirs = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        for (int[] dir : diagDirs) {
            int nr = r + dir[0], nc = c + dir[1];
            while (nr >= 0 && nr < 8 && nc >= 0 && nc < 8) {
                Piece p = board[nr][nc];
                if (p != null) {
                    if (p.getColor() == enemy && (p instanceof Bishop || p instanceof Queen))
                        return true;
                    break;                  // blocked
                }
                nr += dir[0];
                nc += dir[1];
            }
        }

        /*----------------------------------------------------------------------
         * 4.  Rook / Queen (files & ranks)
         *--------------------------------------------------------------------*/
        int[][] orthoDirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : orthoDirs) {
            int nr = r + dir[0], nc = c + dir[1];
            while (nr >= 0 && nr < 8 && nc >= 0 && nc < 8) {
                Piece p = board[nr][nc];
                if (p != null) {
                    if (p.getColor() == enemy && (p instanceof Rook || p instanceof Queen))
                        return true;
                    break;                  // blocked
                }
                nr += dir[0];
                nc += dir[1];
            }
        }

        /*----------------------------------------------------------------------
         * 5.  King (adjacent squares)
         *--------------------------------------------------------------------*/
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int nr = r + dr, nc = c + dc;
                if (nr >= 0 && nr < 8 && nc >= 0 && nc < 8) {
                    Piece p = board[nr][nc];
                    if (p instanceof King && p.getColor() == enemy) return true;
                }
            }
        }

        /*----------------------------------------------------------------------
         * 6.  Not attacked
         *--------------------------------------------------------------------*/
        return false;
    }

    //Check if it is checkmate
    public static boolean isCheckmate(BoardImpl board, Color color) {
        if (!isKingInCheck(board, color)) return false;
        List<Move> validMoves = getAllLegalMoves(board, color);

        return validMoves.isEmpty();
    }

    //Check for stalemate
    public static boolean isStalemate(BoardImpl board, Color color) {
        if (isKingInCheck(board, color)) return false;
        List<Move> validMoves = getAllLegalMoves(board, color);

        return validMoves.isEmpty();
    }

    /**
     * Increments the counter of moves of the player.
     * @param color the color of the player
     */
    public static void incrementCounterFromMoveHistory(Color color){
        playersMoves.compute(color, (k, counter) -> counter + 1);
    }

    /**
     * Resets the move history of the color associated to the player.
     * @param color the color of the piece/player.
     */
    public static void resetMoveHistory(Color color){
        playersMoves.put(color, 0);
    }
    /**
     * Verifies the 50-move-rule
     * @param player the player
     * @return true iff the 50-move-rule has been breached
     */
    public static boolean isFiftyMoveRule(Player player){
        return RulesEngine.playersMoves.get(player.getColor()) >= 50;
    }

    public static boolean isThreeFoldRepetition(){

    }
}
