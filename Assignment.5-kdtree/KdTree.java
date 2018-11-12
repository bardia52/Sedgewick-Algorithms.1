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
        if (p == null)
            throw new IllegalArgumentException("Null Entry");
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
        if (p == null)
            throw new IllegalArgumentException("Null Entry");
        return contains(root, p, true);
    }

    private boolean contains(KdTreeNode node, Point2D p, boolean isXAxis) {
        if (node == null)
            return false;
        int cmpX = Double.compare(p.x(), node.point.x());
        int cmpY = Double.compare(p.y(), node.point.y());
        if ((cmpX == 0) && (cmpY == 0)) {
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

    private int intersects(RectHV rect, KdTreeNode node) {
        if (node.isXAxis) {
            if (rect.xmax() < node.point.x())
                return -1;
            else if (rect.xmin() > node.point.x())
                return 1;
            else
                return 0;
        }
        else {
            if (rect.ymax() < node.point.y())
                return -1;
            else if (rect.ymin() > node.point.y())
                return 1;
            else
                return 0;
        }
    }

    // draw all points to standard draw
    public void draw() {
        /// @to-do: Fill this 
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("Null Entry");
        return range(root, rect);
    }

    private Iterable<Point2D> range(KdTreeNode node, RectHV rect) {
        if (node == null)
            return null;
        Queue<Point2D> containedPoints = new Queue<Point2D>();
        if (rect.contains(node.point))
            containedPoints.enqueue(node.point);

        if (intersects(rect, node) == 1) {
            Iterable<Point2D> rightSet = range(node.right, rect);
            if (rightSet != null)
                for (Point2D point : rightSet)
                    containedPoints.enqueue(point);
        }
        else if (intersects(rect, node) == -1) {
            Iterable<Point2D> leftSet = range(node.left, rect);
            if (leftSet != null)
                for (Point2D point : leftSet)
                    containedPoints.enqueue(point);
        }
        else {
            Iterable<Point2D> leftSet = range(node.left, rect);
            if (leftSet != null)
                for (Point2D point : leftSet)
                    containedPoints.enqueue(point);
            Iterable<Point2D> rightSet = range(node.right, rect);
            if (rightSet != null)
                for (Point2D point : rightSet)
                    containedPoints.enqueue(point);
        }
        return containedPoints;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    /// @Todo: Fill this
    public Point2D nearest(Point2D query) {
        if (query == null)
            throw new IllegalArgumentException("Null Entry");
        if (root == null)
            throw new IllegalArgumentException("Null Tree");
        return nearest(root, query, null);
    }

    private Point2D nearest(KdTreeNode node, Point2D query, Point2D currentBestPoint) {
        Point2D closestPoint = null;
        if (currentBestPoint == null) {
            closestPoint = node.point;
        }
        else if (node.point.distanceTo(query) < currentBestPoint.distanceTo(query)) {
            closestPoint = node.point;
        }
        else {
            closestPoint = currentBestPoint;
        }
        double shortestDistance = closestPoint.distanceTo(query);
        if (node.isXAxis) {
            if ((query.x() + shortestDistance < node.point.x()) && (node.left != null)) {
                // Look at left rectangle only
                closestPoint = nearest(node.left, query, closestPoint);
            }
            else if ((query.x() - shortestDistance > node.point.x()) && (node.right != null)) {
                // Look at right rectangle only
                closestPoint = nearest(node.right, query, closestPoint);
            }
            else {
                // Look at both left and right rectangle
                // Look at the first point that is on the same side of query point
                if (query.x() < node.point.x()) {
                    if (node.left != null)
                        closestPoint = nearest(node.left, query, closestPoint);
                    if (node.right != null)
                        closestPoint = nearest(node.right, query, closestPoint);
                }
                else {
                    if (node.right != null)
                        closestPoint = nearest(node.right, query, closestPoint);
                    if (node.left != null)
                        closestPoint = nearest(node.left, query, closestPoint);
                }
            }
        }
        else {
            if ((query.y() + shortestDistance < node.point.y()) && (node.left != null)) {
                // Don't worry about right rectangle
                // Look at left rectangle
                closestPoint = nearest(node.left, query, closestPoint);
            }
            else if ((query.y() - shortestDistance > node.point.y()) && (node.right != null)) {
                // Don't worry about left rectangle
                // Look at right rectangle
                closestPoint = nearest(node.right, query, closestPoint);
            }
            else {
                // Look at both left and right rectangle
                // Look at the first point that is on the same side of query point
                if (query.y() < node.point.y()) {
                    if (node.left != null)
                        closestPoint = nearest(node.left, query, closestPoint);
                    if (node.right != null)
                        closestPoint = nearest(node.right, query, closestPoint);
                }
                else {
                    if (node.left != null)
                        closestPoint = nearest(node.left, query, closestPoint);
                    if (node.right != null)
                        closestPoint = nearest(node.right, query, closestPoint);
                }
            }
        }
        return closestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        /// @to-do: Fill this
        KdTree myTree = new KdTree();
        StdOut.printf("size 0 = %d \n", myTree.size());
//        myTree.insert(new Point2D(0.75,0.875));
//        StdOut.printf("size A = %d \n", myTree.size());
//        myTree.insert(new Point2D(0.875,0.75));
//        StdOut.printf("size B = %d \n", myTree.size());
//        myTree.insert(new Point2D(0.875,0.125));
//        StdOut.printf("size C = %d \n", myTree.size());
//        myTree.insert(new Point2D(0.375,0.25));
//        StdOut.printf("size D = %d \n", myTree.size());
//        myTree.insert(new Point2D(0.5,0.75));
//        StdOut.printf("size E = %d \n", myTree.size());
//        myTree.insert(new Point2D(0.625,0.25));
//        StdOut.printf("size F = %d \n", myTree.size());
//        myTree.insert(new Point2D(0.0,0.375));
//        StdOut.printf("size G = %d \n", myTree.size());
//        myTree.insert(new Point2D(0.875,0.5));
//        StdOut.printf("size H = %d \n", myTree.size());
//        myTree.insert(new Point2D(0.375,0.125));
//        StdOut.printf("size I = %d \n", myTree.size());
//        myTree.insert(new Point2D(0.375,0.125));
//        StdOut.printf("size J = %d \n", myTree.size());

        myTree.insert(new Point2D(0.7, 0.2));
        StdOut.printf("size A = %d \n", myTree.size());
        myTree.insert(new Point2D(0.5, 0.4));
        StdOut.printf("size B = %d \n", myTree.size());
        myTree.insert(new Point2D(0.2, 0.3));
        StdOut.printf("size C = %d \n", myTree.size());
        myTree.insert(new Point2D(0.4, 0.7));
        StdOut.printf("size D = %d \n", myTree.size());
        myTree.insert(new Point2D(0.9, 0.6));
        StdOut.printf("size E = %d \n", myTree.size());
        StdOut.printf("Range for [0.378, 0.69] x [0.667, 0.958]: %s\n", myTree.range(new RectHV(0.378, 0.39, 0.667, 0.958)));
        StdOut.printf("Nearest query point (0.74, 0.87): %s\n", myTree.nearest(new Point2D(0.74, 0.87)));

//        myTree.insert(new Point2D(0.5, 0.5));
//        StdOut.printf("size A = %d \n", myTree.size());
//        StdOut.printf("Nearest query point (0.037, 0.035): %s\n", myTree.nearest(new Point2D(0.037, 0.035)));
    }
}
