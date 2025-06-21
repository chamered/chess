package board;

import pieces.*;

public class BoardImpl implements Board {

    private Piece[][] board;
    private Color currentTurn = Color.WHITE;

    public BoardImpl() {
        setupBoard();
    }

    public void setupBoard() {
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
    public boolean movePiece(Position from, Position to) {
        Piece piece = getPieceAt(from);

        if (piece == null || piece.getCOLOR() != currentTurn) {
            return false;
        }

        if (!isMoveValid(from, to)) {
            System.out.println("Move is invalid.");
            return false;
        }

        setPieceAt(from, null);
        setPieceAt(to, piece);

        switchPlayer();

        return true;
    }

    @Override
    public boolean isMoveValid(Position from, Position to) {
        return false;
    }

    @Override
    public boolean inCheck() {
        return false;
    }

    @Override
    public boolean isCheckmate() {
        return false;
    }

    @Override
    public boolean isStalemate() {
        return false;
    }

    @Override
    public Color getCurrentPlayer() {
        return currentTurn;
    }

    @Override
    public void switchPlayer() {
        currentTurn = (currentTurn == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    @Override
    public void resetBoard() {
        setupBoard();
    }

    @Override
    public boolean canCastle() {
        return false;
    }

    @Override
    public boolean isEnPassantPossible() {
        return false;
    }

    public static void main(String[] args) {
        BoardImpl board = new BoardImpl();
        board.printBoard();
        board.movePiece(new Position(6, 7), new Position(4, 7));
        board.printBoard();
    }
}