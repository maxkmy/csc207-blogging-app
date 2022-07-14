package entities;
import java.io.Serializable;
import java.util.UUID;

public class Like implements Serializable {
    /**
     * the id of the parent entity being liked (either a Post or Comment)
     */
    UUID postID;
    /**
     * the username of the user liking the parent Post or Comment
     */
    String liker;

    /**
     * the unique id of the like
     */
    UUID id;

    public Like(UUID postID, String liker){
        this.postID = postID;
        this.liker = liker;
        this.id = UUID.randomUUID();
    }

    public String getLiker() {return liker;}

    public UUID getId() {return id;}

    public UUID getPostID() {return postID;}
}
