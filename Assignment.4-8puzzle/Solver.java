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
import edu.princeton.cs.algs4.Queue;

public class Solver {
    private final int distance;
    private final MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
    private Iterable<Board> solutionBoards;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        distance = initial.manhattan();
        SearchNode firstNode = new SearchNode(distance, null, initial);
        pq.insert(firstNode);
        solutionBoards = this.solution();
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return (distance == 0);
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        int counter = 0;
        for (Board b : solutionBoards)
            counter++;
        return counter;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        Queue<Board> boards = new Queue<Board>();
        while (true) {
            SearchNode minNode = pq.delMin();
            Board minBoard = minNode.current;
            boards.enqueue(minBoard);
            if (minBoard.isGoal())
                break;
            for (Board aNeighbor : minBoard.neighbors()) {
                if ((minNode.previous == null) || (!aNeighbor.equals(minNode.previous.current))) {
                    SearchNode sn = new SearchNode(aNeighbor.manhattan(), minNode, aNeighbor);
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
