import java.util.*;

class Solution {

    public List<String> removeInvalidParentheses(String s) {

        List<String> result = new ArrayList<>();

        // BFS starts with the original string
        Set<String> current = new HashSet<>();
        current.add(s);

        while (!current.isEmpty()) {

            // Check if any valid string exists at this level
            for (String str : current) {
                if (isValid(str)) {
                    result.add(str);
                }
            }

            // If valid strings found, this is the minimum removal
            if (!result.isEmpty()) {
                return result;
            }

            // Generate next level by removing one parenthesis
            Set<String> next = new HashSet<>();

            for (String str : current) {

                for (int i = 0; i < str.length(); i++) {

                    // Only remove parentheses
                    if (str.charAt(i) != '(' && str.charAt(i) != ')') {
                        continue;
                    }

                    // Avoid duplicate states
                    if (i > 0 && str.charAt(i) == str.charAt(i - 1)) {
                        continue;
                    }

                    String nextString =
                            str.substring(0, i) + str.substring(i + 1);

                    next.add(nextString);
                }
            }

            current = next;
        }

        return result;
    }

    // Checks whether parentheses are valid
    private boolean isValid(String s) {

        int balance = 0;

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                balance++;
            } 
            else if (ch == ')') {
                balance--;
            }

            // More ')' than '('
            if (balance < 0) {
                return false;
            }
        }

        // All '(' must have matching ')'
        return balance == 0;
    }
}