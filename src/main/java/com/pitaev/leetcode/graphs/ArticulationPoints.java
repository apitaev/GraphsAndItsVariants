package com.pitaev.leetcode.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Problem: given a connected graph, find a vertex, whose removal disconnects the graph.
 * This vertex is called Articulation Point.
 * Use arrival times to find Articulation Points
 */
public class ArticulationPoints {
    List<Integer> findArticulationPoints(Integer n, int[][] edges) {
        List<Integer> result = new ArrayList<>();
        List<List<Integer>> adjList = buildGraph(n, edges);
        int [] arrivals = new int[n];
        int [] parents = new int[n];
        for (int i = 0; i < n; i++) {
            arrivals[i] = -1;
            parents[i] = -1;
        }
        dfs(0, 0, adjList, arrivals, parents, result);
        // go through the parents array and check if the first / root node is an articulation point
        int childrenCount = 0;
        for (int i = 0; i < n; i++) {
            if (parents[i] == 0) {
                childrenCount++;
            }
        }
        if (childrenCount > 1) {
            result.add(0);
        }
        return result;
    }

    private Integer dfs(Integer node, Integer timestamp, List<List<Integer>> adjList, int[] arrivals, int[] parents, List<Integer> result) {
        boolean isArticulationPoint = false;
        arrivals[node] = timestamp++;
        int earliestArrivalTime = arrivals[node];
        for (Integer neighbor : adjList.get(node)) {
            // if neighbor is not yet visited
            if (arrivals[neighbor] == -1) {
                parents[neighbor] = node;
                int childArrival = dfs(neighbor, timestamp, adjList, arrivals, parents, result);
                earliestArrivalTime = Math.min(earliestArrivalTime, childArrival);
                // dfs(neighbor) >= arrival of node (any neighbor can only reach the parent)
                isArticulationPoint = childArrival >= arrivals[node];
            } else if (parents[node] != neighbor){
                // if neighbor is visited, but it is not a parent of the current mode
                earliestArrivalTime = Math.min(earliestArrivalTime, arrivals[neighbor]);
            }
        }
        // if earliestArrival of node equal to arrivalTime of this node, it's parent will be Articulation point
        // Exclude starting / root node first, check for the root node in the outer loop
        if (isArticulationPoint && node != 0) {
            result.add(node);
        }
        return earliestArrivalTime;
    }

    private List<List<Integer>> buildGraph(Integer n, int[][] edges) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(new ArrayList<>());
        }
        for (int i = 0; i < edges.length; i++) {
             result.get(edges[i][0]).add(edges[i][1]);
             result.get(edges[i][1]).add(edges[i][0]);
        }
        return result;
    }

    public static void main(String [] args) {
        ArticulationPoints test = new ArticulationPoints();
        int [][] edges = {{0, 1}, {0, 2}, {0, 4}, {1, 2}, {1, 3}};
        List<Integer> result = test.findArticulationPoints(5, edges);
        // node 1 is an articulation point
        // node 0 is an articulation point
        for (Integer node : result) {
            System.out.println(node);
        }
        // TODO provide more test cases
    }
}
