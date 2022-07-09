package controllers.account;

import controllers.appWide.RequestController;
import useCases.IAccountManager;

public class ViewFollowerController extends RequestController {
    /**
     * Constructor for a controller responsible for handling input related to viewing a list of followers.
     *
     * @param accountManager  a use case responsible for managing accounts
     */
    public ViewFollowerController(IAccountManager accountManager) {
        this.accountManager = accountManager;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() {
        return "View your followers";
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester) {
        StringBuilder outBuilder = new StringBuilder();
        for (String username : accountManager.getFollowersOf(requester)) {
            outBuilder.append(username);
            outBuilder.append("\n");
        }
        System.out.println("Here are your followers: ");
        System.out.println(outBuilder);
        return false;
    }
}
