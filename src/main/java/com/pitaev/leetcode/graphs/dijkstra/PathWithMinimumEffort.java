import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
/**
 * This class implements Dijkstra-based solution for the leetcode problem <code>1631</code>:
 * https://leetcode.com/problems/path-with-minimum-effort/description/
 */
class PathWithMinimumEffort {
    static class Neighbor {
        int row;
        int column;
        int height;
        int distance;
        Neighbor(int row, int column, int height) {
            this.row = row;
            this.column = column;
            this.height = height;
        }
        int getDistance() {
            return this.distance;
        }
    }
    public int minimumEffortPath(int[][] heights) {
        // build graph on the fly
        // run Dijkstra
        // returm min distance for the bottom-right index
        int [][] captured = new int [heights.length][heights[0].length];
        for (int row = 0; row < heights.length; row++) {
            for (int column = 0; column < heights[0].length; column++) {
                captured[row][column] = -1;
            }
        }
        PriorityQueue<Neighbor> queue = new PriorityQueue(Comparator.comparingInt(Neighbor::getDistance));
        queue.offer(new Neighbor(0, 0, heights[0][0]));
        while (queue.size() > 0) {
            Neighbor current = queue.poll();
            if (captured[current.row][current.column] >= 0) continue;
            captured[current.row][current.column] = current.distance;
            if ((current.row == heights.length - 1) && (current.column == heights[0].length - 1)) return current.distance;
            for (Neighbor neighbor : getNeighbors(current, heights)) {
                if (captured[neighbor.row][neighbor.column] == -1) {
                    neighbor.distance = Math.max(current.distance , Math.abs(current.height - neighbor.height));
                    queue.offer(neighbor);
                }
            }
        }

        // if we reach this line, the most right bottom cell was not reachable for some reason
        return captured[heights.length - 1][heights[0].length - 1];
    }

    private static List<Neighbor> getNeighbors(Neighbor node, int[][] heights) {
        List<Neighbor> result = new ArrayList<>();
        Neighbor neighbor;
        // left
        if (node.column - 1 >= 0) {
            neighbor = new Neighbor(node.row, node.column - 1, heights[node.row][node.column - 1]);
            result.add(neighbor);
        }
        // right
        if (node.column + 1 < heights[0].length) {
            neighbor = new Neighbor(node.row, node.column + 1, heights[node.row][node.column + 1]);
            result.add(neighbor);
        }
        // up
        if (node.row - 1 >= 0) {
            neighbor = new Neighbor(node.row - 1, node.column, heights[node.row - 1][node.column]);
            result.add(neighbor);
        }
        // bottom
        if (node.row + 1 < heights.length) {
            neighbor = new Neighbor(node.row + 1, node.column, heights[node.row + 1][node.column]);
            result.add(neighbor);
        }

        return result;
    }
}