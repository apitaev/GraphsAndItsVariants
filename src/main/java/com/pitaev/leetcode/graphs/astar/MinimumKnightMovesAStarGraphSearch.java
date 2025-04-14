import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.PriorityQueue;
/**
 * A* graph search solution of the leetcode problem<code>1197</code>:
 * https://leetcode.com/problems/minimum-knight-moves/description/
 * Observations:
 * <ul>
 *     <li>Use heuristics to minimize the path to the target: e.g. distance + |x2 - x1| + |y2 - y1|</li>
 *     <li>Stored nodes to the priority queue sorted by the heuristics above</li>
 *     <li>Use captured array to mark visited nodes</li>
 * </ul>
 */
class MinimumKnighMovesAStarGraphSearch {
    static class ChessSquare{
        int x;
        int y;
        Integer distance;
        Double weight;
        ChessSquare(int x, int y) {
            this.x = x;
            this.y = y;
        }

        double getWeight() {
            return this.weight;
        }
    }
    public int minKnightMoves(int x, int y) {
        // if target square is a source square, return 0
        if (x == 0 && y == 0) return 0;
        ChessSquare target = new ChessSquare(x, y);
        // A* start solution using captured arrays
        // 1. Build a graph on the fly
        // 2. Run A* version of Dijkstra
        PriorityQueue<ChessSquare> queue = new PriorityQueue<>((a,b) -> a.weight.compareTo(b.weight));
        Set<ChessSquare> captured = new HashSet<>();
        // source square is (0, 0)
        ChessSquare source = new ChessSquare(0,0);
        // distance of the source square is 0
        source.distance = 0;
        // weight (to the target node) is x + y / 3
        source.weight = Double.valueOf((Math.abs(x) + Math.abs(y)) / 3.0);
        queue.offer(source);
        while(queue.size() > 0) {
            ChessSquare current = queue.poll();
            // min distance will have a node returned from the queue
            if (current.x == x && current.y == y) return current.distance;
            // if the square has been visited before
            if (captured.contains(current)) continue;
            captured.add(current);
            for (ChessSquare neighbor : getNeighbors(current)) {
                if (!captured.contains(neighbor)) {
                    neighbor.distance = current.distance + 1;
                    // distance is an approximated distance to the target, for the chess we divide by 3 (because of the knight move)
                    // for the distance: |x1 - x| + |y1 - y|, where x1, y1 - current coordinates, x,y - target coordinates
                    neighbor.weight = neighbor.distance + getWeight(x, y, neighbor.x, neighbor.y);
                    queue.offer(neighbor);
                }
            }
        }
        // 3. Return -1, if no solution possible
        return -1;
    }

    private static List<ChessSquare> getNeighbors(ChessSquare current) {
        List<ChessSquare> result = new ArrayList<>();
        result.add(new ChessSquare(current.x + 1, current.y + 2));
        result.add(new ChessSquare(current.x + 1, current.y - 2));
        result.add(new ChessSquare(current.x - 1, current.y + 2));
        result.add(new ChessSquare(current.x - 1, current.y - 2));
        result.add(new ChessSquare(current.x + 2, current.y + 1));
        result.add(new ChessSquare(current.x + 2, current.y - 1));
        result.add(new ChessSquare(current.x - 2, current.y + 1));
        result.add(new ChessSquare(current.x - 2, current.y - 1));
        return result;
    }

    private static double getWeight(int x, int y, int x1, int y1) {
        return Double.valueOf((Math.abs(x - x1) + Math.abs(y - y1)) / 3.0);
    }
}