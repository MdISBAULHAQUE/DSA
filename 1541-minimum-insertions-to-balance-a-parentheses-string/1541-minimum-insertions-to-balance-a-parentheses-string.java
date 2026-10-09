
class Solution {
    public int minInsertions(String s) {
        int insertions = 0;
        int need = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == '(') {
                // We need two closing parentheses
                if (need % 2 == 1) {
                    insertions++;
                    need--;
                }
                need += 2;
            } else {
                need--;

                // No opening parenthesis is available
                if (need < 0) {
                    insertions++;
                    need = 1;
                }
            }
        }

        return insertions + need;
    }
}
