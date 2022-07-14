package useCases;

import entities.Like;
import java.util.*;

public interface ILikeManager {
    /**
     * Return a list of likes created by the account with the provided username
     *
     * @param username a string representing a username of a user
     * @return a list of likes created by the account with the provided username
     */
    ArrayList<Like> getLikesByUser(String username);

    /**
     * Return a list of all the likes for a post
     *
     * @param postId the id of the post/parent
     * @return a list of all the likes for a post
     */
    ArrayList<Like> getLikesUnder(UUID postId);

    /**
     * Return the total number of likes the post has
     *
     * @param postID the id of the post/parent
     * @return an int representing the number of likes the post has
     */
    int totalLikesUnder(UUID postID);

    /**
     * Add a new like to the Post whose postID is provided
     *
     * @param postID the id of the post/parent
     * @param user the username of the account that is creating the like
     * @return the id of the newly aadded like
     */
    UUID addLike(UUID postID, String user);

    /**
     * Delete a like based on the id of the like
     *
     * @param id the id of the like to be deleted
     */
    void unlike(UUID id);

    /**
     * Return whether the user wanting to create a like can create a like ( has not already liked the post)
     *
     * @param postId  the id of the like to be deleted
     * @param user    the username of the user wanting to create a like
     * @return a boolean representing whether the user can already liked the post or not
     */
    boolean canLike(UUID postId, String user);

    /**
     * Return a Like entity based on the id of the like
     *
     * @param id the id of the like to be returned
     * @return  the Like entity with an id that matches the provided id
     */
    Like getLike(UUID id);

    /**
     * Return the UUID of the Like created by the provided user of the Post whose postId is provided
     *
     * @param postId the id of the like to be returned
     * @param user the username of the user wanting to create a like
     * @return the UUID of the Like created by the provided user of the Post whose postId is provided
     */
    UUID getIdFromPostId (UUID postId, String user);

    /**
     * Saves the current data.
     */
    void save();
}
