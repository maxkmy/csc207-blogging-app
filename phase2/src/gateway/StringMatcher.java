package gateway;

public class StringMatcher {
    // returns the distance between 2 words where we can insert, delete or replace chars
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