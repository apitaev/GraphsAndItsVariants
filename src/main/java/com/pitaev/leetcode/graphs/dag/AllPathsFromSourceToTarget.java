import java.util.List;
import java.util.ArrayList;
/**
 * This class implements Dijkstra-based solution for the leetcode problem <code>797</code>:
 * https://leetcode.com/problems/all-paths-from-source-to-target/
 * DFS traversal on DAG
 */
class AllPathsFromSourceToTarget {
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> slate = new ArrayList<>();
        dfs(0, graph, slate, result);
        return result;
    }

    private static void dfs(int start, int[][] adjList, List<Integer> slate, List<List<Integer>> result) {
        slate.add(start);
        // node does not have any neighbors
        // edge case 1: reached node n - 1, add partial solution, return
        if (start == adjList.length - 1) {
            result.add(new ArrayList<>(slate));
            slate.remove(slate.size() - 1);
            return;
        }
        // edge case2: node has no neighbors, return
        if (adjList[start].length == 0) {
            slate.remove(slate.size() - 1);
            return;
        }
        for (int neighbor : adjList[start]) {
            dfs(neighbor, adjList, slate, result);
        }
        slate.remove(slate.size() - 1);
    }
}