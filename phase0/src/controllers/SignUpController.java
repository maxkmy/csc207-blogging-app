package controllers;

import java.util.Scanner;

import exception.UsernameExistsException;
import useCases.IAccountManager;

import gateway.Sleeper;
import gateway.ISleeper;


public class SignUpController {
    private IAccountManager accountManager;
    private AccountRequestFacade accountRequestFacade;
    private ISleeper sleeper = new Sleeper();

    public SignUpController(IAccountManager accountManager) {
        this.accountManager = accountManager;
        this.accountRequestFacade = new AccountRequestFacade(accountManager);
    }

    public void presentSignUp() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        sleeper.sleep(200);
        try {
            accountManager.signUp(username, password);
            System.out.println("Successfully signed up.");
            sleeper.sleep(200);
            accountRequestFacade.setRequester(username);
            accountRequestFacade.presentRequest();
        } catch (UsernameExistsException e){
            System.out.println(e.getMessage());
        }
        System.out.println();
    }
}

