import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.PriorityQueue;

/**
 * This class implements Dijkstra-based solution for the leetcode problem <code>743</code>:
 * https://leetcode.com/problems/network-delay-time/description/
 */
class Neighbor {
    Integer node;
    Integer distance;
    Neighbor(Integer node, Integer distance) {
        this.node = node;
        this.distance = distance;
    }
}
public class NetworkDelayTime {
    public int networkDelayTime(int[][] times, int n, int k) {
        // build graph
        List<List<Neighbor>> adjList = buildGraph(n, times);
        // run dijkstra
        int [] captured = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            captured[i] = -1;
        }
        Queue<Neighbor> queue = new PriorityQueue<>((a,b) -> a.distance.compareTo(b.distance));
        Integer minDistance = 0;
        Neighbor start = new Neighbor(k, 0);
        queue.offer(start);
        while (queue.size() > 0) {
            Neighbor current = queue.poll();
            // if the node is already captured, that can not be a min distance, do nothing
            if (captured[current.node] >= 0) continue;
            captured[current.node] = current.distance;
            minDistance = current.distance;
            // visit neighbors
            for (Neighbor neighbor : adjList.get(current.node)) {
                if (captured[neighbor.node] == -1) {
                    neighbor.distance += current.distance;
                    queue.offer(neighbor);
                }
            }

        }
        // if some node is not visited return -1
        for (int i = 1; i <= n; i++) {
            if (captured[i] == -1) {
                return -1;

            }
        }
        return minDistance;
    }

    private static List<List<Neighbor>> buildGraph(Integer n, int [][] edges) {
        List<List<Neighbor>> adjList = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adjList.add(new ArrayList<>());
        }
        for (int [] edge : edges) {
            Neighbor neighbor = new Neighbor(edge[1], edge[2]);
            adjList.get(edge[0]).add(neighbor);
        }
        return adjList;
    }
}