package useCases;

import java.util.List;
import java.util.UUID;
import java.util.HashMap;
import java.util.ArrayList;
import entities.Post;
import gateway.IPostSorter;
import gateway.IReader;
import gateway.IWriter;

public class PostManager {
    /**
     * a mapping of id of the post to the post entity
     */
    private HashMap<UUID, Post> posts = new HashMap<>();
    /**
     * a gateway responsible for writing objects
     */
    private IWriter writer;
    /**
     *  a sorter that sorts an arraylist of posts
     */
    private IPostSorter postSorter;

    /**
     * Constructor of a use case responsible for managing posts.
     *
     * @param reader a gateway responsible for reading objects
     * @param writer a gateway responsible for writing objects
     */
    public PostManager(IReader reader, IWriter writer, IPostSorter postSorter) {
        this.writer = writer;
        posts = reader.read(posts.getClass());
        this.postSorter = postSorter;
    }

    /**
     * Return a list of posts written by the account with the provided username.
     *
     * @param username a string representing a username of a user.
     * @return a list of posts written by an account with the provided username
     */
    public List<Post> getPostsWrittenBy(String username) {
        ArrayList<Post> posts = new ArrayList<>();
        for (UUID id : this.posts.keySet()) {
            Post post = this.posts.get(id);
            if (post.getAuthor().equals(username)) {
                posts.add(post);
            }
        }
        if (!posts.isEmpty()) {
            this.postSorter.sort(posts);
        }
        return posts;
    }

    /**
     * Delete all posts written by the account with the provided username.
     *
     * @param username a string representing a username of a user.
     */
    public void deletePostsWrittenBy(String username) {
        for (Post post : getPostsWrittenBy(username)) {
            deletePost(post.getId());
        }
    }

    private Post createPost(String title, String content, String author) {
        return new Post(title, content, author);
    }

    /**
     * Add a new post given metadata about the post.
     *
     * @param title   the title of the post
     * @param content the content of the post
     * @param author  the username of the account that wrote the post
     * @return the id of the newly added post
     */
    public UUID addPost(String title, String content, String author) {
        Post post = createPost(title, content, author);
        posts.put(post.getId(), post);
        return post.getId();
    }

    /**
     * Delete a post based on the id of the post.
     *
     * @param id the id of the post to be deleted.
     */
    public void deletePost(UUID id) {
        posts.remove(id);
    }

    /**
     * Return a post based on the id of the post.
     *
     * @param id the id of the post to be returned.
     * @return the post with an id that matches the provided id. .
     */
    public Post getPost(UUID id) {
        return posts.get(id);
    }

    /**
     * Saves the current data.
     */
    public void save() {
        writer.write(posts);
    }
}
