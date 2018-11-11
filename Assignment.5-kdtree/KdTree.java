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
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import java.lang.Double;

public class KdTree {
    private int numPoints;
    private KdTreeNode root;

    private class KdTreeNode implements Comparable<KdTreeNode> {
        private boolean isXAxis;
        private Point2D point;
        KdTreeNode left;
        KdTreeNode right;

        public KdTreeNode(Point2D p, boolean xAxis) {
            this.isXAxis = xAxis;
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
        if ((p != null) && !contains(p)) {
            root = insert(root, p, true);
            numPoints++;
        }
    }

    private KdTreeNode insert(KdTreeNode node, Point2D p, boolean isXAxis) {
        if (node == null) {
            KdTreeNode newNode = new KdTreeNode(p,isXAxis);
            return newNode;
        }
        else if (isXAxis) {
            if (p.x() < node.point.x()) {
                node.left = insert(node.left, p, !isXAxis);
            }
            else {
                node.right = insert(node.right, p, !isXAxis);
            }
        }
        else {
            if (p.y() < node.point.y()) {
                node.left = insert(node.left, p, !isXAxis);
            }
            else {
                node.right = insert(node.right, p, !isXAxis);
            }
        }
        return node;
    }

    // does the set contain point p?
    /// @Todo: Fill this
    public boolean contains(Point2D p) {
        return contains(root, p, true);
    }

    private boolean contains(KdTreeNode node, Point2D p, boolean isXAxis) {
        if (node == null)
            return false;
        int cmpX = Double.compare(p.x(), node.point.x());
        int cmpY = Double.compare(p.y(), node.point.y());
        if ((cmpX==0) && (cmpY==0)) {
            return true;
        }
        else {
            if (isXAxis) {
                if (cmpX < 0) {
                    return contains(node.left, p, !isXAxis);
                }
                else {
                    return contains(node.right, p, !isXAxis);
                }
            }
            else {
                if (cmpY < 0) {
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
        KdTree initial = new KdTree();
        StdOut.printf("size 0 = %d \n", initial.size());
        initial.insert(new Point2D(0.75,0.875));
        StdOut.printf("size A = %d \n", initial.size());
        initial.insert(new Point2D(0.875,0.75));
        StdOut.printf("size B = %d \n", initial.size());
        initial.insert(new Point2D(0.875,0.125));
        StdOut.printf("size C = %d \n", initial.size());
        initial.insert(new Point2D(0.375,0.25));
        StdOut.printf("size D = %d \n", initial.size());
        initial.insert(new Point2D(0.5,0.75));
        StdOut.printf("size E = %d \n", initial.size());
        initial.insert(new Point2D(0.625,0.25));
        StdOut.printf("size F = %d \n", initial.size());
        initial.insert(new Point2D(0.0,0.375));
        StdOut.printf("size G = %d \n", initial.size());
        initial.insert(new Point2D(0.875,0.5));
        StdOut.printf("size H = %d \n", initial.size());
        initial.insert(new Point2D(0.375,0.125));
        StdOut.printf("size I = %d \n", initial.size());
        initial.insert(new Point2D(0.375,0.125));
        StdOut.printf("size J = %d \n", initial.size());
    }
}
