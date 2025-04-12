import java.util.List;
import java.util.Comparator;
import java.util.Objects;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
/**
 * This class is a BFS implementation for the leetcode problem: <code>773</code>:
 * https://leetcode.com/problems/sliding-puzzle/
 * Observation: every state of the PuzzleBoard can be represented as a graph node.
 */
class SlidingPuzzleBFS {
    static class BoardState {
        int a;
        int b;
        int c;
        int x;
        int y;
        int z;
        int distance;
        BoardState(int [][] arr) {
            this.a = arr[0][0];
            this.b = arr[0][1];
            this.c = arr[0][2];
            this.x = arr[1][0];
            this.y = arr[1][1];
            this.z = arr[1][2];
        }

        BoardState(int... cells) {
            this.a = cells[0];
            this.b = cells[1];
            this.c = cells[2];
            this.x = cells[3];
            this.y = cells[4];
            this.z = cells[5];
        }

        int getDistance() {
            return this.distance;
        }

        @Override
        public boolean equals(Object object) {
            BoardState boardState = (BoardState) object;
            return (this.a == boardState.a) && (this.b == boardState.b) && (this.c == boardState.c)
                    && (this.x == boardState.x) && (this.y == boardState.y) && (this.z == boardState.z);

        }
        @Override
        public int hashCode() {
            return Objects.hash(a, b, c, x, y, z);
        }

    }
    public int slidingPuzzle(int[][] board) {
        BoardState start = new BoardState(board);
        BoardState target = new BoardState(1, 2, 3, 4, 5, 0);
        if (start.equals(target)) return 0;
        // build graph on the fly
        // run Dijkstra
        Map<BoardState, Integer> distances = new HashMap<>();
        ArrayDeque<BoardState> queue = new ArrayDeque<>();
        queue.offer(start);
        while (queue.size() > 0) {
            BoardState current = queue.poll();
            distances.put(current, current.distance);
            for (BoardState neighbor : getNeighbors(current)) {
                if (distances.get(neighbor) == null) {
                    neighbor.distance = current.distance + 1;
                    queue.offer(neighbor);
                    if (neighbor.equals(target)) return neighbor.distance;
                }
            }
        }
        // return -1 if the state is note reached
        return -1;
    }

    private static List<BoardState> getNeighbors(BoardState current) {
        List<BoardState> result = new ArrayList<>();
        int a = current.a;
        int b = current.b;
        int c = current.c;
        int x = current.x;
        int y = current.y;
        int z = current.z;
        if (a == 0) {
            result.add(new BoardState(b, a, c, x, y, z));
            result.add(new BoardState(x, b, c, a, y, z));
        } else if (b == 0) {
            result.add(new BoardState(b, a, c, x, y, z));
            result.add(new BoardState(a, c, b, x, y, z));
            result.add(new BoardState(a, y, c, x, b, z));
        } else if (c == 0) {
            result.add(new BoardState(a, c, b, x, y, z));
            result.add(new BoardState(a, b, z, x, y, c));
        } else if (x == 0) {
            result.add(new BoardState(x, b, c, a, y, z));
            result.add(new BoardState(a, b, c, y, x, z));
        } else if (y == 0) {
            result.add(new BoardState(a, y, c, x, b, z));
            result.add(new BoardState(a, b, c, y, x, z));
            result.add(new BoardState(a, b, c, x, z, y));
        } else { // z == 0
            result.add(new BoardState(a, b, z, x, y, c));
            result.add(new BoardState(a, b, c, x, z, y));
        }

        return result;
    }
}