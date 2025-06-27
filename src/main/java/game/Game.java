package game;

import pieces.Piece;
import players.Player;

public interface Game {

    /**
     * Starts the game
     */
    void start();

    /**
     * Attempts to move a piece.
     * @param move alleged move to perform
     * @return true iff the move is valid and was performed
     */
    boolean movePiece(Move move);

    /**
     * Undoes the precedent movement of the piece.
     * @param piece the piece
     * @param move the movement performed by the piece
     */
    void undoMove(Piece piece, Move move);

    /**
     * Switches the turn to the next player.
     */
    void switchPlayer();

    /**
     * Returns the current Player playing.
     * @return the current Player.
     */
    Player getCurrentPlayer();

    /**
     * Starts main game loop for chess match.
     * Alternates turns between players, prints updated state of the board,
     * reads player input from console, attempts to perform the specified move.
     * Continues until one player types "exit" or the game ends by other rules
     * (e.g., checkmate or stalemate).
     * Invalid input formats and illegal moves are detected and handled with appropriate messages.
     */
    void runGameLoop();

    /**
     * Handles the different scenarios based on the current game state.
     * @param state the game state to handle
     */
    void handleGameState(GameState state);

    /**
     * Prints a welcome message when the program starts.
     */
    void printWelcomeMessage();
}
