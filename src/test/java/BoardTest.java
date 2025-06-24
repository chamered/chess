import board.BoardImpl;
import board.Position;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pieces.Color;
import pieces.Piece;

public class BoardTest {
    @DisplayName("Should verify that the board has all the pawns setup correctly!")
    @Test
    public void shouldVerifyTheBoard(){
       BoardImpl board = new BoardImpl();
       verifyBoard(board);
    }

    @DisplayName("Provided position does not respect the shape of the board, should return null")
    @Test
    public void shouldReturnNull(){
        BoardImpl board = new BoardImpl();
        Assertions.assertNull(board.getPieceAt(new Position(-2, -4)));
        Assertions.assertNull(board.getPieceAt(new Position(-1, -1)));
        Assertions.assertNull(board.getPieceAt(new Position(-100, -100)));
    }

    @DisplayName("Provided position is valid, should return the pieces properly after move is performed")
    @Test
    public void shouldReturnPieceAfterMove(){
        BoardImpl board = new BoardImpl();
        Piece rook = board.getPieceAt(new Position(0,0));

        Assertions.assertNotNull(board.getPieceAt(new Position(0,0)));
        Assertions.assertEquals("pieces.Rook", rook.getClass().getName());

        board.setPieceAt(new Position(1,0), rook);
        board.setPieceAt(new Position(0,0), null);

        Assertions.assertNull(board.getPieceAt(new Position(0, 0)));
        Assertions.assertNotNull(board.getPieceAt(new Position(1,0)));
    }

    @DisplayName("Should return the position of the King")
    @Test
    public void shouldReturnKingPosition(){
        BoardImpl board = new BoardImpl();

        Assertions.assertEquals(new Position(7,4), board.getKingPosition(Color.WHITE));
        board.setPieceAt(new Position(6,4), board.getPieceAt(new Position(7,4)));
        board.setPieceAt(new Position(7,4), null);
        Assertions.assertEquals(new Position(6,4), board.getKingPosition(Color.WHITE));
    }

    @DisplayName("Should reset the board properly!")
    @Test
    public void shouldResetBoard(){
        BoardImpl board = new BoardImpl();
        verifyBoard(board);

        board.setPieceAt(new Position(1,3), board.getPieceAt(new Position(0, 3)));
        board.setPieceAt(new Position(0,3), null);

        board.setPieceAt(new Position(6,2), board.getPieceAt(new Position(7,2)));
        board.setPieceAt(new Position(7, 2), null);

        Assertions.assertNotNull(board.getPieceAt(new Position(1,3)));
        Assertions.assertNull(board.getPieceAt(new Position(0,3)));
        Assertions.assertNotNull(board.getPieceAt(new Position(6,2)));
        Assertions.assertNull(board.getPieceAt(new Position(7,2)));

        board.resetBoard();
        verifyBoard(board);
    }

    @DisplayName("Should copy the other board correctly")
    @Test
    public void shouldCopyBoard(){
        BoardImpl board = new BoardImpl();
        board.setPieceAt(new Position(1,0), board.getPieceAt(new Position(0, 0)));
        board.setPieceAt(new Position(0, 0), null);

        BoardImpl boardCopy = board.copy();
        Assertions.assertEquals(boardCopy.getPieceAt(new Position(1, 0)).getClass(), board.getPieceAt(new Position(1,0)).getClass());
        Assertions.assertEquals(boardCopy.getPieceAt(new Position(0, 0)), board.getPieceAt(new Position(0, 0)));
    }

    /**
     * Verifies the proper structure of the board
     * @param board the board to verify the placement of its Pieces
     */
    private void verifyBoard(@NotNull BoardImpl board){
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

}
