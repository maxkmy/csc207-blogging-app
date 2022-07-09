package entities;
import java.io.Serializable;
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
    long timePosted;
    /**
     * the id of the post
     */
    UUID id;

    public Post(String title, String content, String author) {
        this.title = title;
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

    public String getTitle() {
        return title;
    }
}
