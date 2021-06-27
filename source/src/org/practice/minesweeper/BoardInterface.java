package org.practice.minesweeper;

public interface BoardInterface {
	public static final char MINE = '*';
	public static final char FLAG = 'F';
	public static final char BOOM = 'X';
	public static final char BLANK = '.';
	public static final char CLEAR = '0';
	public static final char ONE = '1';
	public static final char TWO = '2';
	public static final char THREE = '3';
	public static final char FOUR = '4';
	public static final char FIVE = '5';
	public static final char SIX = '6';
	public static final char SEVEN = '7';
	public static final char EIGHT = '8';
	public static final char BORDER = 'B';
	public static final char WRONG = 'W';

	public static final double MAX_CAPACITY_RATIO = 0.5;
	public static final double MIN_CAPACITY_RATIO = 0.1;
}
