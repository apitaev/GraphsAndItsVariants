
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayDeque;
/**
 * This class implements solution for the leetcode problem<code>332</code>
 * https://leetcode.com/problems/reconstruct-itinerary/description/
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