package controllers.comment;

import controllers.appWide.RequestController;
import controllers.appWide.RequestFacade;
import controllers.appWide.ReturnController;
import dataMapper.DataMapper;
import gateway.CommentTimeSorter;
import useCases.CommentManager;

import java.util.UUID;

public class ViewCommentController extends RequestController {
    /**
     * a data mapper to store comments
     */
    DataMapper commentModel;

    /**
     * Constructor for a controller responsible for accessing comments.
     * Default commentSorter is CommentTimeSorter.
     *
     * @param commentManager use case responsible for managing comments
     */
    public ViewCommentController(DataMapper commentModel, CommentManager commentManager) {
        this.commentModel = commentModel;
        this.commentManager = commentManager;
    }

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
        commentModel.reset();
        commentModel.addItems(
                commentManager.getCommentsUnder(UUID.fromString(requester)),
                new String[]{ "content", "author", "timePosted"}
        );
        // commentPresenter.printComments(commentModel.getModel());

        RequestFacade viewCommentFacade = new RequestFacade(
                new RequestController[] {
                        new ReturnController()
                }
        );
        viewCommentFacade.setRequester(requester);
        viewCommentFacade.presentRequest();
        return false;

    }
}
