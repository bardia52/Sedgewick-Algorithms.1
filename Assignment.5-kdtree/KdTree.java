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
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Queue;

public class KdTree {
    private SET<kdTreeNode> pointTree;
    private int numPoints;

    private class kdTreeNode implements Comparable<kdTreeNode> {
        private boolean xAxis;
        private Point2D point;
        kdTreeNode left;
        kdTreeNode right;

        public kdTreeNode(Point2D p) {
            this.point = p;
            this.left = null;
            this.right = null;
        }

        public int compareTo(kdTreeNode that) {
            if (xAxis) {
                if (this.point.x() < that.point.x()) return -1;
                else if (this.point.x() < that.point.x()) return +1;
                else return 0;
            }
            else {
                if (this.point.y() < that.point.y()) return -1;
                else if (this.point.y() < that.point.y()) return +1;
                else return 0;
            }
        }
    }

    // construct an empty set of points
    public KdTree() {
        this.numPoints = 0;
        this.pointTree = new SET<kdTreeNode>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return (numPoints == 0);
    }

    // number of points in the set
    public int size() {
        return numPoints;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return true;
    }

    // draw all points to standard draw
    public void draw() {
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> p2 = new Queue<Point2D>();
        return p2;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return p;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }
}
