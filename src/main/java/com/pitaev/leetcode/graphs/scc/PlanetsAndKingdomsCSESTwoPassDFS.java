import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;

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
 *  Current solution is based on the Kosaraju's Algorithm
 *  <ul>
 *     <li>Run the first DFS on the graph to evaluate departure time. Introduce an auxilary list and store nodes to this list. Reverse the list, to get decresing order of the departure times.</li>
 *     <li>Reverse the original graph</li>
 *     <li>Run dfs on the reversed graph based decreasing order of the departure times</li>
 *     <li></li>
 *  </ul>
 */
public class PlanetsAndKingdomsCSESTwoPassDFS {
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
        // build adjList
        List<List<Integer>> adjList = buildGraph(n, edges, 0);
        // run the first dfs and store nodes to the list sorted by departure times
        int [] departures = new int [n + 1];
        int [] visited = new int [n + 1];
        List<Integer> topsort = new ArrayList<>();
        int [] timestamp = new int[1];
        timestamp[0] = 0;
        for (int i = 1; i <= n; i++) {
            if (visited[i] == 0) {
                dfs(i, timestamp, adjList, visited, departures, topsort);
            }
        }
        // reverse the topsort list
        Collections.reverse(topsort);
        // reverse the graph
        List<List<Integer>> adjListR = buildGraph(n, edges, 1);
        int [] visitedR = new int[n + 1];
        int sccCount = 0;
        // run the second dfs on the reversed graph
        for (Integer node : topsort) {
            if (visitedR[node] == 0) {
                sccCount++;
                dfsSecond(node, adjListR, visitedR, sccCount);
            }
        }
        // print number of scc
        System.out.println(sccCount);
        // print label of the scc for every node
        for (int i = 1; i <= n; i++) {
            System.out.println(visitedR[i]);
        }
    }
    private static List<List<Integer>> buildGraph(Integer numberOfNodes, List<List<Integer>> edges, int direction) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i <= numberOfNodes; i++) {
            result.add(new ArrayList<>());
        }
        for (List<Integer> edge : edges) {
            if (direction == 0) {
                result.get(edge.get(0)).add(edge.get(1));
            } else if (direction == 1) {
                result.get(edge.get(1)).add(edge.get(0));
            }
        }
        return result;
    }

    private static void dfs(Integer node, int [] timestamp, List<List<Integer>> adjList, int [] visited, int [] departures, List<Integer> result) {
        visited[node] = 1;
        for (Integer neighbor : adjList.get(node)) {
            if (visited[neighbor] == 0) {
                dfs(neighbor, timestamp, adjList, visited, departures, result);
            }
        }
        departures[node] = timestamp[0];
        timestamp[0]++;
        result.add(node);
    }

    private static void dfsSecond(Integer node, List<List<Integer>> adjList, int [] visited, int sccCount) {
        visited[node] = sccCount;
        for (Integer neighbor : adjList.get(node)) {
            if (visited[neighbor] == 0) {
                dfsSecond(neighbor, adjList, visited, sccCount);
            }
        }
    }
}
