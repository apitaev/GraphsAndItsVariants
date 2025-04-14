import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;
import java.util.Collections;
/**
 * This class implements a DP-based solution for the leetcode problem <code>1136</code>:
 * https://leetcode.com/problems/parallel-courses
 * Observations:
 * <ul>
 *     <ul>We can build directed dependency graph for the courses</ul>
 *     <ul>If the graph has cycle, there is no way to take all the courses</ul>
 *     <ul>Otherswise it is DAG</ul>
 *     <ul>Create topological sort order for the graph</ul>
 *     <ul>Reduce the problem to the defining max path on the DAG</ul>
 *     <ul>The minimum number of semester required is the max path on the DAG</ul>
 * </ul>
 *
 */

class ParallelCoursesDP {
    public int minimumSemesters(int n, int[][] relations) {
        // build Graph
        List<List<Integer>> adjList = buildGraph(n, relations);
        // run dfs
        int [] visited = new int[n + 1];
        int [] departure = new int[n + 1];
        Arrays.fill(visited, -1);
        Arrays.fill(departure, -1);
        int [] timestamp = new int [1];
        List<Integer> topSortOrder = new ArrayList<>();
        // start dfs from the first node
        boolean hasCycle = false;
        for (int i = 1; i <= n; i++) {
            if (visited[i] == -1) {
                hasCycle =  dfs(i, adjList, visited, departure, timestamp, topSortOrder);
                // if cycle detected return -1
                if (hasCycle) return -1;
            }
        }

        // if no cycle - it is DAG
        // create topological sortorder based on the reversed departure times list
        Collections.reverse(topSortOrder);
        System.out.println(topSortOrder);
        // visit the graph in the topological sort
        int [] table = new int [n + 1];
        // At least one semester is needed
        Arrays.fill(table, 1);
        for (int i = 1; i <= n; i++ ) {
            // find all neighbors in the graph
            for (Integer neighbor : adjList.get(i)) {
                table[neighbor] = Math.max(table[neighbor], table[i] + 1);
            }
        }
        // return the length of the node with max path
        int maxPath = 1;
        for (int i = 1; i <= n; i++) {
            maxPath = Math.max(maxPath, table[i]);
        }
        return maxPath;
    }

    private static List<List<Integer>> buildGraph(int n, int [][] relations) {
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adjList.add(new ArrayList<>());
        }
        for (int [] relation : relations) {
            adjList.get(relation[0]).add(relation[1]);
        }
        return adjList;
    }

    private static boolean dfs(Integer n, List<List<Integer>> adjList, int[] visited, int [] departure, int [] timestamp, List<Integer> result) {
        visited[n] = 1;
        for (Integer neighbor : adjList.get(n)) {
            if (visited[neighbor] == -1) {
                if (dfs(neighbor, adjList, visited, departure, timestamp, result)) return true;
            } else if (departure[neighbor] == -1){
                // we encounter back edge --> cycle in the graph
                return true;
            }
        }
        departure[n] = timestamp[0];
        timestamp[0]++;
        // add the node to the topological sort order list on departure time
        result.add(n);
        return false;
    }
}