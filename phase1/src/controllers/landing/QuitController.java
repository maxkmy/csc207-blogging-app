package controllers.landing;

import controllers.appWide.RequestController;
import useCases.IAccountManager;
import useCases.IPostManager;

import gateway.IWriter;
import gateway.Writer;

public class QuitController extends RequestController {
    /**
     * a string representing the directory where user data is stored
     */
    final String userDataFileDirectory = "data/userData.txt";
    /**
     * a string representing the directory where post data is stored
     */
    final String postDataFileDirectory = "data/postData.txt";
    /**
     * a use case responsible for managing accounts
     */
    IAccountManager accountManager;

    /**
     * Constructor for a controller responsible for reading input to log users out.
     *
     * @param accountManager a use case responsible for managing accounts
     * @param postManager    a use case responsible for managing posts
     */
    public QuitController(IAccountManager accountManager, IPostManager postManager) {
        this.accountManager = accountManager;
        this.postManager = postManager;
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
        IWriter writer1 = new Writer(userDataFileDirectory);
        writer1.write(accountManager.getMap());
        IWriter writer2 = new Writer(postDataFileDirectory);
        writer2.write(postManager.getMap());
        return true;
    }
}