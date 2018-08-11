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

public class Board {
    private final int[][] internalBlocks;
    private final int boardDim;
    private int hammingScore = 0;

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

    // board dimension n
    public int dimension() {
        return this.boardDim;
    }

    // number of blocks out of place
    public int hamming() {
        int newScore = 0;
        int value = 0;
        for (int i = 0; i < boardDim; i++) {
            for (int j = 0; j < boardDim; j++) {
                value = internalBlocks[i][j];
                newScore += (value == (i * boardDim) + j) ? 0 : 1;
            }
        }
        if (value == 0) // subtract last value
            newScore--;
        return (newScore + hammingScore);
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return true;
    }

    // a board that is obtained by exchanging any pair of blocks
    /*public Board twin() {
        return new Board(int [this.boardDim][this.boardDim]);
    }*/

    // does this board equal y?
    public boolean equals(Object y) {
        return true;
    }

    // all neighboring boards
/*    public Iterable<Board> neighbors() {
        
    }
*/
    // string representation of this board (in the output format specified below)
    public String toString() {
        return "1";
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        return;
    }
}
