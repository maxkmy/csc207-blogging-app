package controllers;

import useCases.IAccountManager;

import gateway.ISleeper;
import gateway.Sleeper;

import java.util.Scanner;

public class AccountRequestFacade {
    protected DeleteUserController deleteUserController;
    protected LogoutController logoutController;
    protected ViewHistoryController viewHistoryController;
    protected String requester;
    private final String requests = "Please enter your request. \n" +
            "\"Delete\" - delete your account \n" +
            "\"Log out\" - log out of your account \n" +
            "\"View history\" - view history of your logins \n" +
            "Request: ";
    protected ISleeper sleeper;

    public AccountRequestFacade(IAccountManager accountManager) {
        deleteUserController = new DeleteUserController(accountManager);
        logoutController = new LogoutController();
        viewHistoryController = new ViewHistoryController(accountManager);
        sleeper = new Sleeper();
    }

    public void handleRequest(String request) {
        if (request.equals("Delete")) {
            deleteUserController.presentDeleteSelf(requester);
            setRequester(null);
            return;
        } else if (request.equals("View history")) {
            viewHistoryController.presentViewHistory(requester);
        } else if (request.equals("Log out")) {
            logoutController.presentLogout(requester);
            setRequester(null);
            return;
        } else {
            System.out.println("The request entered is invalid.");
        }
        sleeper.sleep(200);
        presentRequest();
    }

    public void presentRequest() {
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print(requests);
        String request = scanner.nextLine();
        sleeper.sleep(200);
        System.out.println();
        handleRequest(request);
    }

    // note requester is the username of the account currently logged in
    public void setRequester(String requester) {
        this.requester = requester;
    }
}

