package gateway;

import entities.Comment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CommentTimeSorter implements ICommentSorter {

    private class CommentTimeComparator implements Comparator<Comment> {
        @Override
        public int compare(Comment c1, Comment c2) {
            if (c1.getTimePosted().isEqual(c2.getTimePosted())) {
                return 0;
            } else if (c1.getTimePosted().isAfter(c2.getTimePosted())) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void sort(ArrayList<Comment> comments) {
        Collections.sort(comments, new CommentTimeComparator());
    }
}
