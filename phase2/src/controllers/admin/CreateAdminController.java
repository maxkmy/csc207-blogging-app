package controllers.admin;

import controllers.appWide.RequestController;
import exception.UsernameExistsException;
import useCases.IAccountManager;

import java.util.Scanner;

public class CreateAdminController extends RequestController {
    /**
     * Constructor for a controller responsible for handling input related to create new admin accounts.
     *
     * @param accountManager  a use case responsible for managing accounts
     */
    public CreateAdminController(IAccountManager accountManager) {
        this.accountManager = accountManager;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() {
        return "make a new admin account";
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester) {
        try {
            Scanner scanner = new Scanner(System.in);
            presenter.inlinePrint("Enter the username of the admin account to be created: ");
            String username = scanner.nextLine();
            presenter.inlinePrint("Enter the password of the admin account to be created: ");
            String password = scanner.nextLine();
            accountManager.createAdmin(username, password);
            presenter.blockPrint("Successfully created admin " + username + ".");
        } catch (UsernameExistsException e) {
            presenter.blockPrint(e.getMessage());
        }
        return false;
    }
}
