import java.util.Random;

public class SudokuGenerator {
    private final int[][] board = new int[9][9];
    private final Random rand = new Random();

    public int[][] generateBoard() {
        fillBoard(0, 0);
        removeCells(40); // Remover 40 células para criar o puzzle
        return board;
    }

    private boolean fillBoard(int row, int col) {
        if (row == 9) return true;
        if (col == 9) return fillBoard(row + 1, 0);

        int[] numbers = rand.ints(1, 10).distinct().limit(9).toArray();

        for (int num : numbers) {
            if (isSafe(row, col, num)) {
                board[row][col] = num;
                if (fillBoard(row, col + 1)) return true;
                board[row][col] = 0;
            }
        }

        return false;
    }

    private boolean isSafe(int row, int col, int num) {
        for (int i = 0; i < 9; i++)
            if (board[row][i] == num || board[i][col] == num)
                return false;

        int boxRow = row - row % 3, boxCol = col - col % 3;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[boxRow + i][boxCol + j] == num)
                    return false;

        return true;
    }

    private void removeCells(int count) {
        for (int i = 0; i < count; ) {
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);
            if (board[row][col] != 0) {
                board[row][col] = 0;
                i++;
            }
        }
    }
}
