package gateway;

import entities.Comment;

import java.util.ArrayList;
import java.util.Comparator;

public class CommentNewestToOldestSorter implements ICommentSorter {
    private class CommentNewestToOldestComparator implements Comparator<Comment> {
        @Override
        public int compare(Comment c1, Comment c2) {
            if (c1.getTimePosted().isEqual(c2.getTimePosted())) {
                return 0;
            } else if (c1.getTimePosted().isAfter(c2.getTimePosted())) {
                return -1;
            }
            return 1;
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public ArrayList<Comment> sort(ArrayList<Comment> comments) {
        comments.sort(new CommentNewestToOldestComparator());
        return comments;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String printType() {
        return "Newest to oldest";
    }
}
