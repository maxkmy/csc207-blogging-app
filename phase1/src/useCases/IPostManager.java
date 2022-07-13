package useCases;

import entities.Post;
import gateway.ICommentSorter;
import gateway.IPostSorter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public interface IPostManager {
    /**
     * Return a list of posts written by the account with the provided username.
     *
     * @param username a string representing a username of a user.
     * @return a list of posts written by an account with the provided username
     */
    ArrayList<Post> getPostsWrittenBy(String username);

    /**
     * Delete all posts written by the account with the provided username.
     *
     * @param username a string representing a username of a user.
     */
    void deletePostsWrittenBy(String username);

    /**
     * Add a new post given metadata about the post.
     *
     * @param title   the title of the post
     * @param content the content of the post
     * @param author  the username of the account that wrote the post
     * @return the id of the newly added post
     */
    UUID addPost(String title, String content, String author);

    /**
     * Delete a post based on the id of the post.
     *
     * @param id the id of the post to be deleted.
     */
    void deletePost(UUID id);

    /**
     * Return a post based on the id of the post.
     *
     * @param id the id of the post to be returned.
     * @return the post with an id that matches the provided id. .
     */
    Post getPost(UUID id);

    /**
     * Saves the current data.
     */
    void save();


    /**
     * Returns the Mapping of UUID and Post
     */
    HashMap<UUID,Post> getMap();


    /**
     * Set the postSorter to be used
     *
     * @param postSorter an IPostSorter strategy for sorting comments
     */
    void setPostSorter(IPostSorter postSorter);

}
