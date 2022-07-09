package controllers;

import exception.UsernameExistsException;
import gateway.ISleeper;
import gateway.Sleeper;
import useCases.IAccountManager;

import java.util.Scanner;

public class CreateAdminController extends RequestController {

    public CreateAdminController(IAccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @Override
    public String getRequestDescription() {
        return "make a new admin account";
    }

    @Override
    public boolean handleRequest(String requester) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the username of the admin account to be created: ");
            String username = scanner.nextLine();
            System.out.print("Enter the password of the admin account to be created: ");
            String password = scanner.nextLine();
            sleeper.sleep(200);
            accountManager.createAdmin(username, password);
            System.out.println("Successfully created admin " + username + ".");
        } catch (UsernameExistsException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
