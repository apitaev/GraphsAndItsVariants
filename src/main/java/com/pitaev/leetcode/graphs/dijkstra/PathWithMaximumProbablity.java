import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
/**
 * This class implements a Dijkstra-based solution for the leetcode problem <cod>1514</cod>:
 * https://leetcode.com/problems/path-with-maximum-probability/description/
 * We use some modifications to use Dijkstra algorithms here:
 * <ul>
 *     <li>How to replace multiplication by some: log(ab) = log(a) + log(b) --> use log of the probability</li>
 *     <li>How to find minPath rather than maxPath: all probabilities are in the range [0, 1]. That means, that all log values will be negative.
 *     We can look for the reversed problem: Look for min of the problem * (-1)</li>
 * </ul>
 * The convertions on the probabilites values will look like:
 * <ul>
 *     <li>Take a log from probability</li>
 *     <ll>Multiply it by -1 </ll>
 *     <li>Put it to the MinHeap</li>
 *     <li>Retrive it from the MinHeap</li>
 *     <li>Multiply by -1</li>
 *     <li>Take exp()</li>
 * </ul>
 *
 */

class PathWithMaximumProbability{
    static class Neighbor {
        int node;
        double distance;
        Neighbor(int node, double distance) {
            this.node = node;
            this.distance = distance;
        }
        double getDistance(){
            return this.distance;
        }
    }
    public double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {
        // build undirected graph
        List<List<Neighbor>> adjList = buildGraph(n, edges, succProb);
        // run dijkstra staring from start_node
        double [] captured = new double[n];
        for (int i = 0; i < n; i++) {
            captured[i] = -1;
        }
        PriorityQueue<Neighbor> queue = new PriorityQueue(Comparator.comparingDouble(Neighbor::getDistance));
        queue.offer(new Neighbor(start_node, 0));
        while (queue.size() > 0) {
            Neighbor current = queue.poll();
            if (captured[current.node] >= 0) continue;
            captured[current.node] = current.distance;
            if (current.node == end_node) return Math.exp((-1) * captured[current.node]);
            for (Neighbor neighbor : adjList.get(current.node)) {
                if (captured[neighbor.node] < 0) {
                    neighbor.distance += current.distance;
                    queue.offer(neighbor);
                }
            }
        }

        // return 0, if we reached this line.
        return 0;

    }
    private static List<List<Neighbor>> buildGraph(int n, int[][] edges, double [] succProb) {
        List<List<Neighbor>> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int [] edge = edges[i];
            double distance = (-1) * Math.log(succProb[i]);
            result.get(edge[0]).add(new Neighbor(edge[1], distance));
            result.get(edge[1]).add(new Neighbor(edge[0], distance));
        }
        return result;
    }
}