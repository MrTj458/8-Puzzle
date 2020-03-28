import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Contains the main method of the application and the methods needed to "solve"
 * the puzzle boards.
 * 
 * @author Trevor Hodsdon, Jake Ombach
 * 
 */
public class Solver {
	private SearchNode initial; // puzzle board in initial state
	private SearchNode finalNode; // puzzle board in goal state
	private int moves; // number of moves made to reach current board
	static long start; // start timer to measure execution time

	/**
	 * Constructs the "object" to solve the puzzle board problem.
	 * 
	 * @param initial : initial board created and passed into the algorithm.
	 */
	public Solver(Board initial) {
		this.initial = new SearchNode(initial, initial.hamming(), 0, null);
		finalNode = solve();
		moves = finalNode.getMoves();
	}

	/**
	 * Private method to solve the searchNode using either the hamming or manhattan
	 * priority by using a min priority queue to determine which moves are the most
	 * efficient moves to solve the puzzle.
	 * 
	 * @return : a searchNode with the lowest priority out of all the potential
	 *         different searchNodes that can be created by moving the blank piece
	 *         of the current puzzle board.
	 */
	private SearchNode solve() {
		// MinPQ<SearchNode> queue = new MinPQ<>();
		MinPQ<SearchNode> queue = new MinPQ<>(SearchNode.BY_MANHATTAN);
		queue.insert(initial);

		while (true) {
			SearchNode current = queue.delMin();
			if (current.getBoard().isGoal()) {
				return current;
			}
			for (Board b : current.getBoard().neighbors()) {
				if (current.getPrevious() == null || !b.equals(current.getPrevious().getBoard()))
					queue.insert(new SearchNode(b, b.hamming(), current.getMoves() + 1, current));
			}
		}
	}

	/**
	 * @return : the number of moves that have already been made in the game
	 */
	public int moves() {
		return moves;
	}

	/**
	 * @return : all of the puzzle boards (implemented as a stack) that were a part
	 *         of the step by step solution from initial board to goal board
	 */
	public Iterable<Board> solution() {
		Stack<Board> stack = new Stack<>();

		SearchNode current = finalNode;
		while (current != null) {
			stack.push(current.getBoard());
			current = current.getPrevious();
		}

		return stack;
	}

	public static void main(String[] args) {
		// create initial board from file
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();

		start = System.currentTimeMillis();
		Board initial = new Board(blocks);

		// check if puzzle is solvable; if so, solve it and output solution
		if (initial.isSolvable()) {
			Solver solver = new Solver(initial);
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}

		// if not, report unsolvable
		else {
			StdOut.println("Unsolvable puzzle");
		}
	}
}
