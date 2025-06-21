package game;

import board.BoardImpl;
import board.Position;

public class Game {

    //Set up the game and responds to player interaction

    private static BoardImpl board = new BoardImpl();
    private Player whitePlayer;
    private Player blackPlayer;
    private GameStateEnum gameState;

    public static void main(String[] args) {
        GameManager gm = new GameManager(board);
        board.printBoard();
        gm.movePiece(new Position(6, 7), new Position(4, 7));
        board.printBoard();
    }

    //Initializes board and players
    public void startGame() {
        //TODO
    }

    //Execute a move
    public void playTurn(int fromX, int fromY, int toX, int toY) {
        //TODO
    }

    //Check, Checkmate, etc.
    private void checkGameState() {
        //TODO
    }
}
