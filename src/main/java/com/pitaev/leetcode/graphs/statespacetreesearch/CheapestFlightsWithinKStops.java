import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Comparator;
/**
 * This problem is a State Space Tree Search implementation of the leetcode problem<code>787</code>:
 * https://leetcode.com/problems/cheapest-flights-within-k-stops/
 * The assumtion here: the Graph is infinite, it is not possible to store the visited information.
 * Therefore nodes will be stored multiple times in the PriorityQueue. The node with the min distance, will be returned froom the queue.
 */
class CheapesetFlightsWithinKStops {
    static class Stop {
        int node;
        int distance;
        int cost;
        int level;

        Stop(int  node, int cost) {
            this.node = node;
            this.cost = cost;
        }

        int getDistance() {
            return this.distance;
        }

        @Override
        public boolean equals(Object object) {
            Stop stop = (Stop) object;
            return this.node == stop.node && this.distance == stop.distance && this.cost == stop.cost && this.level == stop.level;
        }

        @Override
        public int hashCode() {
            return Objects.hash(node, distance, cost, level);
        }
    }
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        Map<Integer, List<Stop>> adjMap = buildGraph(n, flights);
        // build graph
        PriorityQueue<Stop> queue = new PriorityQueue<>(Comparator.comparingInt(Stop::getDistance));
        queue.offer(new Stop(src, 0));
        // run Dijkstra based on the distance information
        while(queue.size() > 0) {
            Stop current = queue.poll();
            // if node == dst exit, return distance after the queue only, to make sure it is the minimum distance
            if (current.node == dst) {
                return current.distance;
            }
            // if level == k + 1, skip this node, we can not exit here, it might be nodes with smaller levels and bigger costs in the queue
            if (current.level <= k) {
                for (Stop stop : adjMap.get(current.node)) {
                    stop.distance = current.distance + stop.cost;
                    stop.level = current.level + 1;
                    queue.offer(stop);
                }
            }
        }
        return -1;
    }

    private static Map<Integer, List<Stop>> buildGraph(Integer n, int [][] flights) {
        Map<Integer, List<Stop>> result = new HashMap<>();
        for (int i = 0; i < n; i++) {
            result.put(i, new ArrayList<>());
        }
        for (int i = 0; i < flights.length; i++) {
            int [] flight = flights[i];
            int name = flight[0];
            int neighbor = flight[1];
            int cost = flight[2];
            Stop stop = new Stop(neighbor, cost);
            result.get(name).add(stop);
        }
        return result;
    }
}