
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayDeque;
/**
 * This class implements solution for the leetcode problem<code>332</code>
 * https://leetcode.com/problems/reconstruct-itinerary/description/
 * <p>There're some refreshments about the Eulerian path / Eulerian graph:
 * <ul>
 *     <li>An Eulerian path traverses every edge of the graph exactly ones</li>
 *     <li>An Eulerian cycle is a Eulerian path which ends back at the starting vertex</li>
 *     <li>A Graph containing Eulerian cycle called Eulerian Graph</li>
 * </ul>
 * <p>The undirected graph to be Eulerian:
 * <ul>
 *     <li>Every vertex should have even degree to be Eulerain cycle</li>
 *     <li>At most two vertices should have odd degree to be Eularian path</li>
 * </ul>
 *
 * <p>The undirected graph to be Eulerian:
 * <ul>
 *     <li>Every vertex should have in-degree == out-degree to be Eulearion cycle</li>
 *     <li>For Eulerian path: exactly one vertex with out-degree = in-degree + 1 (start vertex) and exactly one vertex with in-degree = out-degree + 1 (end vertex)</li>
 * </ul>
 * The eulearian graph algorithm uses dfs approach with some modifications:
 * <ul>
 *     <li>Edges is removed from the adjMap, once it is visited , the stack structure allows removal of the element in O(1)</li>
 *     <li>Edges should be sorted in reversed order before added to the stack. The problem expects result in the smallest lexical order</li>
 *     <li>Nodes are added on the departure time to the result list</li>
 *     <li>Result list needs to be reversed after dfs completed</li>
 * </ul>
 *
 */
class FindItinerary{
    public List<String> findItinerary(List<List<String>> tickets) {
        // sort tickets in reverse order
        Collections.sort(tickets, (a, b) -> a.get(1).compareTo(b.get(1)) * (-1));
        Map<String, ArrayDeque<String>> adjList = buildGraph(tickets);
        List<String> result = new ArrayList<>();
        dfs("JFK", adjList, result);
        Collections.reverse(result);
        return result;
    }

    private static Map<String, ArrayDeque<String>> buildGraph(List<List<String>> tickets) {
        Map<String, ArrayDeque<String>> result = new HashMap<>();
        for (List<String> ticket : tickets) {
            if (result.get(ticket.get(0)) == null) {
                ArrayDeque<String> stack = new ArrayDeque<>();
                stack.push(ticket.get(1));
                result.put(ticket.get(0), stack);
            } else {
                result.get(ticket.get(0)).push(ticket.get(1));
            }
        }
        return result;
    }

    private static void dfs(String s, Map<String, ArrayDeque<String>> adjList, List<String> result) {
        ArrayDeque<String> neighbors = adjList.get(s);
        while(neighbors != null && neighbors.size() > 0) {
            String neighbor = neighbors.pop();
            dfs(neighbor, adjList, result);
        }
        result.add(s);
    }
}