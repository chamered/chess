package board;

public class Position {
    private int row;
    private int column;

    // Constructor
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Set a new value for row.
     * @param value the value to set
     */
    public void setRow(int value) {
        this.row = value;
    }

    /**
     * Returns the position row.
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Set a new value for column.
     * @param value the value to set
     */
    public void setColumn(int value) {
        this.column = value;
    }

    /**
     * Returns the position column.
     * @return the column
     */
    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}