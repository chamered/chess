package board;

import game.PlayerTurnEnum;
import pieces.Piece;

import java.util.Map;
import java.util.Set;

public interface ChessBoardInterface {
    /**
     * Method which setups the initial board
     */
    void setupBoard();
    /**
     *
     * @return a set containing all the pieces currently present in the Board
     */
    Set<Piece> getPiecesOnBoard();

    /**
     *
     * @param playerTurnEnum of type PlayerTurnEnum, it can be either BLACK_PLAYER (0) or WHITE_PLAYER (1)
     * @return a set containing all the pieces based on the player
     */
    Set<Piece> getPiecesByPlayer(PlayerTurnEnum playerTurnEnum);
    /**
     *
     * @return a new map which links the player to its pieces
     */
    Map<PlayerTurnEnum, Set<Piece>> getPiecesBelongingToPlayer();
    /**
     *
     * @param piece of type Piece.
     * @return a set containing all the pieces of a certain type
     */
    Set<Piece> getPiecesByType(Piece piece);

    /**
     *
     * @return a map associating each Piece type with its number of occurence in the board, regardless of the color
     */
    Map<Piece, Integer> getPiecesCountByType();
    /**
     *
     * @return true iff a player is in the checkmate state
     */
    boolean checkMate();
}
