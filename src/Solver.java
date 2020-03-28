import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private SearchNode initial;
    private SearchNode finalNode;
    private int moves;

    public Solver(Board initial) {
        this.initial = new SearchNode(initial, initial.hamming(), 0, null);
        finalNode = solve();
        moves = finalNode.getMoves();
    }

    private SearchNode solve() {
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

    public int moves() {
        return moves;
    }

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
