import java.util.Map;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;

/**
 * This class implements a BFS solution for the leetcode problem:<code>752</code>
 * https://leetcode.com/problems/open-the-lock/
 */
class OpenLockBfs {
    public int openLock(String[] deadends, String target) {
        // edge cases:
        // if target equals start position
        if ("0000".equals(target)) return 0;
        // if source is a deadend
        if (isDeadEnd("0000", deadends)) return -1;
        // if target is a deadend
        if(isDeadEnd(target, deadends)) return -1;

        // build graph on the fly
        Map<String, Integer> distances = new HashMap<>();
        String source = "0000";
        distances.put(source, 0);
        ArrayDeque<String> queue = new ArrayDeque<>();
        queue.offer(source);
        // run dfs
        while (queue.size() > 0) {
            String current = queue.poll();
            for (String neighbor : getNeighbors(deadends, current)) {
                if (distances.get(neighbor) == null) {
                    distances.put(neighbor, distances.get(current) + 1);
                    queue.offer(neighbor);
                    if (neighbor.equals(target)) {
                        return distances.get(neighbor);
                    }
                }
            }
        }
        // if this line is reached, it is not possible to reach the target
        return -1;
    }

    private static List<String> getNeighbors(String [] deadends, String source) {
        List<String> result = new ArrayList<>();
        StringBuilder slate;
        for (int i = 0; i < 4; i++) {
            slate = new StringBuilder(source);
            char current = slate.charAt(i);
            // convert chart to int
            int digit = current - '0';
            // increase digit
            digit = (digit + 1) % 10;
            slate.setCharAt(i, (char) (digit + '0'));
            String neighbor = slate.toString();
            if (!isDeadEnd(neighbor, deadends)) {
                result.add(neighbor);
            }
            // decrease digit
            digit = current - '0';
            digit = (digit - 1) % 10;
            if (digit < 0) digit = 9;
            slate.setCharAt(i, (char)(digit + '0'));
            neighbor = slate.toString();
            if (!isDeadEnd(neighbor, deadends)) {
                result.add(neighbor);
            }
        }
        return result;
    }

    private static boolean isDeadEnd(String s, String [] deadends) {
        for (String deadend : deadends) {
            if (s.equals(deadend)) return true;
        }
        return false;
    }
}