import gateway.*;
import org.junit.Test;
import useCases.CommentManager;

import java.util.UUID;

import static org.junit.Assert.assertEquals;


public class CommentManagerTest {
    @Test
    public void testAddPost() {
        IReader reader = new Reader("test/testData/testPostData.txt");
        IWriter writer = new Writer("test/testData/testPostData.txt");
        ICommentSorter sorter = new CommentTimeSorter();
        CommentManager commentManager = new CommentManager(reader, writer, sorter);
        UUID randId = UUID.randomUUID();
        int commentsBeforeAddingComment = commentManager.getCommentsUnder(randId).size();
        commentManager.addComment(randId, "comment", "user");
        assertEquals(commentsBeforeAddingComment + 1, commentManager.getCommentsUnder(randId).size());
    }

    @Test
    public void testDeleteComment() {
        IReader reader = new Reader("test/testData/testPostData.txt");
        IWriter writer = new Writer("test/testData/testPostData.txt");
        ICommentSorter sorter = new CommentTimeSorter();
        CommentManager commentManager = new CommentManager(reader, writer, sorter);
        UUID randId = UUID.randomUUID();
        UUID commentId = commentManager.addComment(randId, "comment", "user");
        commentManager.deleteComment(commentId);
        assertEquals(null, commentManager.getComment(commentId));
    }

    @Test
    public void testDeleteCommentsWrittenBy() {
        IReader reader = new Reader("test/testData/testPostData.txt");
        IWriter writer = new Writer("test/testData/testPostData.txt");
        ICommentSorter sorter = new CommentTimeSorter();
        CommentManager commentManager = new CommentManager(reader, writer, sorter);
        UUID randId = UUID.randomUUID();
        commentManager.addComment(randId, "comment", "user");
        commentManager.addComment(randId, "comment 2.0", "uesr");
        commentManager.deleteCommentsWrittenBy("user");
        assertEquals(commentManager.getCommentsWrittenBy("user").size(), 0);
    }
}
