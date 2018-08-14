import edu.princeton.cs.algs4.Queue;

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

public class Solver {
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return true;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return 0;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        Queue<Board> boards = new Queue<Board>();
        return boards;
    }
}
