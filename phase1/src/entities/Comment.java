package entities;

import java.util.UUID;

public class Comment {
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
    long timePosted;
    /**
     * the id of the comment
     */
    UUID id;

    public Comment(UUID postId, String content, String author) {
        this.postId = postId;
        this.content = content;
        this.author = author;
        this.timePosted = System.currentTimeMillis();
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

    public long getTimePosted() { return timePosted; }

    public UUID getPostId() {
        return this.postId;
    }
}
