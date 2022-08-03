package controllers.post;

import dataMapper.DataMapper;
import entities.Post;
import useCases.AccountManager;
import useCases.ManagerData;
import useCases.PostManager;

import java.util.*;

public class PostController {
    private AccountManager accountManager;
    private PostManager postManager;
    DataMapper postModel = new DataMapper();

    public PostController(ManagerData managerData) {
        accountManager = managerData.getAccountManager();
        postManager = managerData.getPostManager();
    }

    public void addPost(String title, String content, String author) {
        postManager.addPost(title, content, author);
    }

    public List<Map<String, String>> getPostsWrittenBy(String author) {
        DataMapper postModel = new DataMapper();
        postModel.addItems(
                postManager.getPostsWrittenBy(author),
                new String[]{ "title", "author", "content", "timePosted", "id" }
        );
        return postModel.getModel();
    }

    public void deletePost(UUID postId) {
        postManager.deletePost(postId);
    }

    public Map<String, String> getPost(UUID postId) {
        DataMapper postModel = new DataMapper();
        return postModel.getItemMap(
                postManager.getPost(postId),
                new String[]{ "title", "author", "content", "timePosted", "id" }
        );
    }

    public List<Map<String, String>> getFollowingPosts(String requester) {
        HashSet<String> followees = accountManager.getFolloweesOf(requester);
        ArrayList<Post> postsList = new ArrayList<>();
        for (String followee : followees) { postsList.addAll(postManager.getPostsWrittenBy(followee)); }
        postModel.reset();
        postModel.addItems(
                postsList,
                new String[]{ "title", "author", "content", "timePosted", "id"}
        );
        return postModel.getModel();
    }
}