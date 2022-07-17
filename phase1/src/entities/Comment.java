package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Comment implements Serializable {
    /**
     * the id of the parent (which is either a Post or a Comment)
     */
    private UUID postId;
    /**
     * the content of the comment
     */
    private String content;
    /**
     * the username of the user that wrote the comment
     */
    private String author;
    /**
     * the time in which the comment was created
     */
    private LocalDateTime timePosted;
    /**
     * the id of the comment
     */
    private UUID id;

    public Comment(UUID postId, String content, String author) {
        this.postId = postId;
        this.content = content;
        this.author = author;
        this.timePosted = LocalDateTime.now();
        id = UUID.randomUUID();
    }

    /**
     * returns the content of this comment
     *
     * @return String of the content of this comment
     */
    public String getContent() {
        return content;
    }

    /**
     * returns the author of this comment as a String of a username
     *
     * @return String of username of the author of this comment
     */
    public String getAuthor() {
        return author;
    }

    /**
     * returns unique identifier of this comment as UUID object
     *
     * @return UUID object identifying this comment
     */
    public UUID getId() {
        return id;
    }

    /**
     * returns LocalDateTime object representing when this comment
     * was posted
     *
     * @return LocalDateTime object signifying when comment was posted
     */
    public LocalDateTime getTimePosted() {
        return timePosted;
    }

    /**
     * returns a unique identifier of this initialization as UUID
     *
     * @return UUID object uniquely identifying this comment instance
     */
    public UUID getPostId() {
        return this.postId;
    }
}
