package board;

import game.Player;
import game.PlayerTurnEnum;
import pieces.Color;
import pieces.Piece;

import java.util.Map;
import java.util.Set;

public interface Board {

    /**
     * Prints the current state of the board to the terminal.
     */
    void printBoard();

    /**
     * Returns the current 2D array representing the board.
     * @return a 2D array of pieces representing the board state
     */
    Piece[][] getBoard();

    /**
     * Returns the piece at the given position.
     * @param pos the position on the board
     * @return the piece at the given position, or null if the square is empty
     */
    Piece getPieceAt(Position pos);

    /**
     * Sets a piece at the given position.
     * @param pos the position on the board
     * @param piece the piece to place, or null to clear the square
     */
    void setPieceAt(Position pos, Piece piece);

    /**
     * Resets the board to the initial game setup.
     */
    void resetBoard();
}