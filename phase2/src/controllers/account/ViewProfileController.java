package controllers.account;

import controllers.appWide.RequestController;
import controllers.appWide.RequestFacade;
import controllers.appWide.ReturnController;
import controllers.post.ViewPostNoPermissionController;
import dataMapper.DataMapper;
import gateway.PostTimeSorter;
import useCases.AccountManager;
import useCases.CommentManager;
import useCases.PostManager;

import java.util.Scanner;

public class ViewProfileController extends RequestController {
    /**
     * a data mapper responsible for mapping posts into a data structure usable by the presenters
     */
    DataMapper postModel = new DataMapper();
    /**
     *  a data mapper responsible for mapping comments into a data structure usable by the presenters
     */
    DataMapper commentModel = new DataMapper();

    public ViewProfileController(AccountManager accountManager,
                                 PostManager postManager,
                                 CommentManager commentManager) {
        this.accountManager = accountManager;
        this.postManager = postManager;
        this.commentManager = commentManager;
    }


    /**
     * @inheritDoc
     */
    @Override
    protected String getRequestDescription() {
        return "View others profiles";
    }

    /**
     * Displays the profile of target for requester
     * Gives request options that do not require permissions
     *
     * @param requester the current user logged in
     * @param target    the target profile to open
     */
    private void showProfile(String requester, String target) {
        postModel.reset();
        postModel.addItems(
                postManager.getPostsWrittenBy(target),
                new String[]{ "title", "author", "content", "timePosted", "id"}
        );
        // postPresenter.printPosts(postModel.getModel());
        RequestFacade profileFacade = new RequestFacade(
                new RequestController[] {
                        new ViewPostNoPermissionController(postModel, postManager, commentModel, commentManager),
                        new ReturnController()
                }
        );
        profileFacade.setRequester(requester);
        profileFacade.presentRequest();
    }

    /**
     * @inheritDoc
     */
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
