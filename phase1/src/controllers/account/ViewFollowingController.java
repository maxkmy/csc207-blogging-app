package controllers.account;

import controllers.appWide.RequestController;
import useCases.IAccountManager;

public class ViewFollowingController extends RequestController {
    /**
     * Constructor for a controller responsible for handling input related to viewing a list of followees.
     *
     * @param accountManager  a use case responsible for managing accounts
     */
    public ViewFollowingController(IAccountManager accountManager) {
        this.accountManager = accountManager;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() {
        return "View who you are following";
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester) {
        presenter.inlinePrint("Here are your followees: ");
        presenter.printMessages(accountManager.getFolloweesOf(requester), "\n");
        presenter.blockPrint("");
        return false;
    }
}
