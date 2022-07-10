package controllers.account;

import controllers.appWide.RequestController;
import controllers.appWide.RequestFacade;
import controllers.appWide.ReturnController;
import controllers.post.AddPostController;
import controllers.post.ViewPostPermissionController;
import useCases.ICommentManager;
import useCases.IPostManager;
import dataMapper.DataMapper;

import gateway.IPostSorter;

import presenters.ProfilePresenter;

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
     *  a sorter that sorts an arraylist of posts
     */
    IPostSorter postSorter;

    /**
     * Constructor for a controller responsible for handling input related to viewing a user's own profile.
     *
     * @param postManager  a use case responsible for managing posts
     */
    public ViewSelfProfileController(IPostManager postManager, ICommentManager commentManager) {
        this.postManager = postManager;
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
                postSorter.sort(postManager.getPostsWrittenBy(requester)),
                new String[]{ "title", "author", "content", "timePosted", "id"}
        );
        ProfilePresenter profilePresenter = new ProfilePresenter();
        profilePresenter.present(postModel.getModel());
        RequestFacade profileFacade = new RequestFacade(
            new RequestController[] {
                    new AddPostController(postModel, postManager),
                    new ViewPostPermissionController(postModel, postManager, commentModel, commentManager),
                    new ReturnController()
            }
        );
        profileFacade.setRequester(requester);
        profileFacade.presentRequest();
        return false;
    }
}
