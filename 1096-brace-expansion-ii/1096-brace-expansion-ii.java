class Solution {
    public List<String> braceExpansionII(String expression) {
        Set<String> result = solve(expression);
        List<String> ans = new ArrayList<>(result);
        Collections.sort(ans);
        return ans;
    }

    private Set<String> solve(String s) {
        Set<String> result = new HashSet<>();
        List<Set<String>> parts = new ArrayList<>();

        int i = 0;

        while (i < s.length()) {
            char ch = s.charAt(i);

            if (ch == '{') {
                int start = i;
                int count = 0;

                while (i < s.length()) {
                    if (s.charAt(i) == '{') count++;
                    else if (s.charAt(i) == '}') count--;

                    if (count == 0) break;
                    i++;
                }

                String inside = s.substring(start + 1, i);

                Set<String> group = new HashSet<>();
                int level = 0;
                int prev = 0;

                for (int j = 0; j <= inside.length(); j++) {
                    if (j < inside.length()) {
                        if (inside.charAt(j) == '{') level++;
                        else if (inside.charAt(j) == '}') level--;
                    }

                    if (j == inside.length() ||
                        (inside.charAt(j) == ',' && level == 0)) {

                        String part = inside.substring(prev, j);
                        group.addAll(solve(part));
                        prev = j + 1;
                    }
                }

                parts.add(group);
            }
            else if (Character.isLetter(ch)) {
                Set<String> group = new HashSet<>();
                group.add(String.valueOf(ch));
                parts.add(group);
            }

            i++;
        }

        result.add("");

        for (Set<String> part : parts) {
            Set<String> temp = new HashSet<>();

            for (String a : result) {
                for (String b : part) {
                    temp.add(a + b);
                }
            }

            result = temp;
        }

        return result;
    }
}