package org.practice.minesweeper;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class Board implements BoardInterface {

	private final int length, breadth, nbrMines;
	private int flaggedMines = 0;
	private int flagsRemaining = 0;
	private char[][] solution;
	private char[][] board;
	public boolean isExploded = false;

	public Board(int p_length, int p_breadth, int p_nbrMines) throws Exception {
		if (p_nbrMines > (int) (MAX_CAPACITY_RATIO * p_length * p_breadth)) {
			throw new IllegalArgumentException("Too many mines");
		}
		if (p_nbrMines < (int) (MIN_CAPACITY_RATIO * p_length * p_breadth)) {
			throw new IllegalArgumentException("Too few mines");
		}
		this.length = p_length;
		this.breadth = p_breadth;
		this.nbrMines = p_nbrMines;
		this.solution = BoardUtils.getSolution(this.length, this.breadth, this.nbrMines);
		this.board = BoardUtils.getBlankBoard(this.length, this.breadth);
		this.flaggedMines = 0;
		this.flagsRemaining = this.nbrMines;
	}

	public void printBoard() {
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < breadth; j++) {
				if (j != 0) {
					System.out.print(" ");
				}
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}

	public boolean isComplete() {
		return isExploded || (flaggedMines == nbrMines);
	}

	public void flagCell(int p_row, int p_col) throws Exception {
		char ch = BoardUtils.getMatrixCharacter(this.board, p_row, p_col);
		if (ch == BORDER) {
			System.out.println("Location (" + p_row + ", " + p_col + ") is outside board boundaries.");
			return;
		}
		if (ch == FLAG) {
			this.flagsRemaining++;
			this.board[p_row][p_col] = BLANK;
			if (BoardUtils.getMatrixCharacter(this.solution, p_row, p_col) == MINE) {
				flaggedMines--;
			}
			System.out.println();
			return;
		} else if (ch == BLANK) {
			if (flagsRemaining == 0) {
				System.out.println("All flags have been used. Remove some and try again");
				return;
			}
			this.flagsRemaining--;
			this.board[p_row][p_col] = FLAG;
			if (BoardUtils.getMatrixCharacter(this.solution, p_row, p_col) == MINE) {
				flaggedMines++;
			}
		} else {
			System.out.println("Flag cannot be placed at location (" + p_row + ", " + p_col + ").");
			return;
		}
	}

	public void uncoverCell(int p_row, int p_col) {
		char ch = BoardUtils.getMatrixCharacter(board, p_row, p_col);
		if (ch == BORDER) {
			System.out.println("Location (" + p_row + ", " + p_col + ") is outside board boundaries.");
			return;
		}
		if (ch == FLAG) {
			System.out.println("Location (" + p_row + ", " + p_col + ") is flagged and cannot be uncovered.");
			return;
		} else if (ch >= CLEAR & ch < EIGHT) {
			System.out.println("Location (" + p_row + ", " + p_col + ") is cleared and cannot be uncovered.");
			return;
		} else {
			ch = BoardUtils.getMatrixCharacter(solution, p_row, p_col);
			if(ch == MINE) {
				isExploded = true;
				processExplodedBoard(p_row, p_col);
				System.out.println("Location (" + p_row + ", " + p_col + ") contained a mine. Better luck next time!");
				return;
			} else {
				 uncoverBoardSection(p_row, p_col);
				 System.out.println("Location (" + p_row + ", " + p_col + ") was clear. Onward!");
			}
		}
	}

	private void uncoverBoardSection(int p_row, int p_col) {
		if(BoardUtils.getMatrixCharacter(board, p_row, p_col) == BORDER) {
			return;
		}
		Deque<int[]> stack = new ArrayDeque<>();
		Set<int[]> visited = new HashSet<>();
		int[] curr, temp;
		stack.push(new int[] {p_row, p_col});
		while(!stack.isEmpty()) {
			curr = stack.pop();
			board[curr[0]][curr[1]] = solution [curr[0]][curr[1]];
			visited.add(curr);
			if(board[curr[0]][curr[1]] != CLEAR) {
				continue;
			}
			/* process the 8 surrounding cells of curr if solution of curr is clear.
			 * if board cell is uncovered or flagged or outside boundaries, skip.
			 * if cell has already been visited, skip
			 * if board cell is blank, add to queue. 
			 */
			char ch;
			for(int i=curr[0]-1;i<=curr[0]+1;i++) {
				for(int j=curr[1]-1;j<=curr[1]+1;j++) {
					temp = new int[] {i,j};
					if(visited.contains(temp) || stack.contains(temp)) {
						continue;
					}
					ch = BoardUtils.getMatrixCharacter(board, i, j);
					if(ch != BLANK) {
						continue;
					}
					stack.push(temp);
				}
			}
		}
	}

	private void processExplodedBoard(int p_row, int p_col) {
		for(int i=0;i<length;i++) {
			for(int j=0;j<length;j++) {
				if(i == p_row & j == p_col) {
					board[i][j] = BOOM;
					continue;
				}
				if(solution[i][j] == MINE & board[i][j] != FLAG) {
					board[i][j] = MINE;
				}
			}
		}
		return;
	}
}
