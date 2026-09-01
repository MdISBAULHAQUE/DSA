import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int[][] litter = new int[m][n];
        int sr = 0, sc = 0, count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    sr = i;
                    sc = j;
                } else if (ch == 'L') {
                    litter[i][j] = count++;
                }
            }
        }

        if (count == 0) return 0;

        int totalMask = (1 << count) - 1;

        boolean[][][][] visited =
            new boolean[m][n][energy + 1][1 << count];

        Queue<int[]> q = new LinkedList<>();

        q.offer(new int[]{sr, sc, energy, 0});
        visited[sr][sc][energy][0] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        int moves = 0;

        while (!q.isEmpty()) {
            int size = q.size();

            while (size-- > 0) {
                int[] cur = q.poll();

                int r = cur[0];
                int c = cur[1];
                int e = cur[2];
                int mask = cur[3];

                if (mask == totalMask) return moves;

                if (e == 0) continue;

                for (int d = 0; d < 4; d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];

                    if (nr < 0 || nr >= m || nc < 0 || nc >= n)
                        continue;

                    if (classroom[nr].charAt(nc) == 'X')
                        continue;

                    int ne = e - 1;
                    int nmask = mask;

                    if (classroom[nr].charAt(nc) == 'R') {
                        ne = energy;
                    }

                    if (classroom[nr].charAt(nc) == 'L') {
                        nmask |= (1 << litter[nr][nc]);
                    }

                    if (!visited[nr][nc][ne][nmask]) {
                        visited[nr][nc][ne][nmask] = true;
                        q.offer(new int[]{nr, nc, ne, nmask});
                    }
                }
            }

            moves++;
        }

        return -1;
    }
}