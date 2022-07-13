package useCases;

import java.util.UUID;
import java.util.HashMap;
import java.util.ArrayList;


import entities.Post;
import gateway.IPostSorter;
import gateway.IReader;
import gateway.IWriter;

public class PostManager implements IPostManager{
    /**
     * a mapping of id of the post to the post entity
     */
    HashMap<UUID, Post> posts = new HashMap<>();
    /**
     * a gateway responsible for reading objects
     */
    IReader reader;
    /**
     * a gateway responsible for writing objects
     */
    IWriter writer;
    /**
     *  a sorter that sorts an arraylist of posts
     */
    IPostSorter postSorter;

    /**
     * Constructor of a use case responsible for managing posts.
     *
     * @param reader a gateway responsible for reading objects
     * @param writer a gateway responsible for writing objects
     */
    public PostManager(IReader reader, IWriter writer) {
        this.reader = reader;
        this.writer = writer;
        posts = reader.read(posts.getClass());
    }

    /**
     * @inheritDoc
     */
    @Override
    public ArrayList<Post> getPostsWrittenBy(String username) {
        ArrayList<Post> posts = new ArrayList<>();
        for (UUID id : this.posts.keySet()) {
            Post post = this.posts.get(id);
            if (post.getAuthor().equals(username)) {
                posts.add(post);
            }
        }
        this.postSorter.sort(posts);
        return posts;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deletePostsWrittenBy(String username) {
        for (Post post : getPostsWrittenBy(username)) {
            deletePost(post.getId());
        }
    }

    private Post createPost(String title, String content, String author) {
        return new Post(title, content, author);
    }

    /**
     * @inheritDoc
     */
    @Override
    public UUID addPost(String title, String content, String author) {
        Post post = createPost(title, content, author);
        posts.put(post.getId(), post);
        return post.getId();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deletePost(UUID id) {
        posts.remove(id);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Post getPost(UUID id) {
        return posts.get(id);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void save() {
        writer.write(posts);
    }


    /**
     * @inheritDoc
     */
    @Override
    public HashMap<UUID, Post> getMap(){
        return posts;
    }


    @Override
    public void setPostSorter(IPostSorter postSorter) {
        this.postSorter = postSorter;
    }

}
