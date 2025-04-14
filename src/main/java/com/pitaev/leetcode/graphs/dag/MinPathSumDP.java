import java.lang.Math;
/**
 * This class implements a DP-based solution for the leetcode problem <code>64</code>:
 * https://leetcode.com/problems/minimum-path-sum/
 *
 */
class MinPathSumDP {
    public int minPathSum(int[][] grid) {
        int [][] table = new int[grid.length][grid[0].length];

        table[0][0] = grid[0][0];

        // fill the first column (sum of the elemenets from bottom)
        for (int i = 1; i < grid.length; i++) {
            table[i][0] = table[i - 1][0] + grid[i][0];
        }
        // fill the first row from grid (sum of the elements from left)
        for (int i = 1; i < grid[0].length; i++) {
            table[0][i] = table[0][i - 1] + grid[0][i];
        }

        // every other node can be reached either from left or from the top
        // we select a minimum of it
        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[0].length; j++) {
                table[i][j] = grid[i][j] + Math.min(table[i][j - 1], table[i - 1][j]);
            }
        }

        return table[grid.length - 1][grid[0].length - 1];

    }
}