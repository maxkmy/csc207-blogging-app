package controllers.account;

import controllers.appWide.RequestController;
import useCases.AccountManager;

public class ViewFollowerController extends RequestController {
    /**
     * Constructor for a controller responsible for handling input related to viewing a list of followers.
     *
     * @param accountManager  a use case responsible for managing accounts
     */
    public ViewFollowerController(AccountManager accountManager) {
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
        presenter.inlinePrint("Here are your followers: ");
        presenter.printMessages(accountManager.getFollowersOf(requester), "\n");
        presenter.blockPrint("");
        return false;
    }
}
