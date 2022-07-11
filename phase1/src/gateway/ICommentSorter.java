package gateway;

import entities.Comment;

import java.util.ArrayList;

public interface ICommentSorter {
    /**
     * Sort the given ArrayList of comments by mutation
     *
     * @param comments list of comments to be sorted
     */
     ArrayList<Comment> sort(ArrayList<Comment> comments);

    /**
     *
     * @return the method of the comment sorter
     */

    String printType();
}
