import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Stack;

public class Board {
    private int[][] board;
    private int[][] goalBoard;

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

    private int[][] copy() {
        int[][] boardCopy = new int[board.length][board.length];

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                boardCopy[r][c] = board[r][c];
            }
        }

        return boardCopy;
    }

    public int size() {
        return board.length * board[0].length;
    }

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

    public boolean isGoal() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] != goalBoard[r][c]) {
                    return false;
                }
            }
        }
        return true;
    }

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

        return invCount % 2 == 0;
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

    private int[][] swap(int r1, int c1, int r2, int c2) {
        int[][] copy = copy();

        int tmp = copy[r1][c1];
        copy[r1][c1] = copy[r2][c2];
        copy[r2][c2] = tmp;

        return copy;
    }

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
