package entities;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Post implements Serializable {
    /**
     * the title of a post
     */
    private String title;
    /**
     * the content of a post
     */
    private String content;
    /**
     * the username of the user that wrote the post
     */
    private String author;
    /**
     * the time in which the post was created
     */
    private LocalDateTime timePosted;
    /**
     * the id of the post
     */
    private UUID id;

    public Post(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.timePosted = LocalDateTime.now();
        id = UUID.randomUUID();
    }

    /**
     * returns the title of this post
     *
     * @return String of the title of this post
     */
    public String getTitle() {
        return title;
    }

    /**
     * returns the content of this post
     *
     * @return String of the content of this post
     */
    public String getContent() {
        return content;
    }

    /**
     * returns the author of this post as a username
     *
     * @return String of the username of the Author of this post
     */
    public String getAuthor() {
        return author;
    }

    /**
     * returns the unique id of this post as a UUID object
     *
     * @return UUID object uniquely identifying this post
     */
    public UUID getId() {
        return id;
    }

    /**
     * sets a new title this post
     *
     * @param title String of the title to be set for this post
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * replaces previous content of this post
     *
     * @param content String of the content to be set for this post
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * returns the initial time this post was authored as a
     * LocalDateTime object
     *
     * @return LocalDateTime object signifying when this post was authored
     */
    public LocalDateTime getTimePosted() {
        return timePosted;
    }
}
