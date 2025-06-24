/*--------------------------------------------------------------------
 *  Improved BoardTest — JUnit 5
 *-------------------------------------------------------------------*/
import board.BoardImpl;
import board.Position;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pieces.*;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@DisplayName("BoardImpl – canonical behaviour test‑suite")
class BoardTest {

    private BoardImpl board;   // fresh board injected before every test

    /*------------------------------------------------------------*/
    /*  LIFECYCLE                                                 */
    /*------------------------------------------------------------*/
    @BeforeEach
    void setUp() {
        board = new BoardImpl();
    }

    /*------------------------------------------------------------*/
    /*  1. BOARD INITIALISATION                                   */
    /*------------------------------------------------------------*/
    @Test
    @DisplayName("Initial position contains the correct arrangement of major pieces & pawns")
    void initialBoardLayout_isCorrect() {

        // Assertions grouped by piece‑type for better failure reporting
        assertAll("Major pieces",
            // Knights
            () -> assertKnight(new Position(0, 1)),
            () -> assertKnight(new Position(0, 6)),
            () -> assertKnight(new Position(7, 1)),
            () -> assertKnight(new Position(7, 6)),

            // Rooks
            () -> assertRook(new Position(0, 0)),
            () -> assertRook(new Position(0, 7)),
            () -> assertRook(new Position(7, 0)),
            () -> assertRook(new Position(7, 7)),

            // Bishops
            () -> assertBishop(new Position(0, 2)),
            () -> assertBishop(new Position(0, 5)),
            () -> assertBishop(new Position(7, 2)),
            () -> assertBishop(new Position(7, 5)),

            // Queen & King (note: Queen on d‑file)
            () -> assertInstanceOf(Queen.class, piece(0, 3)),
            () -> assertInstanceOf(Queen.class, piece(7, 3)),
            () -> assertInstanceOf(King.class,  piece(0, 4)),
            () -> assertInstanceOf(King.class,  piece(7, 4))
        );

        // Pawns – iterate programmatically, one assertion per file
        IntStream.range(0, 8).forEach(col -> {
            assertAll("Pawns @" + col,
                () -> assertPawn(new Position(1, col)),
                () -> assertPawn(new Position(6, col))
            );
        });
    }

    /*------------------------------------------------------------*/
    /*  2. ILLEGAL COORDINATES                                    */
    /*------------------------------------------------------------*/
    @ParameterizedTest(name = "[{index}] invalid pos → {0}")
    @MethodSource("invalidPositions")
    @DisplayName("getPieceAt returns null for out‑of‑bounds coordinates")
    void getPieceAt_withInvalidPosition_returnsNull(Position invalid) {
        Assertions.assertNull(board.getPieceAt(invalid));
    }
    private static Stream<Position> invalidPositions() {
        return Stream.of(
            new Position(-2, -4),
            new Position(-1, -1),
            new Position(-100, 99)
        );
    }

    /*------------------------------------------------------------*/
    /*  3. PIECE MOVEMENT API                                     */
    /*------------------------------------------------------------*/
    @Test
    @DisplayName("setPieceAt moves a piece correctly and clears its source square")
    void setPieceAt_movesPiece() {
        Position a8 = new Position(0, 0);
        Position a7 = new Position(1, 0);
        Piece rook = piece(a8);

        assertAll(
            () -> assertInstanceOf(Rook.class, rook),
            () -> board.setPieceAt(a7, rook),
            () -> board.setPieceAt(a8, null),
            () -> Assertions.assertNull(piece(a8)),
            () -> Assertions.assertEquals(rook, piece(a7))
        );
    }

    /*------------------------------------------------------------*/
    /*  4. KING POSITION TRACKING                                 */
    /*------------------------------------------------------------*/
    @Test
    @DisplayName("getKingPosition tracks the King when it moves")
    void kingPosition_updatesAfterMove() {
        Position whiteStart = new Position(7, 4);
        Position target     = new Position(6, 4);

        Assertions.assertEquals(whiteStart, board.getKingPosition(Color.WHITE));

        board.setPieceAt(target, piece(whiteStart));
        board.setPieceAt(whiteStart, null);

        Assertions.assertEquals(target, board.getKingPosition(Color.WHITE));
    }

    /*------------------------------------------------------------*/
    /*  5. RESET FUNCTIONALITY                                    */
    /*------------------------------------------------------------*/
    @Test
    @DisplayName("resetBoard restores canonical starting layout after arbitrary moves")
    void resetBoard_restoresInitialSetup() {
        // scramble a few pieces
        board.setPieceAt(new Position(1,3), piece(0, 3));  board.setPieceAt(new Position(0,3), null);
        board.setPieceAt(new Position(6,2), piece(7, 2));  board.setPieceAt(new Position(7,2), null);

        // verify the scramble took effect
        Assertions.assertAll(
            () -> Assertions.assertNull(piece(0,3)),
            () -> Assertions.assertNull(piece(7,2)),
            () -> assertNotNullPiece(1,3),
            () -> assertNotNullPiece(6,2)
        );

        board.resetBoard();  // action
        initialBoardLayout_isCorrect();  // reuse logic
    }

    /*------------------------------------------------------------*/
    /*  6. DEEP COPY                                              */
    /*------------------------------------------------------------*/
    @Test
    @DisplayName("copy() produces a deep clone, independent of original board")
    void copy_producesDeepClone() {
        // simple move to differentiate
        board.setPieceAt(new Position(1,0), piece(0, 0));
        board.setPieceAt(new Position(0,0), null);

        BoardImpl cloned = board.copy();

        Assertions.assertAll(
            () -> assertInstanceOf(Rook.class, cloned.getPieceAt(new Position(1,0))),
            () -> Assertions.assertNull(cloned.getPieceAt(new Position(0,0))),
            () -> { // mutate original, expect clone unaffected
                   board.setPieceAt(new Position(2,0), piece(1, 0));
                   Assertions.assertNotEquals(board.getPieceAt(new Position(2,0)),
                                              cloned.getPieceAt(new Position(2,0)));
            }
        );
    }

    /*------------------------------------------------------------*/
    /*  Helper Methods                                            */
    /*------------------------------------------------------------*/
    private Piece piece(int row, int col) { return board.getPieceAt(new Position(row, col)); }
    private Piece piece(Position p)       { return board.getPieceAt(p);                    }

    private void assertKnight(Position p){ assertInstanceOf(Knight.class, piece(p)); }
    private void assertRook(Position p)  { assertInstanceOf(Rook.class,   piece(p)); }
    private void assertBishop(Position p){ assertInstanceOf(Bishop.class, piece(p)); }
    private void assertPawn(Position p)  { assertInstanceOf(Pawn.class,   piece(p)); }

    private void assertNotNullPiece(int r, int c) {
        Assertions.assertNotNull(piece(r, c), "Expected piece at " + r + "," + c);
    }
}
