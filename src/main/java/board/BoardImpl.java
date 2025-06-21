package board;

import pieces.*;
import game.Move;

public class BoardImpl implements Board {

    private Piece[][] board;

    public BoardImpl() {
        setupBoard();
    }

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
        System.out.println("    a b c d e f g h\n");

        for (int i = 0; i < 8; i++) {
            System.out.print(8 - i + "   ");
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece != null) System.out.print(piece.getSYMBOL() + " ");
                else System.out.print("X ");
            }
            System.out.println("  " + (8 - i));
        }

        System.out.println("\n    a b c d e f g h");
    }

    //Method to generate an hypothetical move for simulation purposes
    public void makeMove(Move move) {
        Piece piece = getPieceAt(move.getFrom());
        setPieceAt(move.getFrom(), null);
        setPieceAt(move.getTo(), piece);
    }

    //Copy of the board
    public BoardImpl copy() {
        BoardImpl newBoard = new BoardImpl();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null) {
                    newBoard.board[row][col] = piece.copy();
                }
            }
        }

        return newBoard;
    }

    public Position findKingPosition(Color color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece instanceof King &&
                        piece.getCOLOR() == color) {
                    return new Position(row, col);
                }
            }
        }

        // This should never happen in a legal game state
        throw new IllegalStateException("King of color " + color + " not found on the board.");
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
    public void resetBoard() {
        setupBoard();
    }
}