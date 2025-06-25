package game;

import board.BoardImpl;
import board.Position;
import pieces.Color;
import pieces.Piece;
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
        Piece piece = board.getPieceAt(move.from());

        if (piece == null || piece.getColor() != color) return false;

        List<String> legalDestinations = piece.generatePossibleMoves(board, move.from());
        String toAlgebraic = Piece.toAlgebraic(move.to());

        if (!legalDestinations.contains(toAlgebraic)) return false;

        return !wouldCauseSelfCheck(board, move, color);
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
        List<Move> validMoves = new ArrayList<>();
        Piece[][] boardState = board.getBoard();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = boardState[row][col];
                if (piece != null && piece.getColor() == color) {
                    Position from = new Position(row, col);
                    List<String> possibleDestinations = piece.generatePossibleMoves(board, from);
                    possibleDestinations.forEach(algebraic -> {
                        Position to = Piece.fromAlgebraic(algebraic);
                        Move move = new Move(from, to);
                        if(isMoveLegal(board, move, color)) validMoves.add(move);
                    });
                }
            }
        }

        return validMoves;
    }

    /**
     *
     * @param color the current player playing
     * @param board the current board
     * @return true iff the King is in check
     */
    public static boolean isKingInCheck(BoardImpl board, Color color) {
        Position kingPos = board.getKingPosition(color);
        Piece[][] boardState = board.getBoard();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = boardState[row][col];
                if (piece != null && piece.getColor() != color) {
                    Position from = new Position(row, col);
                    List<String> enemyMoves = piece.generatePossibleMoves(board, from);
                    for (String move : enemyMoves) {
                        Position coordinates = Piece.fromAlgebraic(move);
                        if (coordinates.getRow() == kingPos.getRow() && coordinates.getColumn() == kingPos.getColumn()) {
                            return true;
                        }
                    }
                }
            }
        }
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
     * Verifies the 50-move-rule
     * @param player the player
     * @return true iff the 50-move-rule has been breached
     */
    public static boolean isFiftyMoveRule(Player player){
        return false;
    }
}
