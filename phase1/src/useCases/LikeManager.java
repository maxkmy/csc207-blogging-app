package useCases;

import entities.Like;

import gateway.IReader;
import gateway.IWriter;

import java.util.*;

public class LikeManager implements ILikeManager {
    /**
     * a mapping of id of the like to the like entity
     */
    HashMap<UUID, Like> likes = new HashMap<>();

    /**
     * a gateway responsible for reading objects
     */
    IReader reader;

    /**
     * a gateway responsible for writing objects
     */
    IWriter writer;

    public LikeManager(IReader reader, IWriter writer){
        this.reader = reader;
        this.writer = writer;
        likes = reader.read(likes.getClass());
    }

    /**
     * @inheritDoc
     */
    @Override
    public ArrayList<Like> getLikesByUser(String username){
        ArrayList<Like> likes = new ArrayList<>();
        for (Like like : this.likes.values()){
            if (like.getLiker().equals(username)){
                likes.add(like);
            }
        }
        return likes;
    }

    /**
     * @inheritDoc
     */
    @Override
    public ArrayList<Like> getLikesUnder(UUID postId){
        ArrayList<Like> likes = new ArrayList<>();
        for (Like like : this.likes.values()){
            if (like.getPostID().equals(postId)) {
                likes.add(like);
            }
        }
        return likes;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int totalLikesUnder(UUID postId){
        ArrayList<Like> likes = new ArrayList<>();
        for (Like like : this.likes.values()){
            if (like.getPostID().equals(postId)) {
                likes.add(like);
            }
        }
        return likes.size();
    }

    /**
     * @inheritDoc
     */
    @Override
    public UUID addLike(UUID postId, String user){
        if (canLike(postId, user)) {
            Like like = new Like(postId, user);
            likes.put(like.getId(), like);
            return like.getId();
        }
        else{
            return new UUID(0,0);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void unlike(UUID id){
        likes.remove(id);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean canLike(UUID postId, String user){
        for (Like like : likes.values()){
            if (like.getPostID().equals(postId) && like.getLiker().equals(user)){
                return false;
            }
        }
        return true;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Like getLike(UUID id){ return likes.get(id); }

    /**
     * @inheritDoc
     */
    @Override
    public UUID getIdFromPostId(UUID postId, String user){
        for (Like like : likes.values()){
            if (like.getPostID().equals(postId) && like.getLiker().equals(user)){
                return like.getId();
            }
        }
        return new UUID(0,0);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void save() { writer.write(likes); }

}
