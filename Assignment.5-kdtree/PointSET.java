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

public class PointSET {
    private SET<Point2D> pointTree;
    private int numPoints;

    // construct an empty set of points
    public PointSET() {
        this.numPoints = 0;
        this.pointTree = new SET<Point2D>();
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
        if (!pointTree.contains(p)) {
            pointTree.add(p);
            numPoints++;
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return pointTree.contains(p);
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
        double minDistance = 2;
        Point2D minPoint = p;
        for (Point2D neighbor : pointTree) {
            double curDistance = distance(p, neighbor);
            if (curDistance < minDistance) {
                minDistance = curDistance;
                minPoint = neighbor;
            }
        }
        return minPoint;
    }

    private double distance(Point2D p1, Point2D p2) {
        double xD = p1.x() - p2.x();
        double yD = p1.y() - p2.y();
        return (xD*xD) + (yD*yD);
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }
}
