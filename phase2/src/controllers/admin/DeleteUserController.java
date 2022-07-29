package controllers.admin;

import controllers.appWide.RequestController;
import exception.UserIsAdminException;
import exception.UsernameNotFoundException;
import useCases.AccountManager;
import useCases.CommentManager;
import useCases.PostManager;

import java.util.Scanner;

public class DeleteUserController extends RequestController {
    /**
     * Constructor for a controller responsible for handling input related to deleting others' account.
     *
     * @param accountManager  a use case responsible for managing accounts
     * @param postManager     a use case responsible for managing posts
     */
    public DeleteUserController(AccountManager accountManager,
                                PostManager postManager,
                                CommentManager commentManager) {
        this.accountManager = accountManager;
        this.postManager = postManager;
        this.commentManager = commentManager;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() {
        return "Delete an account";
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester) {
        try {
            Scanner scanner = new Scanner(System.in);
            presenter.inlinePrint("Enter the username of the account you wish to delete: ");
            String target = scanner.nextLine();
            accountManager.deleteUser(target);
            postManager.deletePostsWrittenBy(target);
            commentManager.deleteCommentsWrittenBy(target);
            presenter.blockPrint("Successfully deleted user: " + target);
        } catch (UsernameNotFoundException | UserIsAdminException e) {
           presenter.blockPrint(e.getMessage());
        }
        return false;
    }
}
