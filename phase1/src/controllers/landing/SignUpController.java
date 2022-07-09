package controllers.landing;

import java.util.Scanner;

import controllers.appWide.RequestController;
import controllers.appWide.RequestFacade;
import controllers.account.UnfollowController;
import controllers.account.ViewSelfProfileController;
import controllers.account.*;
import exception.UsernameExistsException;
import useCases.IPostManager;
import useCases.IAccountManager;


public class SignUpController extends RequestController {
    /**
     * a use case responsible for managing accounts
     */
    private IAccountManager accountManager;
    /**
     * a request facade containing request controllers for regular accounts
     */
    private RequestFacade accountRequestFacade;

    /**
     * Constructor for a controller responsible for reading input to sign users up.
     *
     * @param accountManager a use case responsible for managing accounts
     * @param postManager    a use case responsible for managing posts
     */
    public SignUpController(IAccountManager accountManager, IPostManager postManager) {
        this.accountManager = accountManager;
        accountRequestFacade = new RequestFacade(new RequestController[]{
            new ViewHistoryController(accountManager),
            new DeleteSelfController(accountManager, postManager),
            new FollowController(accountManager),
            new UnfollowController(accountManager),
            new ViewFollowerController(accountManager),
            new ViewFollowingController(accountManager),
            new ViewSelfProfileController(postManager),
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
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        sleeper.sleep(200);
        try {
            accountManager.signUp(username, password);
            System.out.println("Successfully signed up.");
            sleeper.sleep(200);
            accountRequestFacade.setRequester(username);
            accountRequestFacade.presentRequest();
        } catch (UsernameExistsException e){
            System.out.println(e.getMessage());
        }
        System.out.println();
        return false;
    }
}

