import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Constructs the puzzle boards and the underlying characteristics of the boards
 * including its size, hamming and manhattan priorities and whether or not the
 * board is solvable.
 * 
 * @author Trevor Hodsdon, Jake Ombach
 */
public class Board {
	private int[][] board;
	private int[][] goalBoard;

	/**
	 * Constructs a puzzle board game object
	 * 
	 * @param blocks : the 2d array representing the pieces of the puzzle board
	 */
	public Board(int[][] blocks) {
		this.board = blocks;
		this.goalBoard = new int[blocks.length][blocks[0].length];

		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (r == goalBoard.length - 1 && c == goalBoard.length - 1) {
					goalBoard[r][c] = 0;
				} else {
					goalBoard[r][c] = (r * board[0].length + (c + 1));
				}
			}
		}
	}

	/**
	 * @return a copy of the puzzle board to reference
	 */
	private int[][] copy() {
		int[][] boardCopy = new int[board.length][board.length];

		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board.length; c++) {
				boardCopy[r][c] = board[r][c];
			}
		}

		return boardCopy;
	}

	/**
	 * @return the number of pieces in the puzzle board
	 */
	public int size() {
		return board.length * board[0].length;
	}

	/**
	 * @return the hamming priority of the board
	 */
	public int hamming() {
		int ham = 0;

		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c] != (r * board[0].length + (c + 1))) {
					ham++;
				}
			}
		}

		return ham - 1;
	}

	/**
	 * @return the manhattan priority of the board.
	 */
	public int manhattan() {
		int man = 0;

		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c] != (r * board[0].length + (c + 1))) {
					for (int i = 0; i < goalBoard.length; i++) {
						for (int j = 0; j < goalBoard[0].length; j++) {
							if (board[r][c] == goalBoard[i][j]) {
								man += Math.abs(r - i) + Math.abs(c - j);
							}
						}
					}
				}
			}
		}

		return man;
	}

	public int findRow() {

		List<Integer> list = new ArrayList<>();
		double blankInArray = 0;
		double numOfColumns = board.length;
		double numOfBlocks = board.length * board.length;
		int row = 0;

		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board.length; c++) {
				list.add(board[r][c]);
				if (board[r][c] == 0) {
					blankInArray = list.size() - 1;
				}
			}
		}

		double x = (blankInArray / numOfBlocks);

		for (int i = 0; i < numOfColumns; i++) {
			if (x > i * numOfColumns / numOfBlocks && x < (i + 1) * numOfColumns / numOfBlocks)
				row = i;
		}

		return row;
	}

	/**
	 * @return : true/false whether the current board is solved
	 */
	public boolean isGoal() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c] != goalBoard[r][c]) {
					return false;
				}
			}
		}

		long end = System.currentTimeMillis();
		long totalTime = (end - Solver.start) / 1000;
		StdOut.println("The execution time took " + totalTime + " seconds");
		return true;
	}

	/**
	 * @return : true/false whether the board can be solved or not
	 */
	public boolean isSolvable() {
		List<Integer> list = new ArrayList<>();

		// Turn board into 1d array
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board.length; c++) {
				if (board[r][c] != 0) {
					list.add(board[r][c]);
				}
			}
		}

		int invCount = 0;

		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < i; j++) {
				if (list.get(i) < list.get(j)) {
					invCount++;
				}
			}
		}

		if (board.length % 2 == 0) {
			if ((invCount % 2 != 0 && findRow() % 2 == 0)) {
				return true;
			} else if (invCount % 2 == 0 && findRow() % 2 != 0) {
				return true;
			}
		} else {
			return invCount % 2 == 0;
		}
		return false;

	}

	public boolean equals(Object y) {
		if (y == this)
			return true;
		if (y == null || y.getClass() != this.getClass())
			return false;
		Board other = (Board) y;
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c] != other.board[r][c]) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * @return : a stack of all of the potential boards (up to 4) that can be made
	 *         from the different movements of the blank piece in the board.
	 */
	public Iterable<Board> neighbors() {
		Stack<Board> list = new Stack<>();
		int[] space = findSpace();

		int spaceRow = space[0];
		int spaceCol = space[1];

		if (spaceRow > 0)
			list.push(new Board(swap(spaceRow, spaceCol, spaceRow - 1, spaceCol)));
		if (spaceRow < board.length - 1)
			list.push(new Board(swap(spaceRow, spaceCol, spaceRow + 1, spaceCol)));
		if (spaceCol > 0)
			list.push(new Board(swap(spaceRow, spaceCol, spaceRow, spaceCol - 1)));
		if (spaceCol < board.length - 1)
			list.push(new Board(swap(spaceRow, spaceCol, spaceRow, spaceCol + 1)));

		return list;
	}

	/**
	 * Find the row and column that the empty space is in
	 * 
	 * @return index 0 is the row index 1 is the column of the space.
	 */
	private int[] findSpace() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board.length; c++) {
				if (board[r][c] == 0) {
					return new int[] { r, c };
				}
			}
		}
		return null;
	}

	/**
	 * Swaps the pieces in the board
	 */
	private int[][] swap(int r1, int c1, int r2, int c2) {
		int[][] copy = copy();

		int tmp = copy[r1][c1];
		copy[r1][c1] = copy[r2][c2];
		copy[r2][c2] = tmp;

		return copy;
	}

	/**
	 * @return : string representation of what the goal board should look like.
	 */
	public String printGoalBoard() {
		int N = board.length;
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", goalBoard[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	@Override
	public String toString() {
		int N = board.length;
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", board[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	public static void main(String[] args) {
		int[][] blocks = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
		Board board = new Board(blocks);

		System.out.println("INITIAL:");
		System.out.println(board);

		System.out.println("NEIGHBORS:");
		Iterable<Board> neighbors = board.neighbors();
		for (Board n : neighbors) {
			System.out.println(n);
		}
	}
}
