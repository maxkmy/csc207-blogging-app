package controllers.account;

import controllers.appWide.RequestController;
import controllers.appWide.RequestFacade;
import controllers.appWide.ReturnController;
import controllers.post.ViewPostNoPermissionController;
import dataMapper.DataMapper;
import entities.Post;
import gateway.IPostSorter;
import gateway.PostTimeSorter;
import useCases.IAccountManager;
import useCases.ICommentManager;
import useCases.IPostManager;

import java.util.ArrayList;
import java.util.HashSet;

public class ViewFeedController extends RequestController {
    /**
     * a use case responsible for managing comments
     */
    ICommentManager commentManager;
    /**
     * a data mapper responsible for mapping comments into a data structure usable by the presenters
     */
    DataMapper postModel = new DataMapper();
    /**
     *  a data mapper responsible for mapping comments into a data structure usable by the presenters
     */
    DataMapper commentModel = new DataMapper();
    /**
     *  a sorter that sorts an arraylist of posts, default is oldest to newest
     */
    IPostSorter postSorter = new PostTimeSorter();

    /**
     * Constructor for a controller responsible for retrieving the post feed
     *
     * @param postManager    use case responsible for managing posts
     * @param accountManager use case responsible for managing accounts
     */
    public ViewFeedController(IPostManager postManager, IAccountManager accountManager, ICommentManager commentManager) {
        this.postManager = postManager;
        this.accountManager = accountManager;
        this.commentManager = commentManager;
    }

    /**
     * Sets a sorter that sorts an arraylist of posts
     *
     * @param postSorter a sorter that sorts an arraylist of posts
     */
    public void setSorter(IPostSorter postSorter) {
        this.postSorter = postSorter;
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
        HashSet<String> followees = accountManager.getFolloweesOf(requester);
        ArrayList<Post> postsList = new ArrayList<>();
        for (String followee : followees) { postsList.addAll(postManager.getPostsWrittenBy(followee)); }

        postModel.reset();
        postModel.addItems(
                postSorter.sort(postsList),
                new String[]{ "title", "author", "content", "timePosted", "id"}
        );

//        PostPresenter postPresenter = new PostPresenter();
//        postPresenter.printPosts(postModel.getModel());
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
