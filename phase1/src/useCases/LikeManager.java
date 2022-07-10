package useCases;

import entities.Like;
import gateway.IReader;
import gateway.IWriter;

import java.util.*;

public class LikeManager implements ILikeManager {
    HashMap<UUID, Like> likes = new HashMap<>();

    IReader reader;

    IWriter writer;

    public LikeManager(IReader reader, IWriter writer){
        this.reader = reader;
        this.writer = writer;
        likes = reader.read(likes.getClass());
    }

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



    @Override
    public boolean like(UUID postId, String user){
        if (canLike(postId, user)) {
            Like like = new Like(postId, user);
            likes.put(like.getId(), like);
            return true;
        }
        return false;
    }

    @Override
    public void unlike(UUID id){
        likes.remove(id);
    }

    @Override
    public boolean canLike(UUID postId, String user){
        for (Like like : likes.values()){
            if (like.getPostID().equals(postId) && like.getLiker().equals(user)){
                return false;
            }
        }
        return true;
    }

    @Override
    public Like getLike(UUID id){ return likes.get(id); }

    @Override
    public void save() { writer.write(likes); }

}
