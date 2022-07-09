package controllers;

import java.util.Scanner;
import useCases.IAccountManager;

import gateway.ISleeper;
import gateway.Sleeper;

public class AdminRequestFacade extends AccountRequestFacade {
    private BanUserController banUserController;
    private AdminManagementController adminManagementController;
    private ISleeper sleeper = new Sleeper();
    private final String requests = "Please enter your request. \n" +
            "\"Ban\" - ban an account \n" +
            "\"Unban\" - unban an account \n" +
            "\"Promote user\" - promote user to admin \n" +
            "\"Create admin\" - make a new admin account \n" +
            "\"Delete\" - delete your account \n" +
            "\"Delete user\" - delete an account \n" +
            "\"Log out\" - log out of your account \n" +
            "\"View history\" - view history of your logins \n" +
            "Request: ";

    public AdminRequestFacade(IAccountManager accountManager) {
        super(accountManager);
        banUserController = new BanUserController(accountManager);
        adminManagementController = new AdminManagementController(accountManager);
    }

    @Override
    public void handleRequest(String request) {
        if (request.equals("Ban")) {
            banUserController.presentBanUser();
        } else if (request.equals("Unban")) {
            banUserController.presentUnbanUser();
        } else if (request.equals("Delete")) {
            deleteUserController.presentDeleteSelf(requester);
            setRequester(null);
            return;
        } else if (request.equals("Delete user")) {
            deleteUserController.presentDeleteUser();
        } else if (request.equals("View history")) {
            viewHistoryController.presentViewHistory(requester);
        } else if (request.equals("Promote user")) {
            adminManagementController.presentPromoteUser();
        } else if (request.equals("Log out")) {
            logoutController.presentLogout(requester);
            setRequester(null);
            return;
        } else if (request.equals("Create admin")) {
            adminManagementController.presentMakeAdmin();
        } else {
            System.out.println("The request entered is invalid");
        }
        presentRequest();
    }

    @Override
    public void presentRequest() {
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print(requests);
        String request = scanner.nextLine();
        sleeper.sleep(200);
        System.out.println();
        handleRequest(request);
    }
}
