package useCases;

import entities.Like;
import java.util.*;

public interface ILikeManager {

    ArrayList<Like> getLikesByUser(String username);

    ArrayList<Like> getLikesUnder(UUID postId);

    int totalLikesUnder(UUID postID);
    boolean like(UUID postID, String user);

    void unlike(UUID id);

    boolean canLike(UUID postId, String user);

    Like getLike(UUID id);

    void save();
}
