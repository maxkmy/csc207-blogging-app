package gateway;

import entities.Comment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CommentTimeSorter implements ICommentSorter {

    private class CommentTimeComparator implements Comparator<Comment> {

        @Override
        public int compare(Comment c1, Comment c2) {
            return (int) (c1.getTimePosted() - c2.getTimePosted());
        }
    }

    @Override
    public void sort(ArrayList<Comment> comments) {
        Collections.sort(comments, new CommentTimeComparator());
    }
}
