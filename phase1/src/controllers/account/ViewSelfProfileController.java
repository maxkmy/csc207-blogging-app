package controllers.account;

import controllers.appWide.RequestController;
import controllers.appWide.RequestFacade;
import controllers.appWide.ReturnController;
import controllers.post.AddPostController;
import controllers.post.ViewPostPermissionController;
import gateway.PostTimeSorter;
import presenters.PostPresenter;
import useCases.ICommentManager;
import useCases.ILikeManager;
import useCases.IPostManager;
import dataMapper.DataMapper;

import gateway.IPostSorter;

public class ViewSelfProfileController extends RequestController {
    /**
     * a use case responsible for managing posts
     */
    IPostManager postManager;
    /**
     * a use case responsible for managing posts
     */
    ICommentManager commentManager;
    /**
     * a data mapper responsible for mapping posts into a data structure usable by the presenters
     */
    DataMapper postModel = new DataMapper();
    /**
     *  a data mapper responsible for mapping comments into a data structure usable by the presenters
     */
    DataMapper commentModel = new DataMapper();
    /**
     * a dataMapper to Store likes
     */
    DataMapper likeModel = new DataMapper();
    /**
     * a use case responsible for managing likes
     */
    ILikeManager likeManager;

    /**
     * Constructor for a controller responsible for handling input related to viewing a user's own profile.
     *
     * @param postManager  a use case responsible for managing posts
     */
    public ViewSelfProfileController(IPostManager postManager, ICommentManager commentManager, ILikeManager likeManager) {
        this.postManager = postManager;
        this.commentManager = commentManager;
        this.likeManager = likeManager;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() {
        return "View your profile";
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester) {
        postManager.setPostSorter(new PostTimeSorter());
        postModel.reset();
        postModel.addItems(
                postManager.getPostsWrittenBy(requester),
                new String[]{ "title", "author", "content", "timePosted", "id"}
        );
        PostPresenter postPresenter = new PostPresenter();
        postPresenter.printPosts(postModel.getModel());
        RequestFacade profileFacade = new RequestFacade(
            new RequestController[] {
                    new AddPostController(postModel, postManager),
                    new ViewPostPermissionController(postModel, postManager, commentModel, commentManager,likeManager, likeModel),
                    new ReturnController()
            }
        );
        profileFacade.setRequester(requester);
        profileFacade.presentRequest();
        return false;
    }
}
