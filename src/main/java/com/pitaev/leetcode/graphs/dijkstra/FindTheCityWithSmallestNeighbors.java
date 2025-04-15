import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Comparator;
/**
 * This class implements Dijkstra-based solution for the leetcode problem <code>1334</code>:
 * https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/description/
 */
class FindTheCityWithSmallestNeighbors {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        // build the graph
        List<List<Integer[]>> adjList = buildGraph(n, edges);
        // run dijkstra for every node
        int minNeighbors = Integer.MAX_VALUE;
        int bestCity = -1;
        for (int i = 0; i < n; i++) {
            Map<Integer, Integer> captured = new HashMap<>();
            int currentNeighbors = dijkstra(i, adjList, captured, distanceThreshold);
            if (minNeighbors >= currentNeighbors) {
                minNeighbors = currentNeighbors;
                bestCity = i;
            }
        }
        return bestCity;
    }

    private static List<List<Integer []>> buildGraph(int n, int [][] edges) {
        List<List<Integer []>> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(new ArrayList<>());
        }
        for (int [] edge : edges) {
            Integer [] neighborData = new Integer[2];
            int source = edge[0];
            int dist = edge[1];
            int distance = edge[2];
            // neighbor city
            neighborData[0] = dist;
            // neighbor distance
            neighborData[1] = distance;
            result.get(source).add(neighborData);
            // undirected graph, add second edge
            neighborData = new Integer[2];
            neighborData[0] = source;
            neighborData[1] = distance;
            result.get(dist).add(neighborData);
        }
        return result;
    }

    private static int dijkstra(Integer node, List<List<Integer []>> adjList, Map<Integer, Integer> captured, int threshold) {
        PriorityQueue<Integer[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        Integer [] source = new Integer[] {node, 0};
        queue.offer(source);
        while(queue.size() > 0) {
            Integer [] current = queue.poll();
            if (captured.get(current[0]) != null) continue;
            captured.put(current[0], current[1]);
            if (current[1].compareTo(threshold) > 0) {
                break;
            }
            for (Integer[] neighbor : adjList.get(current[0])) {
                if (captured.get(neighbor[0]) == null) {
                    // update distance for the neighbor city
                    Integer [] city  = new Integer[2];
                    city[0] = neighbor[0];
                    city[1] = current[1] + neighbor[1];
                    queue.offer(city);
                }
            }
        }
        return captured.size();
    }
}