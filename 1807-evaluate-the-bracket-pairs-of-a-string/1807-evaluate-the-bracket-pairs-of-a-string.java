import java.util.*;

class Solution {
    public String evaluate(String s, List<List<String>> knowledge) {
        
        Map<String, String> map = new HashMap<>();

        // Store key-value pairs in HashMap
        for (List<String> pair : knowledge) {
            map.put(pair.get(0), pair.get(1));
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) == '(') {
                int j = i + 1;

                // Find closing bracket
                while (s.charAt(j) != ')') {
                    j++;
                }

                String key = s.substring(i + 1, j);

                // Get value, or "?" if key doesn't exist
                result.append(map.getOrDefault(key, "?"));

                i = j;
            } 
            else {
                result.append(s.charAt(i));
            }
        }

        return result.toString();
    }
}