import board.BoardImpl;
import board.Position;
import game.Move;
import game.RulesEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.*;

import java.util.List;

public class RulesEngineTest {
    private BoardImpl board;

    @BeforeEach
    void setUp() {
        board = new BoardImpl();
    }

    @Test
    public void testIsMoveLegal(){
        Move move1 = new Move(new Position(6, 4), new Position(4, 4));
        boolean moveResult1 = RulesEngine.isMoveLegal(board, move1, Color.WHITE);
        Assertions.assertTrue(moveResult1);

        Move move2 = new Move(new Position(6, 4), new Position(3, 4));
        boolean moveResult2 = RulesEngine.isMoveLegal(board, move2, Color.WHITE);
        Assertions.assertFalse(moveResult2);
    }

    @Test
    public void testWouldCauseSelfCheck() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.setPieceAt(new Position(i, j), null);
            }
        }

        board.setPieceAt(new Position(7, 4), new King(Color.WHITE));
        board.setPieceAt(new Position(0, 4), new Rook(Color.BLACK));
        board.setPieceAt(new Position(6, 4), new Bishop(Color.WHITE));

        Position from = new Position(6, 4);
        Position to = new Position(3, 5);
        boolean result = RulesEngine.wouldCauseSelfCheck(board, new Move(from, to), Color.WHITE);

        Assertions.assertTrue(result);
    }

    @Test
    public void testGetAllLegalMoves() {
        Move possibleMove = new Move(new Position(6, 4), new Position(4, 4));
        List<Move> possibleMoves = RulesEngine.getAllLegalMoves(board, Color.WHITE);

        Assertions.assertTrue(possibleMoves.contains(possibleMove));
    }

    @Test
    void testIsKingInCheck() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.setPieceAt(new Position(i, j), null);
            }
        }

        board.setPieceAt(new Position(7, 4), new King(Color.WHITE));
        board.setPieceAt(new Position(0, 4), new Rook(Color.BLACK));

        boolean result = RulesEngine.isKingInCheck(board, Color.WHITE);
        Assertions.assertTrue(result);
    }

    @Test
    void testIsCheckmate() {
        board.setPieceAt(new Position(6, 5), null);
        board.setPieceAt(new Position(6, 6), null);

        board.setPieceAt(new Position(0, 3), null);
        board.setPieceAt(new Position(3, 7), new Queen(Color.BLACK));

        boolean result = RulesEngine.isCheckmate(board, Color.WHITE);
        Assertions.assertTrue(result);
    }

    @Test
    void testIsStalemate() {
        board.setPieceAt(new Position(6, 5), null);
        board.setPieceAt(new Position(6, 6), null);
        board.setPieceAt(new Position(7, 6), null);

        board.setPieceAt(new Position(7, 7), new King(Color.WHITE));

        board.setPieceAt(new Position(6, 7), new Queen(Color.BLACK));
        board.setPieceAt(new Position(6, 6), new Rook(Color.BLACK));

        Assertions.assertFalse(RulesEngine.isKingInCheck(board, Color.WHITE));

        boolean isStalemate = RulesEngine.isStalemate(board, Color.WHITE);
        Assertions.assertTrue(isStalemate);
    }
}
