package controllers.account;

import controllers.appWide.RequestController;
import useCases.IAccountManager;
import useCases.IPostManager;

public class DeleteSelfController extends RequestController {
    /**
     * Constructor for a controller responsible for handling input related to account deletion.
     *
     * @param accountManager  a use case responsible for managing accounts
     * @param postManager     a use case responsible for managing posts
     */
    public DeleteSelfController(IAccountManager accountManager, IPostManager postManager) {
        this.accountManager = accountManager;
        this.postManager = postManager;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() {
        return "Delete your account";
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester) {
        accountManager.deleteSelf(requester);
        postManager.deletePostsWrittenBy(requester);
        presenter.inlinePrint("Successfully deleted user " + requester);
        return true;
    }
}
