/******************************************************************************
 *  Name:    Bardia Alavi
 *  NetID:   bardia
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description: A BruteForce solution for finding collinear points in a set of points
 ******************************************************************************/

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points)
    {
        // Check corner cases
        checkNullEntries(points);
        checkDuplicatedEntries(points);
        ArrayList<LineSegment> foundSegments = new ArrayList<>();

        // We need a copy because we are sorting
        Point[] pointsCopy = Arrays.copyOf(points, points.length);

        // Sorting points based on compareTo (first Y, then X)
        Arrays.sort(pointsCopy);

        // Loop over 4 points
        for (int p = 0; p < pointsCopy.length - 3; p++) {
            for (int q = p + 1; q < pointsCopy.length - 2; q++) {
                for (int r = q + 1; r < pointsCopy.length - 1; r++) {
                    for (int s = r + 1; s < pointsCopy.length; s++) {
                        double pqSlope = pointsCopy[p].slopeTo(pointsCopy[q]);
                        double prSlope = pointsCopy[p].slopeTo(pointsCopy[r]);
                        double psSlope = pointsCopy[p].slopeTo(pointsCopy[s]);
                        if ((pqSlope == prSlope) && (pqSlope == psSlope)) {
                            foundSegments.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
                        }
                    }
                }
            }
        }

        segments = foundSegments.toArray(new LineSegment[foundSegments.size()]);
    }

    // the number of line segments
    public int numberOfSegments()
    {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments()
    {
        return Arrays.copyOf(segments, numberOfSegments());
    }

    private void checkNullEntries(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Null Entry");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("Null Entry");
            }
        }
    }

    private void checkDuplicatedEntries(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException(
                    "Duplicated entries in given points.");
                }
            }
        }
    }

}
