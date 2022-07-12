package controllers.account;

import controllers.appWide.RequestController;
import controllers.appWide.RequestFacade;
import controllers.appWide.ReturnController;
import controllers.post.AddPostController;
import controllers.post.ViewPostNoPermissionController;
import controllers.post.ViewPostPermissionController;
import dataMapper.DataMapper;
import entities.Post;
import gateway.IPostSorter;
import gateway.PostTimeSorter;
import presenters.PostPresenter;
import useCases.IAccountManager;
import useCases.ICommentManager;
import useCases.IPostManager;

import java.util.Scanner;

public class ViewProfileController extends RequestController {
    /**
     * a use case responsible for managing posts
     */
    IPostManager postManager;
    /**
     * a use case responsible for managing posts
     */
    ICommentManager commentManager;
    /**
     * a use case responsible for managing accounts
     */
    IAccountManager accountManager;
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
    IPostSorter postSorter = new PostTimeSorter();

    public ViewProfileController(IAccountManager accountManager,
                                 IPostManager postManager,
                                 ICommentManager commentManager) {
        this.accountManager = accountManager;
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
    protected String getRequestDescription() {
        return "View others profiles";
    }

    private void showProfile(String requester, String target) {
        postModel.reset();
        postModel.addItems(
                postSorter.sort(postManager.getPostsWrittenBy(target)),
                new String[]{ "title", "author", "content", "timePosted", "id"}
        );
        PostPresenter postPresenter = new PostPresenter();
        postPresenter.printPosts(postModel.getModel());
        RequestFacade profileFacade = new RequestFacade(
                new RequestController[] {
                        new ViewPostNoPermissionController(postModel, postManager, commentModel, commentManager),
                        new ReturnController()
                }
        );
        profileFacade.setRequester(requester);
        profileFacade.presentRequest();
    }

    @Override
    protected boolean handleRequest(String requester) {
        Scanner scanner = new Scanner(System.in);
        presenter.inlinePrint("Enter the username of the profile you wish to view or nothing to exit: ");
        String target = scanner.nextLine();
        sleeper.sleep(200);
        if (target.equals(requester)) {
            new ViewSelfProfileController(postManager, commentManager).handleRequest(requester);
        } else if (accountManager.containsUser(target)) {
            showProfile(requester, target);
        } else if (!target.equals("")) {
            presenter.blockPrint("This username does not exist");
            handleRequest(requester);
        }
        return false;
    }
}
