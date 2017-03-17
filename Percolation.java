/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TATA
 */


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF qu;
    private int gridsize;
    private int top;
    private int bottom;
    private boolean[] open;
    private final int length;



    public Percolation(int n)// create n-by-n grid, with all sites blocked
    {
        if (n <= 0) throw new IllegalArgumentException("Grid size n is less or equal to 0");
        this.gridsize = n*n;
        this.qu = new WeightedQuickUnionUF(gridsize + 2);
        this.top = 0;
        this.bottom = this.gridsize+1;
        this.open = new boolean[gridsize];
        this.length = (int) Math.sqrt(this.gridsize);
        System.out.println("Length "+this.length);
    }

    private int from2Dto1D(int x, int y)// Mapping the grid coordinates to 1D
    {
        int val;

        if (x > this.length || x < 1 || y > this.length || y < 1)
        {
            val = -1;
        }
        else
        {
            val = this.length * (x-1) + (y - 1) + 1;
        }
        return val;
    }

    private int[] from1Dto2D(int n)// Mapping the grid coordinates to 1D
    {
        int[] coord = new int[2];
        if (n < this.length)
        {
            coord[0] = 1;
            coord[1] = n;
        }
        else if (n % this.length != 0)
        {
            coord[0] =  (n / this.length) + 1;
            coord[1] = n % this.length;
        }
        else
        {
            coord[0] =  (n / this.length);
            coord[1] = this.length;
        }

        return coord;
    }
    private void validate(int x, int y)
    {
        if (x < 1 || x > this.gridsize || y < 1 || y > this.gridsize)
        {
            throw new IndexOutOfBoundsException("Index is out of bound!");
        }
    }

    public void open(int row, int col)// open site (row, col) if it is not open already
    {
        validate(row, col);

        if (!isOpen(row, col))
        {
            this.open[from2Dto1D(row, col) - 1] = true;
            if (row == 1)
            {
                int curr = from2Dto1D(row, col);
                this.qu.union(curr, this.top);
                connectNeighbours(row, col);
            }
            else if (row == this.length)
            {
                int curr = from2Dto1D(row, col);
                this.qu.union(curr, this.bottom);
                connectNeighbours(row, col);
            }
            else
            {
                connectNeighbours(row, col);
            }
        }
    }

    private void connectNeighbours(int row, int col)
    {
        int curr = from2Dto1D(row, col);
        int[] var = {from2Dto1D(row - 1, col), from2Dto1D(row + 1, col), from2Dto1D(row, col - 1), from2Dto1D(row, col + 1)};
        for (int iter : var)
        {
            int[] coord = from1Dto2D(iter);
            if (iter != -1 && !this.qu.connected(curr, iter))
            {
                if (isOpen(coord[0], coord[1]))
                {
                    this.qu.union(curr, iter);
                }
            }
        }
    }

    public boolean isOpen(int row, int col)// is site (row, col) open?
    {
        validate(row, col);
        return this.open[from2Dto1D(row, col) - 1];
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        validate(row, col);
        return this.qu.connected(from2Dto1D(row, col), this.top);
    }

    public boolean percolates()              // does the system percolate?
    {
        return this.qu.connected(this.top, this.bottom);
    }

    public static void main(String[] args)   // test client (optional)
    {

    }
}
