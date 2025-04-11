//package com.pitaev.leetcode.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.RuntimeException;

/**
 * Problem: https://cses.fi/problemset/task/1682/
 * There are n cities and m flight connections. Your task is to check if you can travel from any city to any other city using the available flights.
 * Input
 * The first input line has two integers n and m: the number of cities and flights. The cities are numbered 1,2,\dots,n.
 * After this, there are m lines describing the flights. Each line has two integers a and b: there is a flight from city a to city b. All flights are one-way flights.
 * Output
 * Print "YES" if all routes are possible, and "NO" otherwise. In the latter case also print two cities a and b such that you cannot travel from city a to city b. If there are several possible solutions, you can print any of them.
 * Tarjan's Algorithm is based on the lowest arrival time:
 * There is a path from every vertex in a SCC to its predecessor
 *  lowestsArrivalTime[node} <= arrivalTine(node)
 */
public class FlightsRoutesCheckCSES {
    public static void main(String[] args) {
            Scanner input = new Scanner(System.in);
            String line = input.nextLine();
            String [] numbers = line.split(" ");
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
            List<List<Integer>> adjList = buildGraph(n, edges);
            // run single dfs
            int [] arrivals = new int[n + 1];
            // specify list of nodes, from which 1 can not be reached
            List<Integer> result = new ArrayList<>();
            Integer [] timestamp = new Integer[1];
            timestamp[0] = 1;
            // found vertex who can not reach 1
            dfs(1, timestamp, adjList, arrivals, result);
            if (result.size() > 0) {
                System.out.println("NO");
                System.out.println(result.get(0) + " " + 1);
                return;
            }

            // check that there is a path from vertex 1 to any other vertex
            for (int i = 1; i <= n; i++) {
                if (arrivals[i] == 0) {
                    System.out.println("NO");
                    System.out.println(1 + " " + i);
                    return;
                }
            }
            System.out.println("YES");
        }
    private static List<List<Integer>> buildGraph(Integer n, List<List<Integer>> edges) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            result.add(new ArrayList<>());
        }
        // The graph is directed
        for (List<Integer> edge : edges) {
            result.get(edge.get(0)).add(edge.get(1));
        }
        return result;
    }

    private static Integer dfs(Integer start, Integer [] timestamp, List<List<Integer>> adjList, int [] arrivals, List<Integer> result) {
        arrivals[start] = timestamp[0];
        timestamp[0]++;
        int lowestArrivalTime = arrivals[start];
        for (Integer neighbor : adjList.get(start)) {
            if (arrivals[neighbor] == 0) { // tree edge
                lowestArrivalTime = Math.min(lowestArrivalTime, dfs(neighbor, timestamp, adjList, arrivals, result));
            } else { // back edge, forward edge, cross edge out of myself
                lowestArrivalTime = Math.min(lowestArrivalTime, arrivals[neighbor]);
            }
        }
        if (lowestArrivalTime == arrivals[start] && start != 1) {
            // found a counter example, can not reach from myself vertex 1
            result.add(start);

        }
        return lowestArrivalTime;
    }
}
