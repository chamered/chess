package game;

import board.BoardImpl;
import board.Position;
import pieces.Color;
import pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class RulesEngine {

    //Legality validation

    //Check if a move is legal
    public boolean isLegalMove(BoardImpl board, Move move, Color color) {
        Piece piece = board.getPieceAt(move.from());

        if (piece == null || piece.getColor() != color) return false;

        List<String> legalDestinations = piece.generatePossibleMoves(board, move.from());
        String toAlgebraic = Piece.toAlgebraic(move.to());

        if (!legalDestinations.contains(toAlgebraic)) return false;

        return !wouldCauseSelfCheck(board, move, color);
    }

    //Check if the move is going to cause a self check (illegal)
    public boolean wouldCauseSelfCheck(BoardImpl board, Move move, Color color) {
        BoardImpl simulatedBoard = board.copy();
        simulatedBoard.makeMove(move);
        return isKingInCheck(color, simulatedBoard);
    }

    //Returns a list of valid moves (not illegal)
    public List<Move> getAllValidMoves(BoardImpl board, Color color) {
        List<Move> validMoves = new ArrayList<>();
        Piece[][] boardState = board.getBoard();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = boardState[row][col];
                if (piece != null && piece.getColor() == color) {
                    Position from = new Position(row, col);
                    List<String> possibleDestinations = piece.generatePossibleMoves(board, from);
                    for (String algebraic : possibleDestinations) {
                        int[] coordinates = Piece.fromAlgebraic(algebraic);
                        Position to = new Position(coordinates[0], coordinates[1]);
                        Move move = new Move(from, to);
                        if (isLegalMove(board, move, color)) {
                            validMoves.add(move);
                        }
                    }
                }
            }
        }

        return validMoves;
    }

    //Checks if the king is in check
    public boolean isKingInCheck(Color color, BoardImpl board) {
        Position kingPos = board.getKingPosition(color);
        Piece[][] boardState = board.getBoard();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = boardState[row][col];
                if (piece != null && piece.getColor() != color) {
                    Position from = new Position(row, col);
                    List<String> enemyMoves = piece.generatePossibleMoves(board, from);
                    for (String move : enemyMoves) {
                        int[] coords = Piece.fromAlgebraic(move);
                        if (coords[0] == kingPos.getRow() && coords[1] == kingPos.getColumn()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    //Check if it is checkmate
    public boolean isCheckmate(Color color, BoardImpl board) {
        if (!isKingInCheck(color, board)) return false;
        List<Move> validMoves = getAllValidMoves(board, color);

        return validMoves.isEmpty();
    }

    //Check for stalemate
    public boolean isStalemate(Color color, BoardImpl board) {
        if (isKingInCheck(color, board)) return false;
        List<Move> validMoves = getAllValidMoves(board, color);

        return validMoves.isEmpty();
    }

}
