//package com.pitaev.leetcode.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Problem: https://cses.fi/problemset/task/1691
 *
 * Your task is to deliver mail to the inhabitants of a city. For this reason, you want to find a route whose starting and ending point are the post office, and that goes through every street exactly once.
 * Input
 * The first input line has two integers n and m: the number of crossings and streets. The crossings are numbered 1,\,2,\ldots,\,n, and the post office is located at crossing 1.
 * After that, there are m lines describing the streets. Each line has two integers a and b: there is a street between crossings a and b. All streets are two-way streets.
 * Every street is between two different crossings, and there is at most one street between two crossings.
 * Output
 * Print all the crossings on the route in the order you will visit them. You can print any valid solution.
 * If there are no solutions, print "IMPOSSIBLE".
 * <p>Observation:
 * <ul>
 *     <li>The problem can be reduced to the Eulearian cycle on am undirected graph</li>
 *     <li>The node degree should be even</li>
 *     <li>The graph should be connected (we should check afterwards that all edges have been visited</li>
 *     <li>Once we visit edge from a to b, the edge b - a should be also deleted from the graph</li>
 * </ul>
 */
public class MailDeliveryCSES {
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
        // check if eulerian cycle exists
        if (!isEulearinCycle(n, edges)) {
            System.out.println("IMPOSSIBLE");
            return;
        }
        // run dfs
        int [] visited = new int[n + 1];
        Map<Integer, ArrayDeque<Integer>> adjMap = buildGraph(n, edges);
        List<Integer> result = new ArrayList<>();
        dfs(1, adjMap, visited, result);
        Collections.reverse(result);
        // check that cycle ended in 1
        if (result.get(result.size() - 1) != 1) {
            System.out.println("IMPOSSIBLE");
            return;
        }
        // check if all nodes are visited and all edges consumed
        for (int i = 1; i <= n; i++) {
            if (adjMap.get(i).size() > 0) {
                System.out.println("IMPOSSIBLE");
                return;
            }
        }

       System.out.println(result.stream().map(Object::toString).collect(Collectors.joining(" ")));
    }
    private static boolean isEulearinCycle(Integer n, List<List<Integer>> edges) {
        int [] degree = new int [n + 1];
        int [] outDegree = new int [n + 1];
        for (List<Integer> edge : edges) {
            degree[edge.get(0)]++;
            degree[edge.get(1)]++;
        }
        for (int i = 1; i <= n; i++) {
           if (degree[i] % 2 != 0) return false;
        }
        return true;
    }

    private static void dfs(Integer node, Map<Integer, ArrayDeque<Integer>> adjMap, int [] visited, List<Integer> result) {
        visited[node] = 1;
        ArrayDeque<Integer> neighbors = adjMap.get(node);
        while(neighbors.size() > 0) {
            Integer neighbor = neighbors.pop();
            // remove a symmetric edge from neighbor to node
            adjMap.get(neighbor).remove(node);
            dfs(neighbor, adjMap, visited, result);
        }
        result.add(node);
    }

    private static Map<Integer, ArrayDeque<Integer>> buildGraph(Integer n, List<List<Integer>> edges) {
        Map<Integer, ArrayDeque<Integer>> adjMap = new HashMap<>();
        for (int i = 0; i <= n; i++) {
            adjMap.put(i, new ArrayDeque<>());
        }
        for (List<Integer> edge : edges) {
            adjMap.get(edge.get(0)).push(edge.get(1));
            adjMap.get(edge.get(1)).push(edge.get(0));
        }
        return adjMap;
    }



}
