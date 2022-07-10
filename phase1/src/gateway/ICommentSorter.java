package gateway;

import entities.Comment;

import java.util.ArrayList;

public interface ICommentSorter {
    /**
     * Sort the given ArrayList of comments by mutation
     *
     * @param comments
     */
    public void sort(ArrayList<Comment> comments);
}
