import board.ChessBoard;
import org.junit.jupiter.api.Test;

public class ChessBoardTest {
    @Test
    public void shouldPrintBoard(){
        ChessBoard board = new ChessBoard();
        board.printBoard();
    }
}
