package controllers.account;

import controllers.appWide.RequestController;
import controllers.appWide.RequestFacade;
import controllers.appWide.ReturnController;
import controllers.post.AddPostController;
import controllers.post.ViewPostPermissionController;
import useCases.IPostManager;
import dataMapper.DataMapper;

import presenters.ProfilePresenter;

public class ViewSelfProfileController extends RequestController {
    /**
     * a use case responsible for managing posts
     */
    IPostManager postManager;
    /**
     * a data mapper responsible for mapping posts into a data structure usable by the presenters
     */
    DataMapper postModel = new DataMapper();

    /**
     * Constructor for a controller responsible for handling input related to viewing a user's own profile.
     *
     * @param postManager  a use case responsible for managing posts
     */
    public ViewSelfProfileController(IPostManager postManager) {
        this.postManager = postManager;
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
        postModel.reset();
        postModel.addItems(
            postManager.getPostsWrittenBy(requester),
            new String[]{ "title", "author", "content", "timePosted", "id"}
        );
        postModel.sortBy("timePosted");
        ProfilePresenter profilePresenter = new ProfilePresenter();
        profilePresenter.present(postModel.getModel());
        RequestFacade profileFacade = new RequestFacade(
            new RequestController[] {
                    new AddPostController(postModel, postManager),
                    new ViewPostPermissionController(postModel, postManager),
                    new ReturnController()
            }
        );
        profileFacade.setRequester(requester);
        profileFacade.presentRequest();
        return false;
    }
}
