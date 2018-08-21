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
import java.util.Comparator;

public class Solver {
    private final int distance;
    private final MinPQ<SearchNode> minPQ = new MinPQ();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        distance = initial.manhattan();
        SearchNode firstNode = new SearchNode(distance, null, initial);
        // minPQ = new MinPQ(firstNode);
        minPQ.insert(firstNode);
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return (distance == 0);
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

class SearchNode {
    private final int priority;
    private final SearchNode previous;
    private final Board current;

    public SearchNode(int priority, SearchNode previous, Board current) {
        this.priority = priority;
        this.previous = previous;
        this.current = current;
    }
    
    public int getPriority() {
        return this.priority;
    }
}

class SortNode implements Comparator<SearchNode> {
    
    public int compare(SearchNode v, SearchNode w) {
        return (v.getPriority() >= w.getPriority()) ? 1 : 0;
    }
}
