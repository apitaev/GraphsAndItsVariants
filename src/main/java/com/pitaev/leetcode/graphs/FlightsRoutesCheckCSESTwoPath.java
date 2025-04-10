//package com.pitaev.leetcode.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Problem: https://cses.fi/problemset/task/1682/
 * There are n cities and m flight connections. Your task is to check if you can travel from any city to any other city using the available flights.
 * Input
 * The first input line has two integers n and m: the number of cities and flights. The cities are numbered 1,2,\dots,n.
 * After this, there are m lines describing the flights. Each line has two integers a and b: there is a flight from city a to city b. All flights are one-way flights.
 * Output
 * Print "YES" if all routes are possible, and "NO" otherwise. In the latter case also print two cities a and b such that you cannot travel from city a to city b. If there are several possible solutions, you can print any of them.
 * Note: time limit errors on the platform
 */
public class FlightsRoutesCheckCSESTwoPath {
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
            // run first dfs
            int [] visited = new int[n + 1];
            int components = 0;
            for (int i = 1; i <= n; i++) {
                if (visited[i] == 0) {
                    components++;
                    if (components >= 2) {
                        System.out.println("NO");
                        // print counter example
                        System.out.println(1 + " " + i);
                        return;
                    }
                    dfs(i, adjList, visited);
                }
            }
            // build adjList for the reversed graph (all cities need to be reachable)
            List<List<Integer>> adjListR = buildGraphR(n, edges);
            int [] visitedR = new int[n + 1];
            components = 0;
            // run second dfs
            for (int i = 1; i <= n; i++) {
                if (visitedR[i] == 0) {
                    components++;
                    if (components >= 2) {
                        System.out.println("NO");
                        // print counter example
                        System.out.println(i + " " + 1);
                        return;
                    }
                    dfs(i, adjListR, visitedR);
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

    private static List<List<Integer>> buildGraphR(Integer n, List<List<Integer>> edges) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            result.add(new ArrayList<>());
        }
        // The graph is directed
        for (List<Integer> edge : edges) {
            result.get(edge.get(1)).add(edge.get(0));
        }
        return result;
    }
    private static void dfs(Integer start, List<List<Integer>> adjList, int [] visited) {
        visited[start] = 1;
        for (Integer neighbor : adjList.get(start)) {
            if (visited[neighbor] == 0) {
                dfs(neighbor, adjList, visited);
            }
        }
    }
}
