/******************************************************************************
 *  Name:    Bardia Alavi
 *  NetID:   bardia
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  Model an n-by-n percolation system using the union-find
 *                data structure.
 ******************************************************************************/
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.In;

public class Percolation {

    private boolean [][] sites;
    private final int size;
    private int openSiteCount;
    private final WeightedQuickUnionUF wquf;
    private final WeightedQuickUnionUF backwashUF;
    private final int top;
    private final int bottom;

    public Percolation(int n) // create n-by-n grid, with all sites blocked
    {
        if (n <= 0)
        {
            throw new IllegalArgumentException("Threw an IllegalArgumentException");
        }
        size = n;
        openSiteCount = 0;
        top = size * size;
        bottom = size * size + 1;
        wquf = new WeightedQuickUnionUF(size * size + 2); // 2 more for top and bottom
        backwashUF = new WeightedQuickUnionUF(size * size + 1);
        sites = new boolean[size] [size];
    }

    public void open(int row, int col) // open site (row, col)
    {
        if ((row > 0) && (row <= size) && (col > 0) && (col <= size))
        {
            if (!sites[row-1][col-1])
            {
                sites[row-1][col-1] = true;
                openSiteCount++;
            }
            if (size == 1)
            {
                wquf.union(top, getIndex(row, col));
                wquf.union(bottom, getIndex(row, col));
                backwashUF.union(top, getIndex(row, col));
            }
            else if (row == 1)
            {
                if (col == 1)
                {
                    if (isOpen(row, col+1))
                    {
                        wquf.union(getIndex(row, col), getIndex(row, col+1));
                        backwashUF.union(getIndex(row, col), getIndex(row, col+1));
                    }
                }
                else if (col == size)
                {
                    if (isOpen(row, col-1))
                    {
                        wquf.union(getIndex(row, col), getIndex(row, col-1));
                        backwashUF.union(getIndex(row, col), getIndex(row, col-1));
                    }
                }
                else
                {
                    if (isOpen(row, col+1))
                    {
                        wquf.union(getIndex(row, col), getIndex(row, col+1));
                        backwashUF.union(getIndex(row, col), getIndex(row, col+1));
                    }
                    if (isOpen(row, col-1))
                    {
                        wquf.union(getIndex(row, col), getIndex(row, col-1));
                        backwashUF.union(getIndex(row, col), getIndex(row, col-1));
                    }
                }
                if (isOpen(row+1, col))
                {
                    wquf.union(getIndex(row, col), getIndex(row+1, col));
                    backwashUF.union(getIndex(row, col), getIndex(row+1, col));
                }
                wquf.union(top, getIndex(row, col));
                backwashUF.union(top, getIndex(row, col));
            }
            else if (row == size)
            {
                if (col == 1)
                {
                    if (isOpen(row, col+1))
                    {
                        wquf.union(getIndex(row, col), getIndex(row, col+1));
                        backwashUF.union(getIndex(row, col), getIndex(row, col+1));
                    }
                }
                else if (col == size)
                {
                    if (isOpen(row, col-1))
                    {
                        wquf.union(getIndex(row, col), getIndex(row, col-1));
                        backwashUF.union(getIndex(row, col), getIndex(row, col-1));
                    }
                }
                else
                {
                    if (isOpen(row, col+1))
                    {
                        wquf.union(getIndex(row, col), getIndex(row, col+1));
                        backwashUF.union(getIndex(row, col), getIndex(row, col+1));
                    }
                    if (isOpen(row, col-1))
                    {
                        wquf.union(getIndex(row, col), getIndex(row, col-1));
                        backwashUF.union(getIndex(row, col), getIndex(row, col-1));
                    }
                }
                if (isOpen(row-1, col))
                {
                    wquf.union(getIndex(row, col), getIndex(row-1, col));
                    backwashUF.union(getIndex(row, col), getIndex(row-1, col));
                }
                wquf.union(getIndex(row, col), bottom);
            }
            else if (col == 1)
            {
                if (isOpen(row-1, col))
                {
                    wquf.union(getIndex(row, col), getIndex(row-1, col));
                    backwashUF.union(getIndex(row, col), getIndex(row-1, col));
                }
                if (row < size)
                {
                    if (isOpen(row+1, col))
                    {
                        wquf.union(getIndex(row, col), getIndex(row+1, col));
                        backwashUF.union(getIndex(row, col), getIndex(row+1, col));
                    }
                }
                if (isOpen(row, col+1))
                {
                    wquf.union(getIndex(row, col), getIndex(row, col+1));
                    backwashUF.union(getIndex(row, col), getIndex(row, col+1));
                }
            }
            else if (col == size)
            {
                if (isOpen(row-1, col))
                {
                    wquf.union(getIndex(row, col), getIndex(row-1, col));
                    backwashUF.union(getIndex(row, col), getIndex(row-1, col));
                }
                if (row < size)
                {
                    if (isOpen(row+1, col))
                    {
                        wquf.union(getIndex(row, col), getIndex(row+1, col));
                        backwashUF.union(getIndex(row, col), getIndex(row+1, col));
                    }
                }
                if (isOpen(row, col-1))
                {
                    wquf.union(getIndex(row, col), getIndex(row, col-1));
                    backwashUF.union(getIndex(row, col), getIndex(row, col-1));
                }
            }
            else
            {
                if (isOpen(row-1, col))
                {
                    wquf.union(getIndex(row, col), getIndex(row-1, col));
                    backwashUF.union(getIndex(row, col), getIndex(row-1, col));
                }
                if (isOpen(row+1, col))
                {
                    wquf.union(getIndex(row, col), getIndex(row+1, col));
                    backwashUF.union(getIndex(row, col), getIndex(row+1, col));
                }
                if (isOpen(row, col-1))
                {
                    wquf.union(getIndex(row, col), getIndex(row, col-1));
                    backwashUF.union(getIndex(row, col), getIndex(row, col-1));
                }
                if (isOpen(row, col+1))
                {
                    wquf.union(getIndex(row, col), getIndex(row, col+1));
                    backwashUF.union(getIndex(row, col), getIndex(row, col+1));
                }
            }
        }
        else
        {
            throw new IllegalArgumentException("Threw an IllegalArgumentException");
        }
    }

    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        if ((row <= 0) || (row > size) || (col <= 0) || (col > size))
        {
            throw new IllegalArgumentException("Threw an IllegalArgumentException");
        }
        return sites[row-1][col-1];
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        if ((row <= 0) || (row > size) || (col <= 0) || (col > size))
        {
            throw new IllegalArgumentException("Threw an IllegalArgumentException");
        }
        return backwashUF.connected(top, getIndex(row, col));
    }

    public int numberOfOpenSites()           // number of open sites
    {
        return openSiteCount;
    }

    public boolean percolates()              // does the system percolate?
    {
        return wquf.connected(top, bottom);
    }

    private int getIndex(int row, int col)
    {
        return (row-1)*size + col-1;
    }

    public static void main(String[] args)   // test client (optional)
    {
        int[] whitelist = In.readInts(args[0]);

        int size = whitelist[0];
        int dataLength = whitelist.length;
        Percolation testPer = new Percolation(size);
        for (int i = 1; i < dataLength; i += 2)
        {
            int row = whitelist[i];
            int col = whitelist[i+1];
            testPer.open(row, col);
        }
        boolean check = testPer.percolates();
        System.out.printf("This one is %b", check);
    }
}