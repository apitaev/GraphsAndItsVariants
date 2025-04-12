import java.util.List;
import java.util.Comparator;
import java.util.Objects;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
/**
 * This class is a BFS implementation for the leetcode problem: <code>365</code>:
 * https://leetcode.com/problems/water-and-jug-problem/
 * Observation: every state of jugs can be represented as a graph node.
 */
class WaterAndJugProblem {
    static class JugsState {
        Integer first;
        Integer second;
        JugsState(Integer first, Integer second) {
            this.first = first;
            this.second = second;
        }
        @Override
        public boolean equals(Object object) {
            JugsState jugsState = (JugsState) object;
            return this.first.equals(jugsState.first) && this.second.equals(jugsState.second);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }
    }
    public boolean canMeasureWater(int x, int y, int target) {
        if (x + y == target) return true;
        Map<JugsState, Integer> visited = new HashMap<>();
        JugsState start = new JugsState(0, 0);
        visited.put(start, 1);
        ArrayDeque<JugsState> queue = new ArrayDeque<>();
        queue.offer(start);
        while (queue.size() > 0) {
            JugsState current = queue.poll();
            for (JugsState neighbor : getNeighbors(current, x, y)) {
                if (visited.get(neighbor) == null) {
                    visited.put(neighbor, 1);
                    queue.offer(neighbor);
                    if (neighbor.first + neighbor.second == target) return true;
                }
            }

        }
        return false;
    }

    public static List<JugsState> getNeighbors(JugsState state, int x, int y) {
        List<JugsState> result = new ArrayList<>();
        // 1. State: 0, 0 --> [fill first, fill second, fill both completely]
        if (state.first == 0 && state.second == 0) {
            result.add(new JugsState(x, 0));
            result.add(new JugsState(0, y));
            result.add(new JugsState(x, y));
        } else if (state.first == x && state.second == y) {
            // 2. State: x, y --> [empty first, empty second, empty both completely]
            result.add(new JugsState(0, y));
            result.add(new JugsState(x, 0));
            result.add(new JugsState(0, 0));
        } else if (state.first == 0 && state.second == y) {
            // 3. State: 0, y --> [fill first, empty second, fill first and empty second, pour from second to the first]
            result.add(new JugsState(x, y));
            result.add(new JugsState(0, 0));
            result.add(new JugsState(x, 0));
            if (y >= x) {
                result.add(new JugsState(x, y - x));
            } else {
                result.add(new JugsState(x - y, 0));
            }

        } else if (state.first == x && state.second == 0) {
            // 4. State: x, 0 -->[empty first, fill second, empty first and fill second, pour from first to the second]
            result.add(new JugsState(0, 0));
            result.add(new JugsState(0, y));
            if (x >= y) {
                result.add(new JugsState(x - y, y));
            } else {
                result.add(new JugsState(0, y - x));
            }
        } else if (state.first > 0 && state.first < x && state.second > 0 && state.second < y) {
            // 5. State: x1, y1 --> [pour from first to the second, pour from second to the first]
            if (state.first >= y - state.second) {
                result.add(new JugsState(state.first - y, y));
            } else {
                result.add(new JugsState(0, state.first + state.second));
            }
            if (state.second  >= x - state.first) {
                result.add(new JugsState(x, state.second - state.first));
            } else {
                result.add(new JugsState(state.first + state.second, 0));
            }
        } else if (state.first == 0 && state.second > 0 && state.second < y) {
            // 6. State: 0, y1 --> [fill first, pour from second to the first]
            result.add(new JugsState(x, state.second));
            if (state.second >= y) {
                result.add(new JugsState(x, state.second - x));
            } else {
                result.add(new JugsState(state.second, 0));
            }
        } else if (state.first == x && state.second > 0 && state.second < y) {
            // 7. State: x, y1 --> [empty first, pour from the first to the second]
            result.add(new JugsState(0, state.second));
            if (x > y - state.second) {
                result.add(new JugsState(state.first - y + state.second, y));
            } else {
                result.add(new JugsState(0, state.second + x));
            }
        } else if (state.first > 0 && state.first < x && state.second == 0) {
            // 8. State: x1, 0 --> [pour from first to the second, fill second]
            if (state.first >= y) {
                result.add(new JugsState(state.first - y, y));
            } else {
                result.add(new JugsState(0, y - state.first));
            }
            result.add(new JugsState(state.first, 0));
        } else {
            // 9. State: x1, y -->[empty second, pour from the second to the first]
            result.add(new JugsState(state.first, 0));
            if (y > x - state.first) {
                result.add(new JugsState(x, y - state.first));
            } else {
                result.add(new JugsState(x - state.first - y, 0));
            }
        }
        return result;
    }
}