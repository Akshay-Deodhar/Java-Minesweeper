package org.practice.minesweeper;

import java.util.Scanner;

public class Game {
	private Board gameBoard;
	private Scanner sc = null;
	private int length;
	private int breadth;
	private int mines;
	private int row, col;

	public static void main(String[] args) throws Exception {
		Game game = new Game();
		game.play();
		return;
	}

	private void play() throws Exception {
		sc = new Scanner(System.in);
		System.out.println("Starting a game. Please input game parameters");
		System.out.print("Board Length: ");
		length = sc.nextInt();
		System.out.print("Board Length: ");
		breadth = sc.nextInt();
		System.out.print("Number of Mines: ");
		mines = sc.nextInt();

		try {
			System.out.println("Initializing the game board...");
			gameBoard = new Board(length, breadth, mines);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			sc.close();
			return;
		}

		System.out.println("The board is ready.");
		gameBoard.printBoard();
		String str;
		while (!gameBoard.isComplete()) {
			System.out.println("Input your move, or input 'h' for help.");
			str = sc.next();
			switch (str.toLowerCase()) {
			case "uncover":
			case "u":
				handleUncoverMove();
				break;
			case "flag":
			case "f":
				handleFlagMove();
				break;
			case "help":
			case "h":
				handleHelpMove();
				break;
			case "exit":
			case "e":
				handleExitMove();
				break;
			default:
				System.out.println("Unrecognized move. Please try again.");
				break;
			}
			System.out.println("Board is now:");
			gameBoard.printBoard();
		}
		if (gameBoard.isExploded) {
			System.out.println("Game over. Thanks for playing!");
		} else {
			System.out.println("You Win! Thanks for playing!");
		}
		sc.close();
	}

	private void handleHelpMove() {
		System.out.println("uncover/u: Uncovers a location. Requires a row and col index.");
		System.out.println("flag/f: Flags a location. Requires a row and col index.");
		System.out.println("help/h: Provides help about functions.");
		System.out.println("exit/e: Exits the game.");
	}

	private void handleFlagMove() throws Exception {
		System.out.println("Flagging a possible mine.");
		System.out
				.print("Please input row index between 0 and " + length + ": or enter any other number to to cancel:");
		row = sc.nextInt();
		if (row < 0 || row >= length) {
			return;
		}
		System.out.print(
				"Please input column index between 0 and " + breadth + ": or enter any other number to to cancel:");
		col = sc.nextInt();
		if (col < 0 || col >= breadth) {
			return;
		}
		gameBoard.flagCell(row, col);
		return;
	}

	private void handleUncoverMove() {
		System.out.println("Uncovering a cell.");
		System.out
				.print("Please input row index between 0 and " + length + ": or enter any other number to to cancel:");
		row = sc.nextInt();
		if (row < 0 || row >= length) {
			return;
		}
		System.out.print(
				"Please input column index between 0 and " + breadth + ": or enter any other number to to cancel:");
		col = sc.nextInt();
		if (col < 0 || col >= breadth) {
			return;
		}
		gameBoard.uncoverCell(row, col);
	}

	private void handleExitMove() {
		System.out.println("Exiting the game. Thanks for playing!");
		System.exit(0);
	}
}
