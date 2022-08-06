package controllers;

import dataMapper.DataMapper;
import entities.Post;
import useCases.AccountManager;
import useCases.ManagerData;
import useCases.PostManager;

import java.util.*;

public class PostController {
    /**
     * a use case responsible for managing accounts
     */
    private AccountManager accountManager;
    /**
     * a use case responsible for managing posts
     */
    private PostManager postManager;

    /**
     * Constructor of a controller for posts
     *
     * @param managerData an object that groups use cases together
     */
    public PostController(ManagerData managerData) {
        accountManager = managerData.getAccountManager();
        postManager = managerData.getPostManager();
    }

    /**
     * Add a post
     *
     * @param title title of the post
     * @param content content of the post
     * @param author author of the post
     */
    public void addPost(String title, String content, String author) {
        postManager.addPost(title, content, author);
    }

    /**
     * Returns posts written by an author
     *
     * @param author an author
     * @return a list of posts written by an author
     */
    public List<Map<String, String>> getPostsWrittenBy(String author) {
        DataMapper postModel = new DataMapper();
        postModel.addItems(
                postManager.getPostsWrittenBy(author),
                new String[]{ "title", "author", "content", "timePosted", "id" }
        );
        return postModel.getModel();
    }

    /**
     * Deletes a post based on an Id
     *
     * @param postId the id of the post to be deleted
     */
    public void deletePost(UUID postId) {
        postManager.deletePost(postId);
    }

    /**
     * Returns the post based on a postId
     *
     * @param postId the id of the post
     * @return the post with given postId
     */
    public Map<String, String> getPost(UUID postId) {
        DataMapper postModel = new DataMapper();
        return postModel.getItemMap(
                postManager.getPost(postId),
                new String[]{ "title", "author", "content", "timePosted", "id" }
        );
    }

    /**
     * Returns a list of posts written by followees
     *
     * @param username a username
     * @return a list of posts written by followees of the user with provided username
     */
    public List<Map<String, String>> getFollowingPosts(String username) {
        DataMapper postModel = new DataMapper();
        HashSet<String> followees = accountManager.getFolloweesOf(username);
        ArrayList<Post> postsList = new ArrayList<>();
        for (String followee : followees) { postsList.addAll(postManager.getPostsWrittenBy(followee)); }
        postModel.reset();
        postModel.addItems(
                postsList,
                new String[]{ "title", "author", "content", "timePosted", "id"}
        );
        return postModel.getModel();
    }
}