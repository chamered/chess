package game;

import board.BoardImpl;
import pieces.Color;
import pieces.Piece;

public class BotPlayer extends Player{

    public BotPlayer(Color color) {
        super("Bot Player", color);
    }

    public Move chooseMove(BoardImpl board) {
        return null;
    }

    public int minimax(BoardImpl board, int depth, boolean maximizingPlayer) {
        return 0;
    }

    /**
     * This method uses the value of the pieces to do a total evaluation
     * of all the pieces inside the current board
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
