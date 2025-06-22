package board;

import pieces.*;
import game.Move;

/*
ANSI Escape Codes for Text Color
Reset: \u001B[0m
Blue: \u001B[34m
Cyan: \u001B[36m
Red: \u001B[31m
Green: \u001B[32m
White: \u001B[37m
Yellow: \u001B[33m
Black: \u001B[30m
Magenta: \u001B[35m
 */

public class BoardImpl implements Board {
    private Piece[][] board;

    // First constructor
    public BoardImpl() {
        setupBoard();
    }

    // Second constructor
    public BoardImpl(Piece[][] board) {
        this.board = board;
    }

    // Initializes the board to the standard chess starting position
    private void setupBoard() {
        board = new Piece[8][8];

        board[0][0] = new Rook(Color.BLACK);
        board[0][1] = new Knight(Color.BLACK);
        board[0][2] = new Bishop(Color.BLACK);
        board[0][3] = new Queen(Color.BLACK);
        board[0][4] = new King(Color.BLACK);
        board[0][5] = new Bishop(Color.BLACK);
        board[0][6] = new Knight(Color.BLACK);
        board[0][7] = new Rook(Color.BLACK);
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(Color.BLACK);
        }

        board[7][0] = new Rook(Color.WHITE);
        board[7][1] = new Knight(Color.WHITE);
        board[7][2] = new Bishop(Color.WHITE);
        board[7][3] = new Queen(Color.WHITE);
        board[7][4] = new King(Color.WHITE);
        board[7][5] = new Bishop(Color.WHITE);
        board[7][6] = new Knight(Color.WHITE);
        board[7][7] = new Rook(Color.WHITE);
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn(Color.WHITE);
        }
    }

    @Override
    public void printBoard() {
        final String BLACK = "\u001B[34m";
        final String WHITE = "\u001B[33m";
        final String RESET = "\u001B[0m";

        System.out.println("    a b c d e f g h\n");

        for (int i = 0; i < 8; i++) {
            System.out.print(8 - i + "   ");
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece != null) {
                    String colorCode = (piece.getColor() == Color.WHITE) ? WHITE : BLACK;
                    System.out.print(colorCode + piece.getSymbol() + RESET + " ");
                }
                else System.out.print("X ");
            }
            System.out.println("  " + (8 - i));
        }

        System.out.println("\n    a b c d e f g h");
    }

    @Override
    public Piece[][] getBoard() {
        return board;
    }

    @Override
    public Piece getPieceAt(Position pos) {
        return board[pos.getRow()][pos.getColumn()];
    }

    @Override
    public void setPieceAt(Position pos, Piece piece) {
        board[pos.getRow()][pos.getColumn()] = piece;
    }

    @Override
    public void makeMove(Move move) {
        Piece piece = getPieceAt(move.getFrom());
        setPieceAt(move.getFrom(), null);
        setPieceAt(move.getTo(), piece);
    }

    @Override
    public Position getKingPosition(Color color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece instanceof King &&
                        piece.getColor() == color) {
                    return new Position(row, col);
                }
            }
        }

        // This should never happen in a legal game state
        throw new IllegalStateException("King of color " + color + " not found on the board.");
    }

    @Override
    public void resetBoard() {
        setupBoard();
    }

    @Override
    public BoardImpl copy() {
        Piece[][] boardCopy = board.clone();
        return new BoardImpl(boardCopy);
    }
}