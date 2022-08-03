package controllers.admin;

import exception.UserFollowedException;
import exception.UserIsAdminException;
import exception.UsernameNotFoundException;
import useCases.AccountManager;
import useCases.ManagerData;

public class AdminController {

    private AccountManager accountManager;
    private ManagerData managerData;

    public AdminController(ManagerData managerData) {
        accountManager = managerData.getAccountManager();
        this.managerData = managerData;
    }

    public void promote(String user) {
        try {
            accountManager.promoteToAdmin(user);
        } catch (UsernameNotFoundException | UserIsAdminException e) {
            System.out.println(e.getMessage());
        }
    }
}
