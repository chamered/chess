import board.BoardImpl;
import board.Position;
import game.Move;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.*;

public class BoardTest {
    private BoardImpl board;

    @BeforeEach
    void setUp() {
        board = new BoardImpl();
    }

    @Test
    void testInitialBoardSetup() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPieceAt(new Position(i, j));
                Assertions.assertNotNull(piece);
                Assertions.assertEquals(Color.BLACK, piece.getColor());
            }
        }

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPieceAt(new Position(i, j));
                Assertions.assertNull(piece);
            }
        }

        for (int i = 6; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPieceAt(new Position(i, j));
                Assertions.assertNotNull(piece);
                Assertions.assertEquals(Color.WHITE, piece.getColor());
            }
        }
    }

    @Test
    void testGetBoard() {
        Piece[][] rawBoard = board.getBoard();
        Assertions.assertNotNull(rawBoard);
        Assertions.assertEquals(8, rawBoard.length);
        Assertions.assertEquals(8, rawBoard[0].length);
    }

    @Test
    void testSetAndGetPieceAt() {
        Position pos = new Position(0, 4);
        King king = new King(Color.BLACK);

        board.setPieceAt(pos, king);
        Piece retrieved = board.getPieceAt(pos);

        Assertions.assertNotNull(retrieved);
        Assertions.assertEquals(king, retrieved);
    }

    @Test
    void testMakeMove() {
        Position from = new Position(6, 0);
        Position to = new Position(5, 0);
        Piece pawn = board.getPieceAt(from);

        board.makeMove(new Move(from, to));

        Assertions.assertNull(board.getPieceAt(from));
        Assertions.assertEquals(pawn, board.getPieceAt(to));
    }

    @Test
    void testGetKingPosition() {
        Position whiteKingPos = board.getKingPosition(Color.WHITE);
        Assertions.assertEquals(new Position(7, 4), whiteKingPos);

        Position blackKingPos = board.getKingPosition(Color.BLACK);
        Assertions.assertEquals(new Position(0, 4), blackKingPos);
    }

    @Test
    void testResetBoard() {
        Position pos = new Position(4, 4);
        board.setPieceAt(pos, new Pawn(Color.WHITE));
        board.resetBoard();

        Piece piece = board.getPieceAt(pos);
        Assertions.assertNull(piece);
    }

    @Test
    void testCopy() {
        BoardImpl boardCopy = board.copy();

        Position pos = new Position(6, 4);
        Piece original = board.getPieceAt(pos);
        Piece cloned = boardCopy.getPieceAt(pos);

        Assertions.assertNotNull(cloned);
        Assertions.assertEquals(original.getClass(), cloned.getClass());
        Assertions.assertNotSame(original, cloned);
    }
}
