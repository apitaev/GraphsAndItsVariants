/**
 * This class is a FBS solution for the leetcode problem <code>1197</code>
 * https://leetcode.com/problems/minimum-knight-moves/
 *
 * The solution results in the time limit exceed on the leetcode
 */
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayDeque;

class MinimumKnightMovesBFS {
    public int minKnightMoves(int x, int y) {
        // base solution based on dfs
        // build graph(specifiy moves)
        int [] dx = {1, 1, -1, -1, 2, 2, -2, -2};
        int [] dy = {2, -2, 2, -2, 1, -1, 1, -1};
        // source node has coordinates[0,0]
        Map<int [], Integer> distances = new HashMap<>();
        // distance of the source node is 0
        int [] source = {0, 0};
        distances.put(source, 0);
        ArrayDeque<int []> queue = new ArrayDeque<>();
        queue.offer(source);
        // run dfs
        while(queue.size() > 0) {
            int [] current = queue.poll();
            int row = current[0];
            int column = current[1];
            // visit neighbors
            for (int i = 0; i < 8; i++) {
                int neighbor_x = row + dx[i];
                int neighbor_y = column + dy[i];
                int [] neighbor = new int [] {neighbor_x, neighbor_y};
                distances.put(neighbor, distances.get(current) + 1);
                if (neighbor_x == x && neighbor_y == y) {
                    return distances.get(neighbor);
                }
                queue.offer(neighbor);
            }
        }
        // return -1, if target is not reachable
        return -1;

    }
}