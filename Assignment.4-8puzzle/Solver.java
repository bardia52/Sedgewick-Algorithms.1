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
import edu.princeton.cs.algs4.MinPQ;
// import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private final int distance;
    private final Board initBoard;
    private final Stack<Board> boards;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        boards = new Stack<Board>();
        distance = initial.manhattan();
        initBoard = initial;

        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        SearchNode firstNode = new SearchNode(initBoard, 0, null);
        pq.insert(firstNode);
        while (true) {
            SearchNode minNode = pq.delMin();
            // Check to see if in the same path
            Board minBoard = minNode.board;
            // reversedBoards.push(minBoard);
            if (minBoard.isGoal()) {
                this.boards.push(minBoard);
                while (minNode.previous != null) {
                    minNode = minNode.previous;
                    this.boards.push(minNode.board);
                }
                return;
            }
            minNode.moves++;
            // StdOut.print(minNode.moves + ": " + minBoard.manhattan() + " - " + minBoard.toString());
            Iterable<Board> neighbors = minBoard.neighbors();
            for (Board neighbor : neighbors) {
                if ((minNode.previous == null) || (!neighbor.equals(minNode.previous.board))) {
                    SearchNode sn = new SearchNode(neighbor, minNode.moves, minNode);
                    pq.insert(sn);
                }
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return (distance == 0);
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return boards.size() - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return this.boards;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final int priority;
        private final SearchNode previous;
        private final Board board;
        private int moves;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.priority = this.moves + board.manhattan();
        }

        @Override
        public int compareTo(SearchNode that) {
            if (this.priority < that.priority) {
                return -1;
            }
            if (this.priority > that.priority) {
                return +1;
            }
            return 0;
        }
    }
}
