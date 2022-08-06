package controllers;

import dataMapper.DataMapper;
import useCases.CommentManager;
import useCases.ManagerData;

import java.util.*;

public class CommentController {
    /**
     * a use case responsible for managing comments
     */
    private CommentManager commentManager;

    /**
     * Constructor of a controller for comments
     *
     * @param managerData an object that groups use cases together
     */
    public CommentController(ManagerData managerData) {
        commentManager = managerData.getCommentManager();
    }

    public void addComment(UUID postId, String comment, String author) {
        commentManager.addComment(postId, comment, author);
    }

    public List<Map<String, String>> getCommentsUnder(UUID postId) {
        DataMapper commentModel = new DataMapper();
        commentModel.addItems(
                commentManager.getCommentsUnder(postId),
                new String[]{ "content", "author", "timePosted" }
        );
        return commentModel.getModel();
    }
}