public class SearchNode implements Comparable<SearchNode> {
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
}
