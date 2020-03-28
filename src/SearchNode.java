import java.util.Comparator;

public class SearchNode implements Comparable<SearchNode> {
    public static Comparator<SearchNode> BY_MANHATTAN = new ByManhattan();
    private Board board;
    private int hamming;
    private int moves;
    private int priority;
    private SearchNode previous;

    public SearchNode(Board board, int hamming, int moves, SearchNode previous) {
        this.board = board;
        this.hamming = hamming;
        this.moves = moves;
        this.previous = previous;
    }

    public Board getBoard() {
        return this.board;
    }

    public int getHamming() {
        return this.hamming;
    }

    public int getMoves() {
        return this.moves;
    }

    public int getPriority() {
        return this.priority;
    }

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
