/**
 * This class implements a DP-based solution for the leetcode problem <code>62</code>:
 * https://leetcode.com/problems/unique-paths
 * Observation: we only need 2 rows to calculate the grid until the cell (m - 1, n - 1)
 */
class UniquePathsDP {
    public int uniquePaths(int m, int n) {
        int [][] table = new int[2][n];
        // row 0 has number of paths 1 (from the left neighbor)
        for (int i = 0; i < n; i++) {
            table[0][i] = 1;
        }
        // column 0 has number of path 1 (from the bottom neighbor)
        table[1][0] = 1;

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                table[i % 2][j] = table[i % 2][j - 1] + table[(i - 1) % 2][j];
            }
        }
        // returm the bottom right cell
        return table[(m - 1) % 2 ][n - 1];

    }
}