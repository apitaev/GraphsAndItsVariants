import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayDeque;
/**
 * This class implements a bidirectional search solution for the leetcode problem:<code>752</code>
 * https://leetcode.com/problems/open-the-lock/
 * Observations:
 * <ul>
 *     <li>Start BFS from source and from the target</li>
 *     <li>Both searches meet if a node visited from source and it is also visited from target </li>
 *     <li>source - target distance : distance(node from source) + distance(node from target)</li>
 * </ul>
 */
class OpneLockBD {
    public int openLock(String[] deadends, String target) {
        // edges cases;
        // if source == target
        if ("0000".equals(target)) return 0;
        // if source is in the deadends
        if (isDeadend("0000", deadends)) return -1;
        // if target is in the deadends
        if (isDeadend(target, deadends)) return -1;
        // run bidirectional bfs from source and from target
        Map<String, Integer> distancesL = new HashMap<>();
        distancesL.put("0000", 0);
        Map<String, Integer> distancesR = new HashMap<>();
        distancesR.put(target, 0);
        ArrayDeque<String> queueL = new ArrayDeque<>();
        queueL.push("0000");
        ArrayDeque<String> queueR = new ArrayDeque<>();
        queueR.push(target);
        while (queueL.size() > 0 && queueR.size() > 0) {
            String current = queueL.poll();
            for (String neighbor : getNeighbors(current, deadends)) {
                if (distancesL.get(neighbor) == null) {
                    distancesL.put(neighbor, distancesL.get(current) + 1);
                    queueL.offer(neighbor);
                    if (distancesR.get(neighbor) != null) {
                        return distancesL.get(neighbor) + distancesR.get(neighbor);
                    }
                }
            }
            current = queueR.poll();
            for (String neighbor : getNeighbors(current, deadends)) {
                if (distancesR.get(neighbor) == null) {
                    distancesR.put(neighbor, distancesR.get(current) + 1);
                    queueR.offer(neighbor);
                    if (distancesL.get(neighbor) != null) {
                        return distancesL.get(neighbor) + distancesR.get(neighbor);
                    }
                }
            }
        }
        return -1;
    }
    private static boolean isDeadend(String s, String [] deadends) {
        for (String deadend : deadends) {
            if (s.equals(deadend)) return true;
        }
        return false;
    }

    private static List<String> getNeighbors(String s, String [] deadends) {
        List<String> result = new ArrayList<>();
        StringBuilder slate;
        for (int i = 0; i < 4; i++) {
            slate = new StringBuilder(s);
            char current = slate.charAt(i);
            int digit = current - '0';
            // increse digit
            int nextDigit = (digit + 1) % 10;
            slate.setCharAt(i, (char) (nextDigit + '0'));
            String neighbor = slate.toString();
            if (!isDeadend(neighbor, deadends)) {
                result.add(neighbor);
            }
            // decreae digit
            nextDigit = (digit - 1) % 10;
            if (nextDigit < 0) nextDigit = 9;
            slate.setCharAt(i, (char)(nextDigit + '0'));
            neighbor = slate.toString();
            if (!isDeadend(neighbor, deadends)) {
                result.add(neighbor);
            }
        }
        return result;
    }
}