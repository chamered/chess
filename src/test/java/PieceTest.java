import board.BoardImpl;
import board.Position;
import game.Move;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pieces.Bishop;
import pieces.Color;
import pieces.Pawn;
import pieces.Piece;

import java.util.List;

public class PieceTest {
    @Test
    void testEatOtherPiece() {
        BoardImpl board = new BoardImpl();

        Pawn wPawn = new Pawn(Color.WHITE);
        Pawn bPawn = new Pawn(Color.BLACK);
        board.setPieceAt(new Position(4, 4), wPawn);
        board.setPieceAt(new Position(3, 4), bPawn);

        boolean result = wPawn.eatOtherPiece(bPawn);
        Assertions.assertTrue(result);
    }

    @Test
    void testGeneratePossibleMoves() {
        BoardImpl board = new BoardImpl();
        Piece wPawn = board.getPieceAt(new Position(6, 4));

        List<String> possibleMoves = wPawn.generatePossibleMoves(board, new Position(6, 4));
        Assertions.assertTrue(possibleMoves.contains("e4"));
        Assertions.assertFalse(possibleMoves.contains("f7"));
    }

    @Test
    void testToAlgebraic() {
        Position h6 = new Position(2, 7);
        Position a5 = new Position(3, 0);

        Assertions.assertEquals("h6", Piece.toAlgebraic(h6));
        Assertions.assertEquals("a5", Piece.toAlgebraic(a5));
    }

    @Test
    void testFromAlgebraic() {
        String pos = "e2";
        Assertions.assertEquals(Piece.fromAlgebraic(pos), new Position(6, 4));
    }

    @Test
    void testIsInsideBoard() {
        Position in = new Position(3, 6);
        Position out = new Position(-2, 9);

        Assertions.assertTrue(Piece.isInsideBoard(in));
        Assertions.assertFalse(Piece.isInsideBoard(out));
    }

    @Test
    void testCopy() {
        Pawn pawn = new Pawn(Color.WHITE);
        Pawn pawnCopy = (Pawn) pawn.copy();

        Assertions.assertNotNull(pawnCopy);
        Assertions.assertInstanceOf(Pawn.class, pawnCopy);
        Assertions.assertNotSame(pawn, pawnCopy);
    }
}
