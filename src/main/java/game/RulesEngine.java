package game;

import board.BoardImpl;
import board.Position;
import pieces.Color;
import pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class RulesEngine {
    /**
     *
     * @param board the board
     * @param move the alleged movement to be performed
     * @param color the color of the player
     * @return true iff the movement is legal
     */
    public static boolean isLegalMove(BoardImpl board, Move move, Color color) {
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
        return isKingInCheck(color, simulatedBoard);
    }

    /**
     *
     * @param board the current board
     * @param color the current player
     * @return a List of moves containing all the valid moves
     */
    public static List<Move> getAllValidMoves(BoardImpl board, Color color) {
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
                        if(isLegalMove(board, move, color)) validMoves.add(move);
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
    public static boolean isKingInCheck(Color color, BoardImpl board) {
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
    public static boolean isCheckmate(Color color, BoardImpl board) {
        if (!isKingInCheck(color, board)) return false;
        List<Move> validMoves = getAllValidMoves(board, color);

        return validMoves.isEmpty();
    }

    //Check for stalemate
    public static boolean isStalemate(Color color, BoardImpl board) {
        if (isKingInCheck(color, board)) return false;
        List<Move> validMoves = getAllValidMoves(board, color);

        return validMoves.isEmpty();
    }

    public static boolean isFiftyMoveRule(BoardImpl board, Player player){
        return Move.moveHistory.get(player.getColor()).size() > 50 && player.getCapturedPieces().isEmpty();
    }
}
