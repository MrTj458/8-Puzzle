public class SearchNode {
    private int[][] board;
    private int hamming;
    private int moves;
    private int priority;
    private SearchNode previous;

    public SearchNode(int[][] board, int hamming, int moves, int priority, SearchNode previous) {
        this.board = board;
        this.hamming = hamming;
        this.moves = moves;
        this.priority = priority;
        this.previous = previous;
    }

    public SearchNode(int[][] board, int hamming, int moves, int priority) {
        this.board = board;
        this.hamming = hamming;
        this.moves = moves;
        this.priority = priority;
        this.previous = null;
    }

    public int[][] getBoard() {
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
}
