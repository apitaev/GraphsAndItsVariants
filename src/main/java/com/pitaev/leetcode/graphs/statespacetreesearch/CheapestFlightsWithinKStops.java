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
            Integer node;
            final Integer distance;
            Integer cost;
            Integer level;

            Stop(Integer node, Integer cost) {
                this.node = node;
                this.cost = cost;
                this.distance = 0;
            }
            Stop(Integer node, Integer cost, Integer distance, Integer level) {
                this.node = node;
                this.cost = cost;
                this.distance = distance;
                this.level = level;
            }

            Stop withDistanceAndLevel(Integer distance, Integer level) {
                return new Stop(this.node, this.cost, distance, level);
            }

        }
        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
            Map<Integer, List<Stop>> adjMap = buildGraph(n, flights);
            // build graph
            PriorityQueue<Stop> queue = new PriorityQueue<>((a, b) -> a.distance.compareTo(b.distance));
            Stop source = new Stop(src, 0, 0, 0);
            queue.offer(source);
            // run Dijkstra based on the distance information
            while(queue.size() > 0) {
                Stop current = queue.poll();
                System.out.println(current.node + " " + current.distance);
                // if node == dst exit
                if (current.node.equals(dst))  {
                    return current.distance;
                }
                // if level == k + 1, skip this node
                if (current.level.equals(k + 1)) continue;
                for (Stop stop : adjMap.get(current.node)) {
                    Stop stopForQueue = stop.withDistanceAndLevel(current.distance + stop.cost, current.level + 1);
                    queue.offer(stopForQueue);
                }
            }
            return -1;
        }

        private static Map<Integer, List<Stop>> buildGraph(Integer n, int [][] flights) {
            Map<Integer, List<Stop>> result = new HashMap<>();
            for (int i = 0; i < n; i++) {
                result.put(i, new ArrayList<>());
            }
            for (int [] flight : flights) {
                Integer name = flight[0];
                Integer neighbor = flight[1];
                Integer cost = flight[2];
                Stop stop = new Stop(neighbor, cost);
                result.get(name).add(stop);
            }
            return result;
        }
    }