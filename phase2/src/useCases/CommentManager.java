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
     * @inheritDoc
     */
    public ArrayList<Comment> getCommentsWrittenBy(String username) {
        ArrayList<Comment> comments = new ArrayList<>();
        for (UUID id : this.comments.keySet()) {
            Comment comment = this.comments.get(id);
            if (comment.getAuthor().equals(username)) {
                comments.add(comment);
            }
        }
        this.commentSorter.sort(comments);
        return comments;
    }

    /**
     * @inheritDoc
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
     * @inheritDoc
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
     * @inheritDoc
     */
    public UUID addComment(UUID postID, String content, String author) {
        Comment comment = createComment(postID, content, author);
        comments.put(comment.getId(), comment);
        return comment.getId();
    }

    /**
     * @inheritDoc
     */
    public void deleteComment(UUID id) {
        comments.remove(id);
    }

    /**
     * @inheritDoc
     */
    public Comment getComment(UUID id) {
        return comments.get(id);
    }

    /**
     * @inheritDoc
     */
    public void setCommentSorter(ICommentSorter commentSorter) { this.commentSorter = commentSorter; }

    /**
     * @inheritDoc
     */
    public void save() {
        writer.write(comments);
    }
}
