package gateway;

public class StringMatcher {
    /**
     * Returns the number of operations (adding a character, deleting a character, changing a character) needed
     * to get from a start string to a target string
     *
     * @param word1 the start string
     * @param word2 the target string
     * @return the number of operations needed to convert word1 to word2
     */
    public int editDistance(String word1, String word2) {
        int length1 = word1.length();
        int length2 = word2.length();
        // dp[i][j] represents the edit distance of word[i:] and word[j:]
        int[][] dp = new int[length1 + 1][length2 + 1];

        // preprocess dp matrix
        // edit distance from "" to word[j:]
        for (int j = length2 - 1; j >= 0; j--) {
            dp[length1][j] = dp[length1][j + 1] + 1;
        }
        // edit distance from word[i:] to ""
        for (int i = length1 - 1; i >= 0; i--) {
            dp[i][length2] = dp[i + 1][length2] + 1;
        }

        // fill in remainder of dp matrix
        for (int i = length1 - 1; i >= 0; i--) {
            for (int j = length2 - 1; j >= 0; j--) {
                if (word1.charAt(i) == word2.charAt(j)) {
                    dp[i][j] = dp[i + 1][j + 1];
                } else {
                    int delete1 = dp[i + 1][j];
                    int delete2 = dp[i][j + 1];
                    int replace = dp[i + 1][j + 1];
                    dp[i][j] = Math.min(Math.min(delete1, delete2), replace) + 1;
                }
            }
        }
        return dp[0][0];
    }
}