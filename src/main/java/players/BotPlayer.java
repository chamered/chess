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

    public BotPlayer(Color color, int depth) {
        super("Juice Bot's", color);
        this.depth = depth;
    }

    @Override
    public Move chooseMove(BoardImpl board) {
        List<Move> validMoves = RulesEngine.getAllLegalMoves(board, getColor());
        if (validMoves.isEmpty()) return null;

        Move bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        for (Move move : validMoves) {
            BoardImpl boardCopy = board.copy();
            boardCopy.makeMove(move);

            int score = minimax(boardCopy, depth - 1, false);

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        RulesEngine.incrementCounterFromMoveHistory(getColor());

        return bestMove;
    }

    /**
     * Computes the opponent's color.
     * @param color the color of the current Player
     * @return the color of the opponent
     */
    private static Color getOpponentColor(Color color){
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    @Override
    public int minimax(BoardImpl board, int depth, boolean maximizingPlayer) {
        Color botColor = getColor();
        Color currentColor = maximizingPlayer ? botColor : getOpponentColor(botColor);

        //Base case: reached mac depth or no valid moves
        List<Move> validMoves = RulesEngine.getAllLegalMoves(board, currentColor);
        //If the depth is 0 the only evaluation is the board evaluation
        if (depth == 0 || validMoves.isEmpty()) {
            return evaluateBoard(board, botColor);
        }

        int bestScore = maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Move move : validMoves) {
            //Simulate move
            BoardImpl boardCopy = board.copy();
            boardCopy.makeMove(move);

            //Recursive minimax call
            int result = minimax(boardCopy, depth - 1, !maximizingPlayer);

            if (maximizingPlayer) {
                if (result > bestScore) {
                    bestScore = result;
                }
            } else {
                if (result < bestScore) {
                    bestScore = result;
                }
            }
        }

        return bestScore;
    }

    @Override
    public int evaluateBoard(BoardImpl board, Color botColor) {
        Piece[][] boardState = board.getBoard();
        int score = 0;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = boardState[row][col];

                if (piece != null){
                    //The value of pieces is positive and added if it is the sam color of the BotPlayer
                    if (piece.getColor() == botColor) score += Math.abs(piece.getValue());
                        //Is subtracted if it is a different color
                    else {
                        score -= Math.abs(piece.getValue());
                    }
                }
            }
        }

        return score;
    }


}
