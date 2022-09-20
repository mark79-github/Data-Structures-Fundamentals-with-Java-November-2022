package implementations;

public class TheMatrix {
    private final char[][] matrix;
    private final char fillChar;
    private final char toBeReplaced;
    private final int startRow;
    private final int startCol;

    public TheMatrix(char[][] matrix, char fillChar, int startRow, int startCol) {
        this.matrix = matrix;
        this.fillChar = fillChar;
        this.startRow = startRow;
        this.startCol = startCol;
        this.toBeReplaced = this.matrix[this.startRow][this.startCol];
    }

    public void solve() {
        traceMatrix(startRow, startCol);
    }

    private void traceMatrix(int row, int col) {
        this.matrix[row][col] = fillChar;
        if (isAvailable(row + 1, col)) {
            traceMatrix(row + 1, col);
        }
        if (isAvailable(row - 1, col)) {
            traceMatrix(row - 1, col);
        }
        if (isAvailable(row, col + 1)) {
            traceMatrix(row, col + 1);
        }
        if (isAvailable(row, col - 1)) {
            traceMatrix(row, col - 1);
        }
    }

    private boolean isAvailable(int row, int col) {
        return row >= 0 && row < matrix.length && col >= 0 && col < this.matrix[row].length && matrix[row][col] == toBeReplaced;
    }

    public String toOutputString() {
        StringBuilder builder = new StringBuilder();
        for (char[] chars : matrix) {
            for (char aChar : chars) {
                builder.append(aChar);
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString().trim();
    }
}
