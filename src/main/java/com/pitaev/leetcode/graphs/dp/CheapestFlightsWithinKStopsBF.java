import java.util.Arrays;
/**
 * This problem is a Bellman-Ford implementation of the leetcode problem<code>787</code>:
 * https://leetcode.com/problems/cheapest-flights-within-k-stops/
 * Observations used here:
 * <ul>
 *     <li>This problem can be reduced to DP shortest path problem</li>
 *     <li>The DP table will contain n rows and k + 2 columns</li>
 *     <li>Every row is min path to the city</li>
 *     <li>Every column is a stop</li>
 *     <li>table[1][2] contains min path to the city 1 using  overall 3 stops (or within betweenn 2 stops)</li>
 *     <li>Initialize table[src][0] to 0, all other table cell to Infinity</li>
 *     <li>Initialize table[i][j] = table[i][j - 1], than try to improve this value using flights data</li>
 *     <li>Return table[dst][k+2], if its value < Infinity, or -1 else</li>
 * </ul>
 */

class CheapestFlightsWithinKStopsFB {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        Integer [][] table = new Integer[n][k + 2];
        // fill DP table with max values first
        for (Integer [] row : table) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        //path from source to source is 0
        // the rows in the dp table represents nodes
        // the columns in the dp represents stops
        // table[2][3] represents min distance to the node 2 using 3 stops
        table[src][0] = 0;
        // iterate through k
        for (int stop = 1; stop <= k + 1; stop++) {
            // copy value from the left column of the table first
            for (int i = 0; i < n; i++) {
                table[i][stop] = table [i][stop - 1];
            }
            // use flights array to see if you can do it better
            for (int [] flight : flights) {
                int source = flight[0];
                int target = flight[1];
                int cost = flight[2];
                if (table[source][stop - 1] < Integer.MAX_VALUE) {
                    table[target][stop] = Math.min(table[target][stop], table[source][stop - 1] + cost);
                }
            }

        }
        if (table[dst][k + 1].equals(Integer.MAX_VALUE)) return -1;
        return table[dst][k + 1];

    }
}
