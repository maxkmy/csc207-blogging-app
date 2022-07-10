package gateway;

import entities.Post;

import java.util.ArrayList;

public interface IPostSorter {
    /**
     * returns a sorted array list of a posts.
     *
     * @param posts an arraylist of posts
     * @return      a sorted arraylist of posts.
     */
    ArrayList<Post> sort(ArrayList<Post> posts);
}
