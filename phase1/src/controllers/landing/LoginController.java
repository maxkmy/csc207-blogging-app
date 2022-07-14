package controllers.landing;

import controllers.account.*;
import controllers.admin.*;
import controllers.appWide.RequestController;
import controllers.appWide.RequestFacade;
import controllers.search.SearchPostByTitleController;
import controllers.search.SearchUserByUsernameController;
import exception.IncorrectPasswordException;
import exception.UsernameNotFoundException;
import exception.AccountBannedException;
import gateway.ISearch;
import gateway.SearchByUsernameAdmin;
import gateway.SearchByUsernameRegular;
import useCases.IAccountManager;
import useCases.ICommentManager;
import useCases.ILikeManager;
import useCases.IPostManager;

import java.util.Scanner;

public class LoginController extends RequestController {
    /**
     * a request facade containing request controllers for admins
     */
    final private RequestFacade adminRequestFacade;
    /**
     * a request facade containing request controllers for regular accounts
     */
    final private RequestFacade accountRequestFacade;

    /**
     * Constructor for a controller responsible for reading input to log users in.
     *
     * @param accountManager a use case responsible for managing accounts
     * @param postManager    a use case responsible for managing posts
     * @param commentManager a user case responsible for managing comments
     * @param likeManager    a use case responsible for managing likes
     */
    public LoginController(IAccountManager accountManager, IPostManager postManager, ICommentManager commentManager, ILikeManager likeManager){
        this.accountManager = accountManager;
        ISearch searchForRegularUsers = new SearchByUsernameRegular(accountManager);
        ISearch searchForAdmin = new SearchByUsernameAdmin(accountManager);
        accountRequestFacade = new RequestFacade(new RequestController[]{
                new ViewHistoryController(accountManager),
                new DeleteSelfController(accountManager, postManager),
                new FollowController(accountManager),
                new UnfollowController(accountManager),
                new ViewFollowerController(accountManager),
                new ViewFollowingController(accountManager),
                new ViewSelfProfileController(postManager, commentManager, likeManager),
                new ViewFeedController(postManager, accountManager, commentManager, likeManager),
                new ViewProfileController(accountManager, postManager, commentManager, likeManager),
                new SearchPostByTitleController(postManager),
                new SearchUserByUsernameController(accountManager,searchForRegularUsers),
                new LogoutController(),
        });
        adminRequestFacade = new RequestFacade(new RequestController[]{
                new BanUserController(accountManager),
                new UnbanUserController(accountManager),
                new PromoteUserController(accountManager),
                new CreateAdminController(accountManager),
                new DeleteUserController(accountManager, postManager),
                new DeleteSelfController(accountManager, postManager),
                new ViewHistoryController(accountManager),
                new FollowController(accountManager),
                new UnfollowController(accountManager),
                new ViewFollowerController(accountManager),
                new ViewFollowingController(accountManager),
                new ViewSelfProfileController(postManager, commentManager, likeManager),
                new ViewFeedController(postManager, accountManager, commentManager, likeManager),
                new ViewProfileController(accountManager, postManager, commentManager, likeManager),
                new SearchPostByTitleController(postManager),
                new SearchUserByUsernameController(accountManager, searchForAdmin),
                new LogoutController(),
        });
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() {
        return "Log in to your account";
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
        sleeper.sleep(200);
        try {
            accountManager.login(username, password);
            presenter.blockPrint("Successfully logged in.");
            if (accountManager.isAdmin(username)) {
                adminRequestFacade.setRequester(username);
                adminRequestFacade.presentRequest();
                adminRequestFacade.setRequester(null);
            } else {
                accountRequestFacade.setRequester(username);
                accountRequestFacade.presentRequest();
                accountRequestFacade.setRequester(null);
            }
        } catch (IncorrectPasswordException | UsernameNotFoundException | AccountBannedException e) {
            presenter.blockPrint(e.getMessage());
        }
        return false;
    }
}
