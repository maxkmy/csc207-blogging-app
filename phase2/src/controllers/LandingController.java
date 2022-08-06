package controllers;

import exception.*;
import useCases.AccountManager;
import useCases.ManagerData;

public class LandingController {
    AccountManager accountManager;

    public LandingController(ManagerData managerData) {
        this.accountManager = managerData.getAccountManager();
    }

    public String login(String username, String password) {
        try {
            accountManager.login(username, password);
            return "Success";
        } catch (IncorrectPasswordException | UsernameNotFoundException | AccountBannedException e) {
            return e.getMessage();
        }
    }

    public String signUp(String username, String password) {
        try {
            accountManager.signUp(username, password);
            return "Success";
        } catch (UsernameExistsException | InvalidUsernameException e) {
            return e.getMessage();
        }
    }
}
