package gateway;

import entities.Post;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

public class PostTimeSorter implements IPostSorter {
    private class PostTimeComparator implements Comparator<Post> {
        @Override
        public int compare(Post p1, Post p2) {
            if (p1.getTimePosted().isEqual(p2.getTimePosted())) {
                return 0;
            } else if (p1.getTimePosted().isAfter(p2.getTimePosted())) {
                return -1;
            }
            return 1;
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public ArrayList<Post> sort(ArrayList<Post> posts) {
        posts.sort(new PostTimeComparator());
        return posts;
    }
}
