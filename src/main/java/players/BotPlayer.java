package players;

import board.BoardImpl;
import game.Move;
import game.RulesEngine;
import pieces.Color;
import pieces.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BotPlayer extends Player{

    private final int depth;


    public BotPlayer(Color color, int depth) {
        super("Juice Bot's", color);
        this.depth = depth;
    }

    /**
     * Selects the best move for the bot based on the current state of the board.
     * @param board the current board from which the bot should decide its move
     * @return the move that maximizes the bot's advantage (null uf no valid moves are available)
     */
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

        Move.addMoveToHistory(getColor(), bestMove);

        return bestMove;
    }

    private static Color getOpponentColor(Color color){
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    /**
     * Applies the Minimax algorithm to evaluate and choose the best possible move
     * for the current player (bot or opponent) up to a given search depth.
     *
     * The algorithm recursively simulates all possible moves up to a certain depth,
     * assuming that both players play optimally. It returns the score associated
     * with the best move at the root level.
     *
     * @param board the current state of the game board (deep copied at each level)
     * @param depth how many layers deep the algorithm will explore (e.g., 2 = bot move, opponent reply)
     * @param maximizingPlayer true if it's the bot's turn to maximize the score, false if minimizing (opponent)
     * @return the best score the bot can achieve from this state, assuming optimal play by both players
     */
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

    /**
     * This method uses the value of the pieces to do a total evaluation
     * of all the pieces inside the current board
     *
     * @param board the current board
     * @param botColor the color of the bot pieces (WHITE or BLACK)
     * @return The total score of the board
     */
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
