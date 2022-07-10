package entities;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Post implements Serializable {
    /**
     * the title of a post
     */
    String title;
    /**
     * the content of a post
     */
    String content;
    /**
     * the username of the user that wrote the post
     */
    String author;
    /**
     * the time in which the post was created
     */
    LocalDateTime timePosted;
    /**
     * the id of the post
     */
    UUID id;

    public Post(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.timePosted = LocalDateTime.now();
        id = UUID.randomUUID();
    }

    public String getTitle() {
        return title;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimePosted() {
        return timePosted;
    }
}
