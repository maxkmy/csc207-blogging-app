package controllers.account;

import controllers.appWide.RequestController;
import controllers.appWide.RequestFacade;
import controllers.appWide.ReturnController;
import controllers.post.ViewPostNoPermissionController;
import dataMapper.DataMapper;
import entities.Post;
import gateway.IPostSorter;
import gateway.PostTimeSorter;
import useCases.AccountManager;
import useCases.CommentManager;
import useCases.PostManager;

import java.util.ArrayList;
import java.util.HashSet;

public class ViewFeedController extends RequestController {
    DataMapper postModel = new DataMapper();
    /**
     *  a data mapper responsible for mapping comments into a data structure usable by the presenters
     */
    DataMapper commentModel = new DataMapper();

    /**
     * Constructor for a controller responsible for retrieving the post feed
     *
     * @param postManager    use case responsible for managing posts
     * @param accountManager use case responsible for managing accounts
     */
    public ViewFeedController(PostManager postManager, AccountManager accountManager, CommentManager commentManager) {
        this.postManager = postManager;
        this.accountManager = accountManager;
        this.commentManager = commentManager;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() { return "View feed"; }

    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester) {
        IPostSorter postSorter = new PostTimeSorter();
        HashSet<String> followees = accountManager.getFolloweesOf(requester);
        ArrayList<Post> postsList = new ArrayList<>();
        for (String followee : followees) { postsList.addAll(postManager.getPostsWrittenBy(followee)); }

        postModel.reset();
        postModel.addItems(
                postSorter.sort(postsList),
                new String[]{ "title", "author", "content", "timePosted", "id"}
        );
        // postPresenter.printPosts(postModel.getModel());
        RequestFacade feedFacade = new RequestFacade(
                new RequestController[] {
                        new ViewPostNoPermissionController(postModel, postManager, commentModel, commentManager),
                        new ReturnController()
                }
        );
        feedFacade.setRequester(requester);
        feedFacade.presentRequest();
        return false;
    }
}
