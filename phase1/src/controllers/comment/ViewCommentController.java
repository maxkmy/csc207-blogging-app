package controllers.comment;

import controllers.appWide.RequestController;
import entities.Comment;
import gateway.CommentTimeSorter;
import useCases.ICommentManager;

import java.util.ArrayList;
import java.util.UUID;

public class ViewCommentController extends RequestController {
    /**
     * a use case responsible for managing comments
     */
    ICommentManager commentManager;

    /**
     * Constructor for a controller responsible for accessing comments
     *
     * @param commentManager    use case responsible for managing comments
     */
    public ViewCommentController(ICommentManager commentManager) { this.commentManager = commentManager; }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() { return "View comments"; }

    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester) {
        this.commentManager.setCommentSorter(new CommentTimeSorter());
        ArrayList<Comment> comments = this.commentManager.getCommentsUnder(UUID.fromString(requester));
        for (Comment comment : comments) {
            System.out.println("Comment: " + comment.getContent());
            System.out.println("Written by: " + comment.getAuthor() + "\n\n");
        }
        return false;
    }
}
