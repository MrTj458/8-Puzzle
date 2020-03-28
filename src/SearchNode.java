import java.util.Comparator;

/**
 * Constructs the SearchNode objects and contains the getters, Comparator, and
 * compareTo method needed to compare the objects.
 * 
 * @author Trevor Hodsdon, Jake Ombach
 */
public class SearchNode implements Comparable<SearchNode> {
	public static Comparator<SearchNode> BY_MANHATTAN = new ByManhattan(); // comparator to search by Manhattan
	private Board board;
	private int hamming;
	private int moves;
	private int priority;
	private SearchNode previous;

	/**
	 * Constructs a searchNode made up of the puzzleboard and attributes.
	 * 
	 * @param board     : puzzle board object
	 * @param hamming   : hamming priority of puzzle board
	 * @param moves     : number of moves used to reach current puzzle board
	 * @param previous: previous puzzle board prior to current
	 */
	public SearchNode(Board board, int hamming, int moves, SearchNode previous) {
		this.board = board;
		this.hamming = hamming;
		this.moves = moves;
		this.previous = previous;
	}

	/**
	 * @return : current board in play
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * @return hamming priority of current board
	 */
	public int getHamming() {
		return this.hamming;
	}

	/**
	 * @return number of moves to reach current board
	 */
	public int getMoves() {
		return this.moves;
	}

	/**
	 * @return prioriity of current board
	 */
	public int getPriority() {
		return this.priority;
	}

	/**
	 * @return the previous search node
	 */
	public SearchNode getPrevious() {
		return this.previous;
	}

	@Override
	public int compareTo(SearchNode o) {
		return (this.hamming + this.moves) - (o.hamming + o.moves);
	}

	private static class ByManhattan implements Comparator<SearchNode> {

		@Override
		public int compare(SearchNode o1, SearchNode o2) {
			return (o1.board.manhattan() + o1.moves) - (o2.board.manhattan() + o2.moves);
		}

	}

}
