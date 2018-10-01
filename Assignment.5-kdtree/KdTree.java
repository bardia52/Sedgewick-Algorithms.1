/******************************************************************************
 *  Name:    Bardia Alavi
 *  NetID:   bardia
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description: 
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
// import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Queue;

public class KdTree {
    public KdTree() {                             // construct an empty set of points
    }

    public boolean isEmpty() {                    // is the set empty?
        return true;
    }

    public int size() {                           // number of points in the set
        return 0;
    }

    public void insert(Point2D p) {               // add the point to the set (if it is not already in the set)
    }

    public boolean contains(Point2D p) {          // does the set contain point p?
        return true;
    }

    public void draw() {                          // draw all points to standard draw
    }

    public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle (or on the boundary)
        Queue<Point2D> p2 = new Queue<Point2D>();
        return p2;
    }

    public Point2D nearest(Point2D p) {           // a nearest neighbor in the set to point p; null if the set is empty
        return p;
    }

    public static void main(String[] args) {      // unit testing of the methods (optional)
    }
}
