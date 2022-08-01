package controllers.post;

import dataMapper.DataMapper;
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
}