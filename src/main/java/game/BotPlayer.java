package game;

import board.BoardImpl;
import pieces.Color;

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

    public int evaluateBoard(BoardImpl board, Color botColor) {
        return 0;
    }
}
