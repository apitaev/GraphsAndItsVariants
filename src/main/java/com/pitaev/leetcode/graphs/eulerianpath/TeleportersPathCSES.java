//package com.pitaev.leetcode.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.Collections;

/**
 * Problem: https://cses.fi/problemset/task/1693
 *
 * A game has n levels and m teleportes between them. You win the game if you move from level 1 to level n using every teleporter exactly once.
 * Can you win the game, and what is a possible way to do it?
 * Input
 * The first input line has two integers n and m: the number of levels and teleporters. The levels are numbered 1,2,\dots,n.
 * Then, there are m lines describing the teleporters. Each line has two integers a and b: there is a teleporter from level a to level b.
 * You can assume that each pair (a,b) in the input is distinct.
 * Output
 * Print m+1 integers: the sequence in which you visit the levels during the game. You can print any valid solution.
 * If there are no solutions, print "IMPOSSIBLE".
 */
public class TeleportersPathCSES {
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
        // check if the following conditions are met for the elerian path
        // 1. inDegree of the source = outDegree + 1
        // 2. outDegree of the target = inDegree + 1
        // 3. for all other nodes: inDegree == outDegree
        if (!eulerianPathExists(n, edges)) {
            System.out.println("IMPOSSIBLE");
            return;
        }
        Map<Integer, ArrayDeque<Integer>> adjMap = buildGraph(n, edges);
        List<Integer> result = new ArrayList<>();
        int[] visited = new int[n + 1];
        dfs(1, n, adjMap, visited, result);
        Collections.reverse(result);
        // check if graph is not connected, last node is not reachable
        if (visited[n] == 0) {
            System.out.println("IMPOSSIBLE");
            return;
        }
        // check if graph is not connected, some edges not used
        for (int i = 1; i <= n; i++) {
            if (adjMap.get(i).size() > 0) {
                System.out.println("IMPOSSIBLE");
                return;
            }
        }


        for (Integer node : result) {
            System.out.println(node);
        }
    }

    private static Map<Integer, ArrayDeque<Integer>> buildGraph(Integer n, List<List<Integer>> edges) {
        Map<Integer, ArrayDeque<Integer>> result = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            result.put(i, new ArrayDeque<Integer>());
        }
        for (List<Integer> edge : edges) {
            result.get(edge.get(0)).push(edge.get(1));
        }
        return result;
    }
    private static void dfs(Integer node, Integer n, Map<Integer, ArrayDeque<Integer>> adjMap, int [] visited, List<Integer> result) {
        visited[node] = 1;
        ArrayDeque<Integer> neighbors = adjMap.get(node);
        while(neighbors.size() > 0) {
            Integer neighbor = neighbors.pop();
            dfs(neighbor, n, adjMap, visited, result);
        }
        // add node to the result on departure
        result.add(node);
    }

    private static boolean eulerianPathExists(Integer n, List<List<Integer>> edges) {
       int [] inDegree = new int[n + 1];
       int [] outDegree = new int[n + 1];
       for (List<Integer> edge : edges) {
           inDegree[edge.get(1)]++;
           outDegree[edge.get(0)]++;
       }
       for (int i = 1; i <= n; i++) {
           if (inDegree[i] == outDegree[i] && (i == 1 || i == n)) return false;
           if (inDegree[i] - outDegree[i] == 1 && i != n) return false;
           if (outDegree[i] - inDegree[i] == 1 && i != 1) return false;
           if (Math.abs(inDegree[i] - outDegree[i]) > 1) return false;
       }
       return true;
    }
}
