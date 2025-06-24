import board.BoardImpl;
import board.Position;
import game.Move;
import game.RulesEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pieces.Color;

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
}
