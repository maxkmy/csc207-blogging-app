package useCases;

import entities.Comment;
import gateway.ICommentSorter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public interface ICommentManager {
    /**
     * Return a list of comments written by the account with the provided username
     *
     * @param username a string representing a username of a user
     * @return a list of comments written by an account with the provided username
     */
    ArrayList<Comment> getCommentsWrittenBy(String username);

    /**
     * Return a list of all the comments for a post
     *
     * @param postId the id of the post/parent
     * @return a list of all the comments for a post
     */
    ArrayList<Comment> getCommentsUnder(UUID postId);

    /**
     * Delete all comments written by the account with the provided username
     *
     * @param username a string representing a username of a user
     */
    void deleteCommentsWrittenBy(String username);

    /**
     * Add a new comment given metadata about the comment
     *
     * @param postID  the id of the post/parent
     * @param content the content of the comment
     * @param author  the username of the account that wrote the post
     * @return the id of the newly added post
     */
    UUID addComment(UUID postID, String content, String author);

    /**
     * Delete a comment based on the id of the comment
     *
     * @param id the id of the comment to be deleted
     */
    void deleteComment(UUID id);

    /**
     * Return a Comment entity based on the id of the comment
     *
     * @param id the id of the comment to be returned
     * @return the Comment entity with an id that matches the provided id
     */
    Comment getComment(UUID id);

    /**
     * Set the commentSorter to be used
     *
     * @param commentSorter an ICommentSorter strategy for sorting comments
     */
    void setCommentSorter(ICommentSorter commentSorter);

    /**
     * Saves the current data.
     */
    void save();
}
