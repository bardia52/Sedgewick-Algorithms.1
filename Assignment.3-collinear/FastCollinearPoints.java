/******************************************************************************
 *  Name:    Bardia Alavi
 *  NetID:   bardia
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description: A fast solution of finding collinear points in a set of points.
 ******************************************************************************/

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> jSegments = new ArrayList<>();

    // Find all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points)
    {
        // Check corner cases
        checkNullEntries(points);
        checkDuplicatedEntries(points);

        Point[] jCopy = points.clone();
        Arrays.sort(jCopy);

        for (int i = 0; i < jCopy.length - 3; i++) {
            Arrays.sort(jCopy);

            // Sort the points according to the slopes they makes with p.
            // Check if any 3 (or more) adjacent points in the sorted order
            // have equal slopes with respect to p. If so, these points,
            // together with p, are collinear.

            Arrays.sort(jCopy, jCopy[i].slopeOrder());

            int last = 2;
            int first = 1;
            int p = 0;
            while (last < jCopy.length)
            {
                while ((last < jCopy.length) && 
                       (Double.compare(jCopy[p].slopeTo(jCopy[first]),
                        jCopy[p].slopeTo(jCopy[last])) == 0))
                {
                    last++;
                }
                // if found at least 3 elements, make segment if it's unique
                if (last - first >= 3 && jCopy[p].compareTo(jCopy[first]) < 0)
                {
                    jSegments.add(new LineSegment(jCopy[p], jCopy[last - 1]));
                }
                // Try to find next
                first = last;
                last++;
            }
        }
    }

    // the number of line segments
    public int numberOfSegments()
    {
        return jSegments.size();
    }

    // the line segments
    public LineSegment[] segments()
    {
        return jSegments.toArray(new LineSegment[jSegments.size()]);
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

