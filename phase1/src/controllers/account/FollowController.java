package controllers.account;

import controllers.appWide.RequestController;
import exception.UserFollowedException;
import exception.UsernameNotFoundException;
import useCases.IAccountManager;

import java.util.Scanner;

public class FollowController extends RequestController {
    /**
     * a use case responsible for managing accounts
     */
    IAccountManager accountManager;

    /**
     * Constructor for a controller responsible for handling input related to following a user.
     *
     * @param accountManager  a use case responsible for managing accounts
     */
    public FollowController(IAccountManager accountManager) {
        this.accountManager = accountManager;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() {
        return "Follow an account";
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester) {
        try {
            Scanner scanner = new Scanner(System.in);
            presenter.inlinePrint("Enter the username of the account you wish to follow: ");
            String target = scanner.nextLine();
            sleeper.sleep(200);
            if (!requester.equals(target)) {
                accountManager.follow(requester, target);
                presenter.blockPrint("Successfully followed user: " + target);
            } else {
                presenter.blockPrint("Unsuccessful, you cannot follow yourself.");
            }
        } catch (UsernameNotFoundException | UserFollowedException e) {
            presenter.blockPrint(e.getMessage());
        }
        return false;
    }
}
