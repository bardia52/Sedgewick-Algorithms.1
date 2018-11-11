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
    private int numPoints;
    private KdTreeNode root;

    private class KdTreeNode implements Comparable<KdTreeNode> {
        private boolean isXAxis;
        private Point2D point;
        KdTreeNode left;
        KdTreeNode right;

        public KdTreeNode(Point2D p) {
            this.isXAxis = true;
            this.point = p;
            this.left = null;
            this.right = null;
        }

        public int compareTo(KdTreeNode that) {
            if (isXAxis) {
                if (this.point.x() < that.point.x()) return -1;
                else if (this.point.x() > that.point.x()) return +1;
                else return 0;
            }
            else {
                if (this.point.y() < that.point.y()) return -1;
                else if (this.point.y() > that.point.y()) return +1;
                else return 0;
            }
        }
    }

    // construct an empty set of points
    public KdTree() {
        this.numPoints = 0;
        this.root = null;
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
    /// @Todo: Fill this
    public void insert(Point2D p) {
        if (!contains(p)) {
            //KdTreeNode newNode = new KdTreeNode(p);
            //pointTree.add(newNode);
            numPoints++;
        }
    }

    // does the set contain point p?
    /// @Todo: Fill this
    public boolean contains(Point2D p) {
        return contains(root, p, true);
    }

    private boolean contains(KdTreeNode node, Point2D p, boolean isXAxis) {
        if (node == null)
            return false;
        else if (node.point == p) {
            return true;
        }
        else {
            if (isXAxis) {
                if (p.x() < node.point.x()) {
                    return contains(node.left, p, !isXAxis);
                }
                else {
                    return contains(node.right, p, !isXAxis);
                }
            }
            else {
                if (p.x() < node.point.y()) {
                    return contains(node.left, p, !isXAxis);
                }
                else {
                    return contains(node.right, p, !isXAxis);
                }
            }
        }
    }

    // draw all points to standard draw
    public void draw() {
        /// @to-do: Fill this 
    }

    // all points that are inside the rectangle (or on the boundary)
    /// @Todo: Fill this
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> p2 = new Queue<Point2D>();
        return p2;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    /// @Todo: Fill this
    public Point2D nearest(Point2D p) {
        return p;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        /// @to-do: Fill this 
    }
}
