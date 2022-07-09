package controllers.account;

import controllers.appWide.RequestController;
import exception.UserNotFollowedException;
import exception.UsernameNotFoundException;
import useCases.IAccountManager;

import java.util.Scanner;

public class UnfollowController extends RequestController {
    /**
     * a use case responsible for managing accounts
     */
    IAccountManager accountManager;

    /**
     * Constructor for a controller responsible for handling input related to user unfollowing.
     *
     * @param accountManager  a use case responsible for managing accounts
     */
    public UnfollowController(IAccountManager accountManager) {
        this.accountManager = accountManager;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() {
        return "Unfollow an account";
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the username of the account you wish to unfollow: ");
            String target = scanner.nextLine();
            sleeper.sleep(200);
            accountManager.unfollow(requester, target);
            System.out.println("Successfully unfollowed user: " + target);
        } catch (UsernameNotFoundException | UserNotFollowedException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
