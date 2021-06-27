package org.practice.minesweeper;

import java.util.Arrays;

public class BoardUtils implements BoardInterface {
	public static char[][] getBlankBoard(int rows, int cols) {
		char[][] mask = new char[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				mask[i][j] = BLANK;
			}
		}
		return mask;
	}

	public static char[][] getSolution(int rows, int cols, int mines) {
		char[][] solution = new char[rows][cols];
		for (char[] arr : solution) {
			Arrays.fill(arr, CLEAR);
		}
		int count = 0, i, j;
		while (count < mines) {
			i = (int) (rows * Math.random());
			j = (int) (cols * Math.random());
			if (solution[i][j] == MINE) {
				continue;
			} else {
				solution[i][j] = MINE;
				count++;
			}
		}
		for (i = 0; i < rows; i++) {
			for (j = 0; j < cols; j++) {
				if (solution[i][j] == MINE) {
					continue;
				} else {
					solution[i][j] = getSurroundingMines(solution, i, j);
				}
			}
		}
		return solution;
	}

	private static char getSurroundingMines(char[][] solution, int row, int col) {
		int count = 0;
		char ch;
		// checking
		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = col - 1; j <= col + 1; j++) {
				if (j == col & i == row) {
					continue;
				}
				ch = getMatrixCharacter(solution, i, j);
				if (ch == MINE) {
					count++;
				}
			}
		}
		ch = (char) (count + CLEAR);
		return ch;
	}

	public static char getMatrixCharacter(char[][] matrix, int row, int col) {
		try {
			return matrix[row][col];
		} catch (Exception e) {
			return BORDER;
		}
	}
}
