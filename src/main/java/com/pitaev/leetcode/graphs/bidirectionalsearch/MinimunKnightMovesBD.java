import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.HashMap;

/**
 * Binary search solution of the problem:
 * https://leetcode.com/problems/minimum-knight-moves/description/
 * Observations:
 * <ul>
 *     <li>Start bfsfrom source and from the target</li>
 *     <li>The dfs searches meet if the node is visited from source and from target</li>
 *     <li>The distance is: distance (from source) + distance (from target)</li>
 * </ul>
 */
class Coordinate {
    int x;
    int  y;
    Coordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object object) {
        Coordinate coordinate = (Coordinate) object;
        return (this.x == coordinate.x && this.y == coordinate.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

}

class Solution {
    public int minKnightMoves(int x, int y) {
        // edge case: if source == target
        if (x == 0 && y == 0) return 0;
        // start bfs from source and from target
        // two dfs "meet" if node is visited from the source and node is visited from the target
        Coordinate source = new Coordinate(0, 0);
        Coordinate target = new Coordinate(x, y);
        Map<Coordinate, Integer> distancesL = new HashMap<>();
        distancesL.put(source, 0);
        Map<Coordinate, Integer> distancesR = new HashMap<>();
        distancesR.put(target, 0);
        ArrayDeque<Coordinate> queueL = new ArrayDeque<>();
        queueL.offer(source);
        ArrayDeque<Coordinate> queueR = new ArrayDeque<>();
        queueR.offer(target);
        while(queueL.size() > 0 && queueR.size() > 0) {
            // process left queue first
            Coordinate current = queueL.poll();
            Integer row = current.x;
            Integer column = current.y;
            for (Coordinate neighbor : getNeighbors(current)) {
                // if neighbor is not visited from the source
                if (distancesL.get(neighbor) == null) {
                    distancesL.put(neighbor, distancesL.get(current) + 1);
                    queueL.offer(neighbor);
                    // if neighbor already visited from target
                    if (distancesR.get(neighbor) != null) {
                        return distancesL.get(neighbor) + distancesR.get(neighbor);
                    }
                }
            }
            // process right queue
            current = queueR.poll();
            row = current.x;
            column = current.y;
            for (Coordinate neighbor : getNeighbors(current)) {
                // if neighbor is not visited from the target
                if (distancesR.get(neighbor) == null) {
                    distancesR.put(neighbor, distancesR.get(current) + 1);
                    queueR.offer(neighbor);
                    // if neighbor is not visited from the source
                    if (distancesL.get(neighbor) != null) {
                        return distancesR.get(neighbor) + distancesL.get(neighbor);
                    }
                }
            }

        }
        // return the sum of distances in the node where they meet
        return -1;
    }

    public static List<Coordinate> getNeighbors(Coordinate current) {
        List<Coordinate> result = new ArrayList<>();
        result.add(new Coordinate(current.x + 1, current.y + 2));
        result.add(new Coordinate(current.x + 1, current.y - 2));
        result.add(new Coordinate(current.x - 1, current.y + 2));
        result.add(new Coordinate(current.x - 1, current.y - 2));
        result.add(new Coordinate(current.x + 2, current.y + 1));
        result.add(new Coordinate(current.x + 2, current.y - 1));
        result.add(new Coordinate(current.x - 2, current.y + 1));
        result.add(new Coordinate(current.x - 2, current.y - 1));
        return result;
    }
}
