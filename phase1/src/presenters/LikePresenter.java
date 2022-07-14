package presenters;

import java.util.ArrayList;
import java.util.HashMap;

public class LikePresenter {
    /**
     * Display a liker of the post to the user
     *
     * @param like a like to be displayed to the user
     */
    public void printLike(HashMap<String, String> like) {
        System.out.println(like.get("liker"));

    }

    /**
     * Displays a list of likers to users
     *
     * @param likes likes that need to be displayed to users
     */
    public void printLikes(ArrayList<HashMap<String, String>> likes) {
        System.out.println("Users who have liked this post: ");
        for (HashMap<String, String> like : likes) {
            printLike(like);
        }
    }

    /**
     * Displays total likes of the post
     *
     * @param totalLikes the total number of likes this post has
     */
    public void printTotalLikes( int totalLikes) {
        System.out.println("Total number of likes: " + totalLikes);
    }
}
