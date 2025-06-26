package board;

import game.Move;
import org.jetbrains.annotations.NotNull;
import pieces.Color;
import pieces.Piece;

import java.util.Map;

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
     * Makes a hypothetical move for simulation purposes.
     * @param move the move to make
     */
    void makeMove(Move move);

    /**
     * Returns the position on the board in which the king is located.
     * @param color the color of the player
     * @return the king position on the board
     */
    Position getKingPosition(Color color);

    /**
     * Resets the board to the initial game setup.
     */
    void resetBoard();

    /**
     * Returns a deep copy of the board.
     * @return a new Board instance with the same state
     */
    BoardImpl copy();

    /**
     * Returns the last move made.
     * @return the last move
     */
    Move getLastMove();

    /**
     * Implements the zobrist hash algorithm
     * @return a String representation of the coordinates of every piece in the board
     */
    String zobristKey();

    /**
     * Implements a variant of the zobrist hash algorithm.
     * @return a String representation of the coordinates, type and color of every piece in the board.
     */
    String zobristKeyWithColorAndType();

    boolean isSamePosition(@NotNull BoardImpl simulatedBoard);

    /**
     *
     * @param board
     */
    public void updateHistory(@NotNull BoardImpl board);
    /** Getter method
     * @return the position history.
     */
    Map<String, Integer> getPositionHistory();
}