package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Comment implements Serializable {
    /**
     * the id of the parent (which is either a Post or a Comment)
     */
    UUID postId;
    /**
     * the content of the comment
     */
    String content;
    /**
     * the username of the user that wrote the comment
     */
    String author;
    /**
     * the time in which the comment was created
     */
    LocalDateTime timePosted;
    /**
     * the id of the comment
     */
    UUID id;

    public Comment(UUID postId, String content, String author) {
        this.postId = postId;
        this.content = content;
        this.author = author;
        this.timePosted = LocalDateTime.now();
        id = UUID.randomUUID();
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getTimePosted() { return timePosted; }

    public UUID getPostId() {
        return this.postId;
    }
}
