/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TATA
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    private int gridinit;
    private int trials;
    private double[] results;

    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        validate(n, trials);
        this.gridinit = n;
        this.trials = trials;
        this.results = new double[trials];
        int gridsize = gridinit * gridinit;


        for (int i = 0; i < trials; i++)
        {
            Percolation p = new Percolation(gridinit);
            int opened = 0;

            while (!p.percolates())
            {
                int x = StdRandom.uniform(1, this.gridinit + 1);
                int y = StdRandom.uniform(1, this.gridinit + 1);

                if (!p.isOpen(x, y))
                {
                    p.open(x, y);
                    opened++;
                }
            }
            results[i] = (double) opened/gridsize;
        }
    }

    private void validate(int x, int y)
    {
        if (x <= 0 || y <= 0) throw new IllegalArgumentException("Paramaters should be greater than 0!");
    }

    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(results);
    }

    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(results);
    }

    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return mean() - 1.96 * stddev()/Math.sqrt(trials);
    }

    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return mean() + 1.96 * stddev()/Math.sqrt(trials);
    }

    public static void main(String[] args)    // test client (described below)
    {
        int grid = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(grid, trials);

        System.out.println("mean                    = "+ps.mean());
        System.out.println("stddev                  = "+ps.stddev());
        System.out.println("95% confidence interval = "+ps.confidenceLo()+" "+ps.confidenceHi());
    }
}
