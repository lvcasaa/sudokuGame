public class Main {
    public static void main(String[] args) {
        SudokuGenerator generator = new SudokuGenerator();
        int[][] board = generator.generateBoard();
        new SudokuGUI(board);
    }
}