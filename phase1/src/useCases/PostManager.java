package useCases;

import java.util.UUID;
import java.util.HashMap;
import java.util.ArrayList;
import entities.Post;
import gateway.SimilarityScore;

public class PostManager implements IPostManager, ISearchAlgorithm{
    HashMap<UUID, Post> posts;
    public PostManager(HashMap<UUID, Post> posts) {
        this.posts = posts;
    }

    @Override
    public ArrayList<Post> getPostsWrittenBy(String username) {
        ArrayList<Post> posts = new ArrayList<>();
        for (UUID id : this.posts.keySet()) {
            Post post = this.posts.get(id);
            if (post.getAuthor().equals(username)) {
                posts.add(post);
            }
        }
        return posts;
    }

    @Override
    public void deletePostsWrittenBy(String username) {
        for (Post post : getPostsWrittenBy(username)) {
            deletePost(post.getId());
        }
    }

    private Post createPost(String title, String content, String author) {
        return new Post(title, content, author);
    }

    @Override
    public UUID addPost(String title, String content, String author) {
        Post post = createPost(title, content, author);
        posts.put(post.getId(), post);
        return post.getId();
    }

    @Override
    public void deletePost(UUID id) {
        posts.remove(id);
    }

    @Override
    public Post getPost(UUID id) {
        return posts.get(id);
    }

    @Override
    public HashMap<UUID, Post> getMap() {
        return posts;
    }

    @Override
    public HashMap<String, Double> doSearch(String stri, String Query) {
        HashMap<String, Double> map = new HashMap<>();
        if (Query.equals("Title")) {
            for (UUID id : this.posts.keySet()) {
                Post post = this.posts.get(id);
                SimilarityScore curr = new SimilarityScore();
                map.put(id.toString(), curr.getSimilarityScore(post.getTitle(),stri));
            }

        } else if (Query.equals("Content")) {
            for (UUID id : this.posts.keySet()) {
                Post post = this.posts.get(id);
                SimilarityScore curr = new SimilarityScore();
                map.put(id.toString(), curr.getSimilarityScore(post.getContent(),stri));
            }
        }
        return map;
    }
}
