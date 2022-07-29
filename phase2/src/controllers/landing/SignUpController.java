package controllers.landing;

import java.util.Scanner;

import controllers.appWide.RequestController;
import controllers.appWide.RequestFacade;
import controllers.account.UnfollowController;
import controllers.account.ViewSelfProfileController;
import controllers.account.*;
import exception.InvalidUsernameException;
import exception.UsernameExistsException;
import useCases.CommentManager;
import useCases.PostManager;
import useCases.AccountManager;


public class SignUpController extends RequestController {
    /**
     * a request facade containing request controllers for regular accounts
     */
    private RequestFacade accountRequestFacade;

    /**
     * Constructor for a controller responsible for reading input to sign users up.
     *
     * @param accountManager a use case responsible for managing accounts
     * @param postManager    a use case responsible for managing posts
     * @param commentManager a use case responsible for managing comments
     */
    public SignUpController(AccountManager accountManager, PostManager postManager, CommentManager commentManager) {
        this.accountManager = accountManager;
        this.commentManager = commentManager;
        accountRequestFacade = new RequestFacade(new RequestController[]{
            new ViewHistoryController(accountManager),
            new DeleteSelfController(accountManager, postManager, commentManager),
            new FollowController(accountManager),
            new UnfollowController(accountManager),
            new ViewFollowerController(accountManager),
            new ViewFollowingController(accountManager),
            new ViewSelfProfileController(postManager, commentManager),
            new ViewFeedController(postManager, accountManager, commentManager),
            new ViewProfileController(accountManager, postManager, commentManager),
            new LogoutController(),
        });
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() {
        return "Sign up for an account";
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester) {
        Scanner scanner = new Scanner(System.in);
        presenter.inlinePrint("Enter your username: ");
        String username = scanner.nextLine();
        presenter.inlinePrint("Enter your password: ");
        String password = scanner.nextLine();
        try {
            accountManager.signUp(username, password);
            presenter.blockPrint("Successfully signed up.");
            sleeper.sleep(200);
            accountRequestFacade.setRequester(username);
            accountRequestFacade.presentRequest();
        } catch (UsernameExistsException | InvalidUsernameException e){
            presenter.blockPrint(e.getMessage());
        }
        presenter.blockPrint("");
        return false;
    }
}

