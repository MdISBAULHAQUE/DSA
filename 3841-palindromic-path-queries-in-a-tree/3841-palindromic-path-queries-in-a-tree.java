import java.util.*;

class Solution {
    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        int LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        int[][] up = new int[LOG][n];
        int[] tin = new int[n], tout = new int[n];
        int[] depth = new int[n], mask = new int[n];
        int[] bit = new int[n + 2];
        char[] chars = s.toCharArray();
        int[] timer = {0};

        dfs(0, -1, adj, up, tin, tout, depth, mask, chars, timer, LOG);

        class Fenwick {
            void update(int i, int val) {
                while (i < bit.length) {
                    bit[i] ^= val;
                    i += i & -i;
                }
            }

            int query(int i) {
                int res = 0;
                while (i > 0) {
                    res ^= bit[i];
                    i -= i & -i;
                }
                return res;
            }

            void rangeUpdate(int l, int r, int val) {
                update(l, val);
                update(r + 1, val);
            }
        }

        Fenwick fw = new Fenwick();

        for (int i = 0; i < n; i++) {
            fw.rangeUpdate(tin[i], tout[i], mask[i]);
        }

        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.split(" ");
            int u = Integer.parseInt(p[1]);

            if (p[0].equals("update")) {
                int newMask = 1 << (p[2].charAt(0) - 'a');
                int diff = mask[u] ^ newMask;
                fw.rangeUpdate(tin[u], tout[u], diff);
                mask[u] = newMask;
                chars[u] = p[2].charAt(0);
            } else {
                int v = Integer.parseInt(p[2]);
                int lca = getLCA(u, v, up, depth, tin, tout, LOG);
                int pathMask = fw.query(tin[u]) ^ fw.query(tin[v]) ^ mask[lca];
                ans.add((pathMask & (pathMask - 1)) == 0);
            }
        }

        return ans;
    }

    private void dfs(int u, int parent, List<Integer>[] adj, int[][] up,
                     int[] tin, int[] tout, int[] depth, int[] mask,
                     char[] chars, int[] timer, int LOG) {
        tin[u] = ++timer[0];
        up[0][u] = parent == -1 ? u : parent;
        mask[u] = 1 << (chars[u] - 'a');

        for (int k = 1; k < LOG; k++) {
            up[k][u] = up[k - 1][up[k - 1][u]];
        }

        for (int v : adj[u]) {
            if (v == parent) continue;
            depth[v] = depth[u] + 1;
            dfs(v, u, adj, up, tin, tout, depth, mask, chars, timer, LOG);
        }

        tout[u] = timer[0];
    }

    private int getLCA(int u, int v, int[][] up, int[] depth,
                       int[] tin, int[] tout, int LOG) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int k = 0; k < LOG; k++) {
            if ((diff & (1 << k)) != 0) {
                u = up[k][u];
            }
        }

        if (u == v) return u;

        for (int k = LOG - 1; k >= 0; k--) {
            if (up[k][u] != up[k][v]) {
                u = up[k][u];
                v = up[k][v];
            }
        }

        return up[0][u];
    }
}