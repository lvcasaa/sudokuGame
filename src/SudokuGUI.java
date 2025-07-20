import java.awt.*;
import javax.swing.*;

public class SudokuGUI extends JFrame {
    private final JTextField[][] cells = new JTextField[9][9];
    private int[][] originalBoard;

    public SudokuGUI(int[][] board) {
        this.originalBoard = deepCopy(board);

        setTitle("Joguinho de Sudoku");
        setSize(700, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        Font font = new Font("SansSerif", Font.BOLD, 20);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(font);

                if (board[row][col] != 0) {
                    cell.setText(String.valueOf(board[row][col]));
                    cell.setEditable(false);
                    cell.setBackground(new Color(220, 220, 220));
                }

                cells[row][col] = cell;
                gridPanel.add(cell);
            }
        }

        // Botões
        JPanel buttonPanel = new JPanel();
        JButton checkButton = new JButton("Verificar");
        JButton resetButton = new JButton("Resetar");

        checkButton.addActionListener(e -> verificarTabuleiro());
        resetButton.addActionListener(e -> resetarTabuleiro());

        buttonPanel.add(checkButton);
        buttonPanel.add(resetButton);

        add(gridPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void verificarTabuleiro() {
        int[][] userBoard = new int[9][9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String text = cells[row][col].getText();
                if (text.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Existem células em branco!");
                    return;
                }

                try {
                    int value = Integer.parseInt(text);
                    if (value < 1 || value > 9) {
                        JOptionPane.showMessageDialog(this, "Valores inválidos detectados.");
                        return;
                    }
                    userBoard[row][col] = value;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Entrada inválida detectada.");
                    return;
                }
            }
        }

        if (isValidSudoku(userBoard)) {
            JOptionPane.showMessageDialog(this, "🎉 Parabéns! Você completou o Sudoku corretamente!");
        } else {
            JOptionPane.showMessageDialog(this, "❌ O Sudoku contém erros.");
        }
    }

    private void resetarTabuleiro() {
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++) {
                if (originalBoard[row][col] == 0) {
                    cells[row][col].setText("");
                    cells[row][col].setEditable(true);
                    cells[row][col].setBackground(Color.WHITE);
                } else {
                    cells[row][col].setText(String.valueOf(originalBoard[row][col]));
                    cells[row][col].setEditable(false);
                    cells[row][col].setBackground(new Color(220, 220, 220));
                }
            }
    }

    private boolean isValidSudoku(int[][] board) {
        // Verifica linhas, colunas e caixas 3x3
        for (int i = 0; i < 9; i++) {
            boolean[] rowCheck = new boolean[10];
            boolean[] colCheck = new boolean[10];

            for (int j = 0; j < 9; j++) {
                int rowVal = board[i][j];
                int colVal = board[j][i];

                if (rowCheck[rowVal]) return false;
                if (colCheck[colVal]) return false;

                rowCheck[rowVal] = true;
                colCheck[colVal] = true;
            }
        }

        for (int boxRow = 0; boxRow < 9; boxRow += 3) {
            for (int boxCol = 0; boxCol < 9; boxCol += 3) {
                boolean[] boxCheck = new boolean[10];

                for (int row = 0; row < 3; row++) {
                    for (int col = 0; col < 3; col++) {
                        int val = board[boxRow + row][boxCol + col];
                        if (boxCheck[val]) return false;
                        boxCheck[val] = true;
                    }
                }
            }
        }

        return true;
    }

    private int[][] deepCopy(int[][] original) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++)
            System.arraycopy(original[i], 0, copy[i], 0, 9);
        return copy;
    }
}
