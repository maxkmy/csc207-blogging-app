package presenters;

import java.util.ArrayList;
import java.util.HashMap;

public class CommentPresenter {
    /**
     * Display a comment to the user
     *
     * @param comment a comment to be displayed to the user
     */
    public void printPost(HashMap<String, String> comment) {
        System.out.println("Comment: " + comment.get("content"));
        System.out.println("Written by: " + comment.get("author"));
        System.out.println("Time posted: " + comment.get("timePosted"));
        System.out.println();
    }

    /**
     * Displays comments to users
     *
     * @param comments comments that need to be displayed to users
     */
    public void printPosts(ArrayList<HashMap<String, String>> comments) {
        for (HashMap<String, String> comment : comments) {
            printPost(comment);
        }
    }
}
