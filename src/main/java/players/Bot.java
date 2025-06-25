package players;

import board.BoardImpl;
import game.Move;
import pieces.Color;

public interface Bot {
    /**
     * Selects the best move for the bot based on the current state of the board.
     * @param board the current board
     * @return the move that maximizes the bot's advantage (null uf no valid moves are available)
     */
    Move chooseMove(BoardImpl board);

    /**
     * Applies the minimax algorithm to choose the best possible move, up to a given search depth.
     * The algorithm recursively simulates all possible moves up to a certain depth,
     * assuming that both players play optimally. It returns the score associated
     * with the best move at the root level.
     *
     * @param board the current board
     * @param depth the chosen depth of difficulty
     * @param maximizingPlayer true iff it's the bot's turn to maximize the score, false otherwise
     * @return the best score that can be achieved by the bot from this state, assuming optimal play from both parts.
     */
    int minimax(BoardImpl board, int depth, boolean maximizingPlayer);

    /**
     * Uses the values of the pieces to perform a total evaluation of all pieces inside the board.
     * @param board the current board
     * @param botColor bot's color
     * @return the total score of the board
     */
    int evaluateBoard(BoardImpl board, Color botColor);
}
