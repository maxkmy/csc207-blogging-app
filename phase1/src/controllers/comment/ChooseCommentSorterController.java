package controllers.comment;

import controllers.appWide.RequestController;
import dataMapper.DataMapper;
import gateway.CommentNewestToOldestSorter;
import gateway.CommentOldestToNewestSorter;
import gateway.ICommentSorter;
import useCases.ICommentManager;

public class ChooseCommentSorterController extends RequestController {
    /**
     *  a use case responsible for managing comments
     */
    ICommentManager commentManager;
    /**
     * a data mapper to store comments
     */
    DataMapper commentModel;
    /**
     *  An array list of all types of comment sorters
     */
    ICommentSorter[] allCommentSorters;

    /**
     *  Constructor for a controller responsible for changing comment sorting method.
     *  @param commentManager    use case responsible for managing comments
     */
    public ChooseCommentSorterController(DataMapper commentModel, ICommentManager commentManager) {
        this.commentModel = commentModel;
        this.commentManager = commentManager;

        // TODO: is there any other clean architecture/design pattern for a list including all types of comment sorters?
        // Right now, we have to manually add/delete comment sorters
        allCommentSorters = new ICommentSorter[]{
                new CommentNewestToOldestSorter(),
                new CommentOldestToNewestSorter()
        };
    }

    @Override
    public String getRequestDescription() {
        return "Change comment sorting method";
    }

    @Override
    public boolean handleRequest(String requester) {
        CommentSorterFacade CommentSorterFacade = new CommentSorterFacade(commentModel, commentManager, allCommentSorters);
        CommentSorterFacade.setRequester(requester);
        CommentSorterFacade.presentRequest();
        return false;
    }
}
