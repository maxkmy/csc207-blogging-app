package useCases;

import entities.Comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CommentManager implements  ICommentManager{
    /**
     * a mapping of id of the comment to the comment entity
     */
    HashMap<UUID, Comment> comments;

    /**
     * Constructor of a use case responsible for managing users.
     *
     * @param comments a mapping of username of the account to the account entity
     */
    public CommentManager(HashMap<UUID, Comment> comments) {
        this.comments = comments;
    }

    /**
     * @inheritDoc
     */
    @Override
    public ArrayList<Comment> getCommentsWrittenBy(String username) {
        ArrayList<Comment> comments = new ArrayList<>();
        for (UUID id : this.comments.keySet()) {
            Comment comment = this.comments.get(id);
            if (comment.getAuthor().equals(username)) {
                comments.add(comment);
            }
        }
        return comments;
    }

    /**
     * @inheritDoc
     */
    @Override
    public ArrayList<Comment> getCommentsUnder(UUID postId) {
        ArrayList<Comment> comments = new ArrayList<>();
        for (UUID id : this.comments.keySet()) {
            Comment comment = this.comments.get(id);
            if (comment.getPostId().equals(postId)) {
                comments.add(comment);
            }
        }
        return comments;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteCommentsWrittenBy(String username) {
        for (Comment comment : getCommentsWrittenBy(username)) {
            deleteComment(comment.getId());
        }
    }

    private Comment createComment(UUID postId, String content, String author) {
        return new Comment(postId, content, author);
    }

    /**
     * @inheritDoc
     */
    @Override
    public UUID addComment(UUID postID, String content, String author) {
        Comment comment = createComment(postID, content, author);
        comments.put(comment.getId(), comment);
        return comment.getId();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteComment(UUID id) {
        comments.remove(id);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Comment getComment(UUID id) {
        return comments.get(id);
    }

    /**
     * @inheritDoc
     */
    @Override
    public HashMap<UUID, Comment> getMap() {
        return comments;
    }
}
