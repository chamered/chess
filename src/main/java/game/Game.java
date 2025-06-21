package game;

import board.BoardImpl;

public class Game {

    //Set up the game and responds to player interaction

    private BoardImpl board;
    private Player withePlayer;
    private Player blackPlayer;
    private GameStateEnum gameState;

    //Initializes board and players
    public void startGame() {
        //TODO
    }

    //Execute a move
    public void playTurn(int fromX, int fromY, int toX, int toY) {
        //TODO
    }

    //Changes the turn
    private void switchTurn() {
        //TODO
    }

    //Check, Checkmate, etc.
    private void checkGameState() {
        //TODO
    }
}
