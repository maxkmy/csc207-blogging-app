package presenters;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfilePresenter {
    public void present(ArrayList<HashMap<String, String>> posts) {
        System.out.println("number of posts: " + posts.size());
        int postNumber = 1;
        for (HashMap<String, String> post : posts) {
            System.out.println("Post " + postNumber);
            System.out.println("Title: " + post.get("title"));
            System.out.println("Written by: " + post.get("author") + "\n");
            System.out.println(post.get("content") + "\n\n");
            postNumber++;
        }
    }
}
