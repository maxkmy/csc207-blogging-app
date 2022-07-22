package controllers.admin;

import controllers.appWide.RequestController;
import exception.*;
import useCases.IAccountManager;

import java.util.Scanner;

public class PromoteUserController extends RequestController {
    /**
     * Constructor for a controller responsible for handling input related to promoting a user to admin.
     *
     * @param accountManager  a use case responsible for managing accounts
     */
    public PromoteUserController(IAccountManager accountManager) {
        this.accountManager = accountManager;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() {
        return "promote user to admin";
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester) {
        try {
            Scanner scanner = new Scanner(System.in);
            presenter.inlinePrint("Enter username of account to promote: ");
            String target = scanner.nextLine();
            sleeper.sleep(200);
            accountManager.promoteToAdmin(target);
            presenter.blockPrint("Successfully promoted user " + target + " to admin.");
        } catch (UsernameNotFoundException | UserIsAdminException e) {
            presenter.blockPrint(e.getMessage());
        }
        return false;
    }
}
