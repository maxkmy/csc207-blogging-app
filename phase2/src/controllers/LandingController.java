package controllers;

import exception.*;
import useCases.AccountManager;
import useCases.ManagerData;

public class LandingController {
    /**
     * a use case responsible for managing accounts
     */
    private AccountManager accountManager;

    /**
     * Constructor of a controller for sign up and login
     *
     * @param managerData an object that groups use cases together
     */
    public LandingController(ManagerData managerData) {
        this.accountManager = managerData.getAccountManager();
    }

    /**
     * Logs in a user
     *
     * @param username a username
     * @param password a password
     * @return the status of the login
     */
    public String login(String username, String password) {
        try {
            accountManager.login(username, password);
            return "Success";
        } catch (IncorrectPasswordException | UsernameNotFoundException | AccountBannedException e) {
            return e.getMessage();
        }
    }

    /**
     * Signs up a user
     *
     * @param username a username
     * @param password a password
     * @return the status of the sign up
     */
    public String signUp(String username, String password) {
        try {
            accountManager.signUp(username, password);
            return "Success";
        } catch (UsernameExistsException | InvalidUsernameException e) {
            return e.getMessage();
        }
    }
}
