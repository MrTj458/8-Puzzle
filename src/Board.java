import java.util.ArrayList;
import java.util.List;

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
        return null;
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
        int[][] blocks = { { 1, 2, 3 }, { 4, 0, 5 }, { 7, 8, 6 } };
        Board board = new Board(blocks);

        System.out.println(board.isSolvable());

    }
}
