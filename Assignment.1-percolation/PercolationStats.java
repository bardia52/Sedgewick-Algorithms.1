/******************************************************************************
 *  Name:    Bardia Alavi
 *  NetID:   bardia
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  Percolation Statistics
 ******************************************************************************/
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private final int numExp;
    private final double[] fractions;

    /**
     * Performs numTrials independent computational experiments on an N-by-N grid.
     */
    public PercolationStats(int numExperiments, int numTrials) {
        if (numExperiments <= 0 || numTrials <= 0) {
            throw new IllegalArgumentException("Given numExperiments <= 0 "
            +"|| numTrials <= 0");
        }
        numExp = numTrials;
        fractions = new double[numExp];
        for (int expNum = 0; expNum < numExp; expNum++) {
            Percolation perc = new Percolation(numExperiments);
            int openedSites = 0;
            while (!perc.percolates()) {
                int i = StdRandom.uniform(1, numExperiments + 1);
                int j = StdRandom.uniform(1, numExperiments + 1);
                if (!perc.isOpen(i, j)) {
                    perc.open(i, j);
                    openedSites++;
                }
            }
            double fraction = (double) openedSites / (numExperiments * numExperiments);
            fractions[expNum] = fraction;
        }
    }

    /**
     * Sample mean of percolation threshold.
     */
    public double mean() {
        return StdStats.mean(fractions);
    }

    /**
     * Sample standard deviation of percolation threshold.
     */
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    /**
     * Returns lower bound of the 95% confidence interval.
     */
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(numExp));
    }

    /**
     * Returns upper bound of the 95% confidence interval.
     */
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(numExp));
    }

    public static void main(String[] args) {
        int numExperiments = Integer.parseInt(args[0]);
        int numTrials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(numExperiments, numTrials);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}
