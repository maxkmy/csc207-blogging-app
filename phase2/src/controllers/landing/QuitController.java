package controllers.landing;

import controllers.appWide.RequestController;
import useCases.AccountManager;
import useCases.CommentManager;
import useCases.PostManager;

public class QuitController extends RequestController {
    /**
     * Constructor for a controller responsible for reading input to log users out.
     *
     * @param accountManager a use case responsible for managing accounts
     * @param postManager    a use case responsible for managing posts
     */
    public QuitController(AccountManager accountManager, PostManager postManager, CommentManager commentManager) {
        this.accountManager = accountManager;
        this.postManager = postManager;
        this.commentManager = commentManager;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() {
        return "Quit the app";
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester) {
        accountManager.save();
        postManager.save();
        commentManager.save();
        return true;
    }
}