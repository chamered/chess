package game;

import board.Position;
import players.Player;

import java.util.List;

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
     * Switches the turn to the next player.
     */
    void switchPlayer();

    /**
     * Returns the current Player playing.
     * @return the current Player.
     */
    Player getCurrentPlayer();

    /**
     *
     * @param from the initial position
     * @return a list of Strings containing all the possibles moves for the given Piece in the provided position.
     */
    List<String> getUnfilteredPossibleMoves(Position from);

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
