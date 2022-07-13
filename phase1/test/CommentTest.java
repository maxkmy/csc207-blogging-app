import entities.Comment;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.*;

public class CommentTest {
    @Test
    public void testGetContent(){
        UUID postId = UUID.randomUUID();
        Comment comment = new Comment(postId, "Test content", "Test author");
        assertEquals("Test content", comment.getContent());
    }

    @Test
    public void testGetAuthor() {
        UUID postId = UUID.randomUUID();
        Comment comment = new Comment(postId, "Test content", "Test author");
        assertEquals("Test author", comment.getAuthor());
    }

    @Test
    public void testGetTimePostedIsAfter() {
        LocalDateTime beforeTime = LocalDateTime.now();
        UUID postId = UUID.randomUUID();
        Comment comment = new Comment(postId, "Test content", "Test author");
        assertTrue(comment.getTimePosted().isAfter(beforeTime));
    }

    @Test
    public void testGetTimePostedIsBefore() {
        UUID postId = UUID.randomUUID();
        Comment comment = new Comment(postId, "Test content", "Test author");
        LocalDateTime afterTime = LocalDateTime.now();
        assertTrue(comment.getTimePosted().isBefore(afterTime));
    }

    @Test
    public void testGetPostId() {
        UUID postId = UUID.randomUUID();
        Comment comment = new Comment(postId, "Test content", "Test author");
        assertEquals(postId, comment.getPostId());
    }
}