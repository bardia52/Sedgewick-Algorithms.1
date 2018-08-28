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
    private int moves = 0;

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
        int score = 0;
        int value = 0;
        int goalValue = 0;
        for (int i = 0; i < boardDim; i++) {
            for (int j = 0; j < boardDim; j++) {
                value = internalBlocks[i][j];
                goalValue = (i * boardDim) + j + 1;
                score += (value == goalValue) ? 0 : 1;
            }
        }
        if (value != goalValue) // subtract last value
            score--;
        return score;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int score = 0;
        for (int i = 0; i < boardDim; i++) {
            for (int j = 0; j < boardDim; j++) {
                int addValue = 0;
                int value = internalBlocks[i][j];
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
                score += addValue;
            }
        }
        return score;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return (this.hamming() == 0);
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        Board twinBoard = new Board(this);
        Point firstPoint = findANoneZeroPoint();
        Point secondPoint = findNextNoneZeroPoint(firstPoint);
        swapPoints(twinBoard, firstPoint, secondPoint);
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
        for (int i = 0; i < this.boardDim; i++) {
            for (int j = 0; j < this.boardDim; j++) {
                if (this.internalBlocks[i][j] != that.internalBlocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> boards = new Queue<Board>();
        Board newBoard = moveLeft();
        newBoard.moves = this.moves + 1;
        if (!equals(newBoard))
            boards.enqueue(newBoard);
        newBoard = moveRight();
        newBoard.moves = this.moves + 1;
        if (!equals(newBoard))
            boards.enqueue(newBoard);
        newBoard = moveUp();
        newBoard.moves = this.moves + 1;
        if (!equals(newBoard))
            boards.enqueue(newBoard);
        newBoard = moveDown();
        newBoard.moves = this.moves + 1;
        if (!equals(newBoard))
            boards.enqueue(newBoard);
        return boards;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        String strBoard = this.boardDim + "\n";
        StringBuilder sblBoard = new StringBuilder(strBoard);
        for (int i = 0; i < this.boardDim; i++) {
            sblBoard.append(" ");
            for (int j = 0; j < this.boardDim; j++) {
                sblBoard.append(internalBlocks[i][j]);
                sblBoard.append(" ");
            }
            sblBoard.append("\n");
        }
        return sblBoard.toString();
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
            Board twinBoard = initial.twin();
            String strA = initial.toString();
            String strB = twinBoard.toString();
            // Solver solver = new Solver(initial);
            StdOut.println(filename + ": hamming = " + hammingScore + " : mahnatan = " + manhatanScore);
            StdOut.print(strA);
            StdOut.print(strB);
            // StdOut.println(filename + ": " + solver.moves());
        }
        return;
    }

    private void swapPoints(Board board, Point point1, Point point2) {
        int i1 = point1.geti();
        int j1 = point1.getj();
        int i2 = point2.geti();
        int j2 = point2.getj();
        int temp = board.internalBlocks[i1][j1];
        board.internalBlocks[i1][j1] = board.internalBlocks[i2][j2];
        board.internalBlocks[i2][j2] = temp;
    }

    private int findiZero() {
        for (int i = 0; i < this.boardDim; i++) {
            for (int j = 0; j < this.boardDim; j++) {
                if (this.internalBlocks[i][j] == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    private int findjZero() {
        for (int i = 0; i < this.boardDim; i++) {
            for (int j = 0; j < this.boardDim; j++) {
                if (this.internalBlocks[i][j] == 0) {
                    return j;
                }
            }
        }
        return -1;
    }

    private Point findANoneZeroPoint() {
        Point noneZeroPoint = new Point(0, 0, this.boardDim);
        for (int i = 0; i < this.boardDim; i++) {
            for (int j = 0; j < this.boardDim; j++) {
                if (internalBlocks[i][j] != 0) {
                    noneZeroPoint.setPoint(i, j);
                    break;
                }
            }
        }
        return noneZeroPoint;
    }

    private Point findNextNoneZeroPoint(Point p) {
        Point nextPoint = new Point(p);
        int i = p.geti();
        int j = p.getj();
        if (checkLegit(i + 1, j))
            nextPoint.setPoint(i + 1, j);
        else if (checkLegit(i - 1, j))
            nextPoint.setPoint(i - 1, j);
        else if (checkLegit(i, j + 1))
            nextPoint.setPoint(i, j + 1);
        else
            nextPoint.setPoint(i, j - 1);
        return nextPoint;
    }

    private boolean checkLegit(int i, int j) {
        return ((i >= 0) && (i < this.boardDim) && (j >= 0) && (j < this.boardDim) 
                && this.internalBlocks[i][j] != 0);
    }

    private Board moveLeft() {
        Board movedBoard = new Board(this);
        int iZero = findiZero();
        int jZero = findjZero();
        if (jZero > 0) {
            movedBoard.internalBlocks[iZero][jZero] = this.internalBlocks[iZero][jZero - 1];
            movedBoard.internalBlocks[iZero][jZero-1] = 0;
        }
        return movedBoard;
    }

    private Board moveRight() {
        Board movedBoard = new Board(this);
        int iZero = findiZero();
        int jZero = findjZero();
        if (jZero < this.boardDim-1) {
            movedBoard.internalBlocks[iZero][jZero] = this.internalBlocks[iZero][jZero + 1];
            movedBoard.internalBlocks[iZero][jZero + 1] = 0;
        }
        return movedBoard;
    }

    private Board moveUp() {
        Board movedBoard = new Board(this);
        int iZero = findiZero();
        int jZero = findjZero();
        if (iZero > 0) {
            movedBoard.internalBlocks[iZero][jZero] = this.internalBlocks[iZero - 1][jZero];
            movedBoard.internalBlocks[iZero - 1][jZero] = 0;
            // movedBoard.hamming();
            // movedBoard.manhattan();
        }
        return movedBoard;
    }

    private Board moveDown() {
        Board movedBoard = new Board(this);
        int iZero = findiZero();
        int jZero = findjZero();
        if (iZero < this.boardDim-1) {
            movedBoard.internalBlocks[iZero][jZero] = this.internalBlocks[iZero + 1][jZero];
            movedBoard.internalBlocks[iZero + 1][jZero] = 0;
        }
        return movedBoard;
    }

    private int abs(int x) {
        int y = (x >= 0) ? x : -x;
        return y;
    }

    private class Point {
        private int i;
        private int j;
        private final int dim;

        public Point(int i, int j, int dim) {
            this.dim = dim;
            if ((i >= 0) && (i < dim) && (j >= 0) && (j < dim)) {
                this.i = i;
                this.j = j;
            }
            else {
                throw new IllegalArgumentException("Threw an IllegalArgumentException");
            }
        }

        public Point(Point p) {
            this.dim = p.dim;
            this.i = p.i;
            this.j = p.j;
        }

        public int geti() {
            return this.i;
        }

        public int getj() {
            return this.j;
        }

        public int setPoint(int x, int y) {
            if ((x >= 0) && (x < this.dim) && (y >= 0) && (y < this.dim)) {
                this.i = x;
                this.j = y;
                return 0;
            }
            else {
                throw new IllegalArgumentException("Threw an IllegalArgumentException");
            }
        }
    }
}
