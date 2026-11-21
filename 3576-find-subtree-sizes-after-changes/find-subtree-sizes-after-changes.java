import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        List<Integer>[] tree = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        List<Integer>[] newTree = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            newTree[i] = new ArrayList<>();
        }

        int[] last = new int[26];
        Arrays.fill(last, -1);

        int[] newParent = new int[n];
        int[] previous = new int[n];

        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{0, 0}); 

        while (!stack.isEmpty()) {
            int[] curr = stack.pop();
            int node = curr[0];
            int type = curr[1];

            int ch = s.charAt(node) - 'a';

            if (type == 1) {
                last[ch] = previous[node];
                continue;
            }

            previous[node] = last[ch];
            if (last[ch] != -1) {
                newParent[node] = last[ch];
            } else {
                newParent[node] = parent[node];
            }

            last[ch] = node;

            if (node != 0) {
                newTree[newParent[node]].add(node);
            }

            stack.push(new int[]{node, 1});

            for (int i = tree[node].size() - 1; i >= 0; i--) {
                stack.push(new int[]{tree[node].get(i), 0});
            }
        }

        int[] answer = new int[n];
        Arrays.fill(answer, 1);

        Stack<Integer> order = new Stack<>();
        order.push(0);

        List<Integer> traversal = new ArrayList<>();

        while (!order.isEmpty()) {
            int node = order.pop();
            traversal.add(node);

            for (int child : newTree[node]) {
                order.push(child);
            }
        }

        // Reverse order = postorder
        for (int i = traversal.size() - 1; i >= 0; i--) {
            int node = traversal.get(i);

            for (int child : newTree[node]) {
                answer[node] += answer[child];
            }
        }

        return answer;
    }
}