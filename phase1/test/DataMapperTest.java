import entities.Account;
import entities.Comment;
import entities.Post;
import org.junit.Test;
import dataMapper.DataMapper;


import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

public class DataMapperTest {
    @Test
    public void testAddItem(){
        UUID postId = UUID.randomUUID();
        Comment comment = new Comment(postId, "testContent", "testAuthor");
        DataMapper dataMapper = new DataMapper();
        dataMapper.addItem(comment, new String[]{"postId", "content", "author"});
        assertEquals(1, dataMapper.getModel().size());
        assertEquals(postId.toString() ,dataMapper.getModel().get(0).get("postId"));
        assertEquals("testContent", dataMapper.getModel().get(0).get("content"));
        assertEquals("testAuthor", dataMapper.getModel().get(0).get("author"));
    }

    @Test
    public void testAddItems() {
        Account account = new Account("testUsername", "testPassword");
        Account account2 = new Account("testUsername2", "testPassword2");

        ArrayList<Account> accounts = new ArrayList<Account>();
        accounts.add(account);
        accounts.add(account2);

        DataMapper dataMapper = new DataMapper();
        dataMapper.addItems(accounts, new String[]{"username", "hashedPassword"});

        assertEquals(2, dataMapper.getModel().size());

        assertEquals("testUsername", dataMapper.getModel().get(0).get("username"));
        assertEquals("testPassword", dataMapper.getModel().get(0).get("hashedPassword"));

        assertEquals("testUsername2", dataMapper.getModel().get(1).get("username"));
        assertEquals("testPassword2", dataMapper.getModel().get(1).get("hashedPassword"));
    }

    @Test
    public void testReset() {
        Post post = new Post("testTitle", "testContent", "testAuthor");
        Post post2 = new Post("testTitle2", "testContent2", "testAuthor2");

        ArrayList<Post> posts = new ArrayList<Post>();
        posts.add(post);
        posts.add(post2);

        DataMapper dataMapper = new DataMapper();
        dataMapper.addItems(posts, new String[]{"title", "content", "author"});

        assertEquals(2, dataMapper.getModel().size());

        assertEquals("testTitle",dataMapper.getModel().get(0).get("title"));
        assertEquals("testContent",dataMapper.getModel().get(0).get("content"));
        assertEquals("testAuthor",dataMapper.getModel().get(0).get("author"));

        assertEquals("testTitle2",dataMapper.getModel().get(1).get("title"));
        assertEquals("testContent2",dataMapper.getModel().get(1).get("content"));
        assertEquals("testAuthor2",dataMapper.getModel().get(1).get("author"));

        dataMapper.reset();

        assertEquals(0, dataMapper.getModel().size());
    }

    @Test
    public void testDeleteItem() {
        Post post = new Post("testTitle", "testContent", "testAuthor");
        Post post2 = new Post("testTitle2", "testContent2", "testAuthor2");

        ArrayList<Post> posts = new ArrayList<Post>();
        posts.add(post);
        posts.add(post2);

        DataMapper dataMapper = new DataMapper();
        dataMapper.addItems(posts, new String[]{"title", "content", "author"});

        assertEquals(2, dataMapper.getModel().size());

        assertEquals("testTitle",dataMapper.getModel().get(0).get("title"));
        assertEquals("testContent",dataMapper.getModel().get(0).get("content"));
        assertEquals("testAuthor",dataMapper.getModel().get(0).get("author"));

        assertEquals("testTitle2",dataMapper.getModel().get(1).get("title"));
        assertEquals("testContent2",dataMapper.getModel().get(1).get("content"));
        assertEquals("testAuthor2",dataMapper.getModel().get(1).get("author"));

        dataMapper.deleteItem("author", "testAuthor2");

        assertEquals(1, dataMapper.getModel().size());

        assertEquals("testTitle",dataMapper.getModel().get(0).get("title"));
        assertEquals("testContent",dataMapper.getModel().get(0).get("content"));
        assertEquals("testAuthor",dataMapper.getModel().get(0).get("author"));

    }
}
