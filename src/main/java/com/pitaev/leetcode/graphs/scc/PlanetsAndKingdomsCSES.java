import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayDeque;


/**
 * <p>
 * Problem: https://cses.fi/problemset/task/1683
 * A game has n planets, connected by m teleporters. Two planets a and b belong to the same kingdom exactly when there is a route both from a to b and from b to a. Your task is to determine for each planet its kingdom.
 * Input
 * The first input line has two integers n and m: the number of planets and teleporters. The planets are numbered 1,2,\dots,n.
 * After this, there are m lines describing the teleporters. Each line has two integers a and b: you can travel from planet a to planet b through a teleporter.
 * Output
 * First print an integer k: the number of kingdoms. After this, print for each planet a kingdom label between 1 and k. You can print any valid solution
 * <p>
 *   Observation: the node with the biggest departure time is a source of the compressed graph of SCC
 *   Observation: if remove the source of the compressed graph, the new node with the highest departure time will be a new source of the remaining compressed graph
 *
 * <p>
 *  Current solution is based on the Tarjan's Algorithm
 *  <ul>
 *     <li>Build the graph</li>
 *     <li>Run dfs(return lowest arrival time value, store the node on the stack, visiting neighbors</li>
 *     <li>If the neighbor is visited: the edge can be: backe edge (departure time not set), cross edge, forward edge</li>
 *  </ul>
 */
public class PlanetsAndKingdomsCSES{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String line = input.nextLine();
        String[] numbers = line.split(" ");
        Integer n = Integer.parseInt(numbers[0]);
        Integer m = Integer.parseInt(numbers[1]);
        List<List<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            line = input.nextLine();
            numbers = line.split(" ");
            List<Integer> edge = new ArrayList<>();
            edge.add(Integer.parseInt(numbers[0]));
            edge.add(Integer.parseInt(numbers[1]));
            edges.add(edge);
        }
        // build the graph
        List<List<Integer>> adjList = buildGraph(n, edges);
        // run a single dfs
        // prepare auxilary data for the dfs
        int [] arrivals = new int[n + 1];
        int [] departure = new int[n + 1];
        int [] sccids = new int[n + 1];
        int [] sccCount = new int[1];
        int [] timestamp = new int[1];
        timestamp[0] = 1;
        // Stack to keep neighbors until labeling them with sccid
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            if (arrivals[i] == 0) {
                dfs(i, timestamp, adjList, arrivals, departure, sccids, sccCount, stack);
            }
        }

        // print number of connected components
        System.out.println(sccCount[0]);
        // print array of the scc
        for (int i = 1; i <= n; i++) {
            System.out.println(sccids[i]);
        }
    }
    private static List<List<Integer>> buildGraph(Integer n, List<List<Integer>> edges) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            result.add(new ArrayList<>());
        }
        for (List<Integer> edge : edges) {
            result.get(edge.get(0)).add(edge.get(1));
        }
        return result;
    }

    private static Integer dfs(Integer n, int [] timestamp, List<List<Integer>> adjList, int [] arrivals, int [] departure,
                               int [] sccids, int [] sccCount, ArrayDeque<Integer> stack) {
        arrivals[n] = timestamp[0];
        timestamp[0]++;
        int lowestArrival = arrivals[n];
        stack.push(n);
        for (Integer neighbor : adjList.get(n)) {
            if (arrivals[neighbor] == 0) { // neighbor is not visited, tree edge
                lowestArrival = Math.min(lowestArrival, dfs(neighbor, timestamp, adjList, arrivals, departure, sccids, sccCount, stack));
            } else if (departure[neighbor] == 0) { // back edge, update lowest arrival
                lowestArrival = Math.min(lowestArrival, arrivals[neighbor]);
            } else if (arrivals[neighbor] < arrivals[n] && sccids[neighbor] == 0) { // cross-edge from the same scc, update lowest arrival
                lowestArrival = arrivals[neighbor];
            }
        }
        // if it is a root of the scc unload the stack
        if (lowestArrival == arrivals[n]) {
            sccCount[0]++;
            while(stack.peek() != n) {
                sccids[stack.pop()] = sccCount[0];
            }
            // unload root
            sccids[stack.pop()] = sccCount[0];
        }
        // set departure time
        departure[n] = timestamp[0];
        timestamp[0]++;
        // return lowest arrival time
        return lowestArrival;
    }
}
