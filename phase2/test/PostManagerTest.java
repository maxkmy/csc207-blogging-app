import gateway.*;
import org.junit.Test;
import useCases.PostManager;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class PostManagerTest {
    @Test
    public void testAddPost() {
        IReader reader = new Reader("test/testData/testPostData.txt");
        IWriter writer = new Writer("test/testData/testPostData.txt");
        IPostSorter sorter = new PostTimeSorter();
        PostManager postManager = new PostManager(reader, writer, sorter);
        int originalPostsByUser = postManager.getPostsWrittenBy("user").size();
        postManager.addPost("hi", "there", "user");
        assertEquals(originalPostsByUser + 1, postManager.getPostsWrittenBy("user").size());
    }

    @Test
    public void testDeletePostsWrittenBy() {
        IReader reader = new Reader("test/testData/testPostData.txt");
        IWriter writer = new Writer("test/testData/testPostData.txt");
        IPostSorter sorter = new PostTimeSorter();
        PostManager postManager = new PostManager(reader, writer, sorter);
        postManager.addPost("hi", "there", "user");
        postManager.addPost("hi", "there 2.0", "user");
        postManager.deletePostsWrittenBy("user");
        assertEquals(0, postManager.getPostsWrittenBy("user").size());
    }

    @Test
    public void testDeletePost() {
        IReader reader = new Reader("test/testData/testPostData.txt");
        IWriter writer = new Writer("test/testData/testPostData.txt");
        IPostSorter sorter = new PostTimeSorter();
        PostManager postManager = new PostManager(reader, writer, sorter);
        UUID postId = postManager.addPost("hi", "there", "user");
        postManager.deletePost(postId);
        assertEquals(0, postManager.getPostsWrittenBy("user").size());
    }
}
