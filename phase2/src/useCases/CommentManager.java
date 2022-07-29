package useCases;

import entities.Comment;
import gateway.ICommentSorter;
import gateway.IReader;
import gateway.IWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CommentManager {
    /**
     * a mapping of id of the comment to the comment entity
     */
    private HashMap<UUID, Comment> comments = new HashMap<>();
    /**
     * a gateway responsible for reading objects
     */
    private IReader reader;
    /**
     * a gateway responsible for writing objects
     */
    private IWriter writer;
    /**
     * a strategy for sorting comments by a particular criterion
     */
    private ICommentSorter commentSorter;

    /**
     * Constructor of a use case responsible for managing comments.
     *
     * @param reader a gateway responsible for reading objects
     * @param writer a gateway responsible for writing objects
     */
    public CommentManager(IReader reader, IWriter writer) {
        this.reader = reader;
        this.writer = writer;
        comments = reader.read(comments.getClass());
    }

    /**
     * Return a list of comments written by the account with the provided username
     *
     * @param username a string representing a username of a user
     * @return a list of comments written by an account with the provided username
     */
    public ArrayList<Comment> getCommentsWrittenBy(String username) {
        ArrayList<Comment> comments = new ArrayList<>();
        for (UUID id : this.comments.keySet()) {
            Comment comment = this.comments.get(id);
            if (comment.getAuthor().equals(username)) {
                comments.add(comment);
            }
        }
        if (!comments.isEmpty()) {
            this.commentSorter.sort(comments);
        }
        return comments;
    }

    /**
     * Return a list of all the comments for a post
     *
     * @param postId the id of the post/parent
     * @return a list of all the comments for a post
     */
    public ArrayList<Comment> getCommentsUnder(UUID postId) {
        ArrayList<Comment> comments = new ArrayList<>();
        for (UUID id : this.comments.keySet()) {
            Comment comment = this.comments.get(id);
            if (comment.getPostId().equals(postId)) {
                comments.add(comment);
            }
        }
        this.commentSorter.sort(comments);
        return comments;
    }

    /**
     * Delete all comments written by the account with the provided username
     *
     * @param username a string representing a username of a user
     */
    public void deleteCommentsWrittenBy(String username) {
        for (Comment comment : getCommentsWrittenBy(username)) {
            deleteComment(comment.getId());
        }
    }

    private Comment createComment(UUID postId, String content, String author) {
        return new Comment(postId, content, author);
    }

    /**
     * Add a new comment given metadata about the comment
     *
     * @param postID  the id of the post/parent
     * @param content the content of the comment
     * @param author  the username of the account that wrote the post
     * @return the id of the newly added post
     */
    public UUID addComment(UUID postID, String content, String author) {
        Comment comment = createComment(postID, content, author);
        comments.put(comment.getId(), comment);
        return comment.getId();
    }

    /**
     * Delete a comment based on the id of the comment
     *
     * @param id the id of the comment to be deleted
     */
    public void deleteComment(UUID id) {
        comments.remove(id);
    }

    /**
     * Return a Comment entity based on the id of the comment
     *
     * @param id the id of the comment to be returned
     * @return the Comment entity with an id that matches the provided id
     */
    public Comment getComment(UUID id) {
        return comments.get(id);
    }

    /**
     * Set the commentSorter to be used
     *
     * @param commentSorter an ICommentSorter strategy for sorting comments
     */
    public void setCommentSorter(ICommentSorter commentSorter) { this.commentSorter = commentSorter; }

    /**
     * Saves the current data.
     */
    public void save() {
        writer.write(comments);
    }
}
