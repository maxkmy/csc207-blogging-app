package controllers.post;

import dataMapper.DataMapper;
import entities.Post;
import gateway.IPostSorter;
import gateway.PostTimeSorter;
import useCases.AccountManager;
import useCases.ManagerData;
import useCases.PostManager;

import java.util.*;

public class PostController {
    private AccountManager accountManager;
    private PostManager postManager;
    private ManagerData managerData;
    DataMapper postModel = new DataMapper();

    public PostController(ManagerData managerData) {
        this.managerData = managerData;
        accountManager = managerData.getAccountManager();
        postManager = managerData.getPostManager();
    }

    public void addPost(String title, String content, String author) {
        postManager.addPost(title, content, author);
    }

    public ArrayList<HashMap<String, String>> getPostsWrittenBy(String author) {
        IPostSorter postSorter = new PostTimeSorter();
        postManager.setPostSorter(postSorter);
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

    public ArrayList<HashMap<String, String>> getFollowingPosts(String requester) {
        IPostSorter postSorter = new PostTimeSorter();
        postManager.setPostSorter(postSorter);
        HashSet<String> followees = accountManager.getFolloweesOf(requester);
        ArrayList<Post> postsList = new ArrayList<>();
        for (String followee : followees) { postsList.addAll(postManager.getPostsWrittenBy(followee)); }

        postModel.reset();
        postModel.addItems(
                postSorter.sort(postsList),
                new String[]{ "title", "author", "content", "timePosted", "id"}
        );
        return postModel.getModel();
    }
}