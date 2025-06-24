import board.BoardImpl;
import board.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pieces.Piece;

import java.util.Arrays;

public class BoardTest {
    /*
        Rook: (0,0), (0,7)
        Knight: (0,1), (0, 6)
        Bishop: (0,2), (0, 5),
        Queen: (0,3), (7,3)
     */
    @DisplayName("Should verify that the board has all the pawns setup correctly!")
    @Test
    public void shouldVerifyTheBoard(){
       BoardImpl board = new BoardImpl();

        Assertions.assertEquals("pieces.Knight", board.getPieceAt(new Position(0,6)).getClass().getName());
        Assertions.assertEquals("pieces.Knight", board.getPieceAt(new Position(0,1)).getClass().getName());
        Assertions.assertEquals("pieces.Knight", board.getPieceAt(new Position(7,1)).getClass().getName());
        Assertions.assertEquals("pieces.Knight", board.getPieceAt(new Position(7,6)).getClass().getName());

        Assertions.assertEquals("pieces.King", board.getPieceAt(new Position(0,4)).getClass().getName());
        Assertions.assertEquals("pieces.King", board.getPieceAt(new Position(7,4)).getClass().getName());

        Assertions.assertEquals("pieces.Rook", board.getPieceAt(new Position(0,0)).getClass().getName());
        Assertions.assertEquals("pieces.Rook", board.getPieceAt(new Position(0,7)).getClass().getName());
        Assertions.assertEquals("pieces.Rook", board.getPieceAt(new Position(7,0)).getClass().getName());
        Assertions.assertEquals("pieces.Rook", board.getPieceAt(new Position(7, 7)).getClass().getName());

        Assertions.assertEquals("pieces.Bishop", board.getPieceAt(new Position(0,2)).getClass().getName());
        Assertions.assertEquals("pieces.Bishop", board.getPieceAt(new Position(0,5)).getClass().getName());
        Assertions.assertEquals("pieces.Bishop", board.getPieceAt(new Position(7, 2)).getClass().getName());
        Assertions.assertEquals("pieces.Bishop", board.getPieceAt(new Position(7, 5)).getClass().getName());

        Assertions.assertEquals("pieces.Queen", board.getPieceAt(new Position(0,3)).getClass().getName());
        Assertions.assertEquals("pieces.Queen", board.getPieceAt(new Position(7,3)).getClass().getName());

        for(int i = 0; i < 8; i++){
            Assertions.assertEquals("pieces.Pawn", board.getPieceAt(new Position(1, i)).getClass().getName());
            Assertions.assertEquals("pieces.Pawn", board.getPieceAt(new Position(6, i)).getClass().getName());
        }
    }
    @Test
    public void testPieceAt(){
        BoardImpl board = new BoardImpl();
        board.getPieceAt(new Position(-2,-2));

    }
}
