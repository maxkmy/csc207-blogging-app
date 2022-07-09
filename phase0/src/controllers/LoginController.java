package controllers;

import exception.IncorrectPasswordException;
import exception.UsernameNotFoundException;
import exception.AccountBannedException;
import useCases.IAccountManager;

import gateway.ISleeper;
import gateway.Sleeper;

import java.util.Scanner;

public class LoginController {

    private IAccountManager accountManager;
    private AdminRequestFacade adminRequestFacade;
    private AccountRequestFacade accountRequestFacade;
    private ISleeper sleeper = new Sleeper();

    public LoginController(IAccountManager accountManager){
        this.accountManager = accountManager;
        this.accountRequestFacade = new AccountRequestFacade(accountManager);
        this.adminRequestFacade = new AdminRequestFacade(accountManager);
    }

    public void presentLogin() {
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
            } else {
                accountRequestFacade.setRequester(username);
                accountRequestFacade.presentRequest();
            }
        } catch (IncorrectPasswordException | UsernameNotFoundException | AccountBannedException e) {
            System.out.println(e.getMessage());
        }
    }
}
