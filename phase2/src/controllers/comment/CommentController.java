package controllers.comment;

import dataMapper.DataMapper;
import gateway.CommentTimeSorter;
import gateway.ICommentSorter;
import useCases.CommentManager;
import useCases.ManagerData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CommentController {
    private CommentManager commentManager;

    public CommentController(ManagerData managerData) {
        commentManager = managerData.getCommentManager();
    }

    public void addComment(UUID postId, String comment, String author) {
        commentManager.addComment(postId, comment, author);
    }

    public ArrayList<HashMap<String, String>> getCommentsUnder(UUID postId) {
        ICommentSorter commentSorter = new CommentTimeSorter();
        commentManager.setCommentSorter(commentSorter);
        DataMapper commentModel = new DataMapper();
        commentModel.addItems(
                commentManager.getCommentsUnder(postId),
                new String[]{ "content", "author", "timePosted" }
        );
        return commentModel.getModel();
    }
}