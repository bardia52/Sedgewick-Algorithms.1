/******************************************************************************
 *  Name:    Bardia Alavi
 *  NetID:   bardia
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description: A data-type to represent board having the 8-puzzle (n^2-1) puzzle.
 ******************************************************************************/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int[][] internalBlocks;
    private final int boardDim;
    private int hammingScore = 0;
    private int manhattanScore = 0;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        int m = blocks.length;
        int n = blocks[0].length;
        if (m != n) {
            throw new IllegalArgumentException("Non Equal Entry");
        }
        internalBlocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.internalBlocks[i][j] = blocks[i][j];
            }
        }
        this.boardDim = n;
    }

    private Board(Board x) {
        int n = x.dimension();
        internalBlocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.internalBlocks[i][j] = x.internalBlocks[i][j];
            }
        }
        this.boardDim = n;
    }

    // board dimension n
    public int dimension() {
        return this.boardDim;
    }

    // number of blocks out of place
    public int hamming() {
        int newScore = 0;
        int value = 0;
        int goalValue = 0;
        for (int i = 0; i < boardDim; i++) {
            for (int j = 0; j < boardDim; j++) {
                value = internalBlocks[i][j];
                goalValue = (i * boardDim) + j + 1;
                newScore += (value == goalValue) ? 0 : 1;
            }
        }
        if (value != goalValue) // subtract last value
            newScore--;
        return (newScore + hammingScore);
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int newScore = 0;
        int value = 0;
        int addValue = 0;
        for (int i = 0; i < boardDim; i++) {
            for (int j = 0; j < boardDim; j++) {
                value = internalBlocks[i][j];
                int goalValue = (i * boardDim) + j + 1;
                if ((value == goalValue) || (value == 0)) {
                    addValue = 0;
                }
                else {
                    // Find destination coordinate
                    int iVal = (value - 1) / boardDim;
                    int jVal = (value - 1) % boardDim;
                    addValue = abs(iVal - i) + abs(jVal - j);
                }
                newScore += addValue;
            }
        }
        return (newScore + manhattanScore);
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (this.hamming() == 0)
            return true;
        else
            return false;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        Board twinBoard = new Board(this);
        return twinBoard;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        // self check
        if (this == y)
            return true;
        // null check
        if (y == null)
            return false;
        // type check and cast
        if (getClass() != y.getClass())
            return false;
        Board that = (Board) y;
        if (this.boardDim != that.dimension())
            return false;
        for (int i = 0; i < this.boardDim; i++)
            for (int j = 0; j < this.boardDim; j++) {
                if (this.internalBlocks[i][j] != that.internalBlocks[i][j]) {
                    return false;
                }
            }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> boards = new Queue<Board>();
        return boards;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        return "1";
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        // for each command-line argument
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            int hammingScore = initial.hamming();
            int manhatanScore = initial.manhattan();
            // Solver solver = new Solver(initial);
            StdOut.println(filename + ": hamming = " + hammingScore + " : mahnatan = " + manhatanScore);
            // StdOut.println(filename + ": " + solver.moves());
        }
        return;
    }

    private int abs(int x) {
        int y = (x >= 0) ? x : -x;
        return y;
    }
}
