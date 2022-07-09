package controllers;

import exception.IncorrectPasswordException;
import exception.UsernameNotFoundException;
import exception.AccountBannedException;
import useCases.IAccountManager;

import java.util.Scanner;

public class LoginController extends RequestController {
    final private AdminRequestFacade adminRequestFacade;
    final private AccountRequestFacade accountRequestFacade;

    public LoginController(IAccountManager accountManager){
        this.accountManager = accountManager;
        this.accountRequestFacade = new AccountRequestFacade(accountManager);
        this.adminRequestFacade = new AdminRequestFacade(accountManager);
    }

    public String getRequestDescription() {
        return "Log in to your account";
    }

    public boolean handleRequest(String requester) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        sleeper.sleep(200);
        try {
            accountManager.login(username, password);
            System.out.println("Successfully logged in.");
            if (accountManager.isAdmin(username)) {
                adminRequestFacade.setRequester(username);
                adminRequestFacade.presentRequest();
                adminRequestFacade.setRequester(null);
            } else {
                accountRequestFacade.setRequester(username);
                accountRequestFacade.presentRequest();
                accountRequestFacade.setRequester(null);
            }
        } catch (IncorrectPasswordException | UsernameNotFoundException | AccountBannedException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
