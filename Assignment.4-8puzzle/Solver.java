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
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private final boolean solvable;    // Is this board solvable or not
    private final Stack<Board> boards; // Sequence of boards

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Null Entry");
        }
        boards = new Stack<Board>();
        Board initBoard = initial;
        Board twinBoard = initBoard.twin();

        // Do everything the same for twin board
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> pqTwin = new MinPQ<SearchNode>();
        SearchNode firstNode = new SearchNode(initBoard, 0, null);
        SearchNode firstNodeTwin = new SearchNode(twinBoard, 0, null);
        pq.insert(firstNode);
        pqTwin.insert(firstNodeTwin);
        while (true) {
            SearchNode minNode = pq.delMin();
            SearchNode minNodeTwin = pqTwin.delMin();

            Board minBoardTwin = minNodeTwin.board;
            if (minBoardTwin.isGoal()) {
                this.solvable = false;
                return;
            }
            Board minBoard = minNode.board;
            if (minBoard.isGoal()) {
                this.boards.push(minBoard);
                while (minNode.previous != null) {
                    minNode = minNode.previous;
                    this.boards.push(minNode.board);
                }
                this.solvable = true;
                return;
            }
            minNode.addMoves();
            minNodeTwin.addMoves();
            Iterable<Board> neighbors = minBoard.neighbors();
            for (Board neighbor : neighbors) {
                if ((minNode.previous == null) || (!neighbor.equals(minNode.previous.board))) {
                    SearchNode sn = new SearchNode(neighbor, minNode.moves, minNode);
                    pq.insert(sn);
                }
            }
            Iterable<Board> neighborsTwin = minBoardTwin.neighbors();
            for (Board neighborTwin : neighborsTwin) {
                if ((minNodeTwin.previous == null) || (!neighborTwin.equals(minNodeTwin.previous.board))) {
                    SearchNode sn = new SearchNode(neighborTwin, minNodeTwin.moves, minNodeTwin);
                    pqTwin.insert(sn);
                }
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return boards.size() - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solvable)
            return this.boards;
        else
            return null;
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

        public void addMoves() {
            this.moves++;
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
