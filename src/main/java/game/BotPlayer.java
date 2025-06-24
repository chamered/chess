package game;

import board.BoardImpl;
import pieces.Color;
import pieces.Piece;

import java.util.List;

public class BotPlayer extends Player{

    public record MinimaxResult(int score, Move bestMove) {};

    public BotPlayer(Color color) {
        super("Juice Bot", color);
    }

    public Move chooseMove(BoardImpl board) {
        return null;
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
    public MinimaxResult minimax(BoardImpl board, int depth, boolean maximizingPlayer, Color botCol) {
        Color botColor = getColor();
        Color currentColor = maximizingPlayer ? botColor : getOpponentColor(botColor);

        //Base case: reached mac depth or no valid moves
        List<Move> validMoves = RulesEngine.getAllValidMoves(board, currentColor);
        //If the depth is 0 the only evaluation is the board evaluation
        if (depth == 0 || validMoves.isEmpty()) {
            int score = evaluateBoard(board, botColor);
            return new MinimaxResult(score, null);
        }

        Move bestMove = null;
        int bestScore = maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Move move : validMoves) {
            //Simulate move
            BoardImpl boardCopy = board.copy();
            boardCopy.makeMove(move);

            //Recursive minimax call
            MinimaxResult result = minimax(boardCopy, depth - 1, !maximizingPlayer, botColor);

            if (maximizingPlayer) {
                if (result.score > bestScore) {
                    bestScore = result.score;
                    bestMove = move;
                }
            } else {
                if (result.score < bestScore) {
                    bestScore = result.score;
                    bestMove = move;
                }
            }
        }

        return new MinimaxResult(bestScore, bestMove);
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
