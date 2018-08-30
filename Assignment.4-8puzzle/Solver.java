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
//import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private final int distance;
    private final Board initBoard;
    private int numMoves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        distance = initial.manhattan();
        initBoard = initial;
        numMoves = 0;
        Iterable<Board> solutionBoards = solution();
        for (Board b : solutionBoards)
            numMoves++;
        numMoves--;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return (distance == 0);
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return numMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        SearchNode firstNode = new SearchNode(distance, null, initBoard);
        pq.insert(firstNode);
        //Queue<Board> boards = new Queue<Board>();
        Stack<Board> boards = new Stack<Board>();
        while (true) {
            SearchNode minNode = pq.delMin();
            // Check to see if in the same path
            if (minNode.previous != null) {
                Board lastBoard = boards.pop();
                if (!minNode.previous.current.equals(lastBoard)) {
                    // Go back till get the parent path
                    while (!minNode.previous.current.equals(lastBoard)) {
                        if (boards.size() > 0)
                            lastBoard = boards.pop();
                        else
                            minNode = minNode.previous;
                    }
                }
                boards.push(lastBoard);
            }
            Board minBoard = minNode.current;
            boards.push(minBoard);
            if (minBoard.isGoal())
                break;
            StdOut.print(boards.size() + ": " + minBoard.manhattan() + " - " + minBoard.toString());
            for (Board neighbor : minBoard.neighbors()) {
                if ((minNode.previous == null) || (!neighbor.equals(minNode.previous.current))) {
                    SearchNode sn = new SearchNode(neighbor.manhattan()+boards.size(), minNode, neighbor);
                    pq.insert(sn);
                }
            }
        }
        return boards;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final int priority;
        private final SearchNode previous;
        private final Board current;

        public SearchNode(int priority, SearchNode previous, Board current) {
            this.priority = priority;
            this.previous = previous;
            this.current = current;
        }
        
        public int compare(SearchNode v, SearchNode w) {
            return (v.getPriority() >= w.getPriority()) ? 1 : 0;
        }

        public int compareTo(SearchNode sn) {
            return compare(this, sn);
        }

        public int getPriority() {
            return this.priority;
        }
    }
}
