package controllers.comment;

import controllers.appWide.RequestController;
import controllers.appWide.RequestFacade;
import controllers.appWide.ReturnController;
import dataMapper.DataMapper;
import gateway.ICommentSorter;
import presenters.CommentPresenter;
import useCases.ICommentManager;

import java.util.UUID;

public class ViewCommentController extends RequestController {
    /**
     * a use case responsible for managing comments
     */
    ICommentManager commentManager;
    /**
     * a data mapper to store comments
     */
    DataMapper commentModel;
    /**
     *  a sorter that sorts an arraylist of posts
     */
    ICommentSorter commentSorter;

    /**
     * Constructor for a controller responsible for accessing comments.
     * Default commentSorter is CommentNewestToOldestSorter.
     *
     * @param commentManager    use case responsible for managing comments
     */
    public ViewCommentController(DataMapper commentModel, ICommentManager commentManager) {
        this.commentModel = commentModel;
        this.commentManager = commentManager;
        this.commentSorter = commentManager.getCommentSorter();
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
                commentSorter.sort(commentManager.getCommentsUnder(UUID.fromString(requester))),
                new String[]{ "content", "author", "timePosted"}
        );

        CommentPresenter commentPresenter = new CommentPresenter();
        commentPresenter.printComments(commentModel.getModel());
        presenter.blockPrint("Current sorting method: " + commentSorter.printType());

        RequestFacade viewCommentFacade = new RequestFacade(
                new RequestController[] {
                        new ChooseCommentSorterController(commentModel, commentManager),
                        new ReturnController()
                }
        );
        viewCommentFacade.setRequester(requester);
        viewCommentFacade.presentRequest();
        return false;

    }
}
