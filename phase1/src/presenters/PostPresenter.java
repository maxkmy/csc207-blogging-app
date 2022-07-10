package presenters;

import java.util.HashMap;
import java.util.ArrayList;

public class PostPresenter {
    /**
     * Displays a post to user.
     *
     * @param post a post that needs to be displayed to user
     */
    public void printPost(HashMap<String, String> post) {
        System.out.println("Title: " + post.get("title"));
        System.out.println("Written by: " + post.get("author"));
        System.out.println("Time posted: " + post.get("timePosted"));
        System.out.println("Content: " + post.get("content"));
        System.out.println();
    }

    /**
     * Displays posts to users
     *
     * @param posts posts that needs to be displayed to users
     */
    public void printPosts(ArrayList<HashMap<String, String>> posts) {
        int postNumber = 1;
        for (HashMap<String, String> post : posts) {
            System.out.println("Post " + postNumber);
            printPost(post);
            postNumber++;
        }
    }
}
