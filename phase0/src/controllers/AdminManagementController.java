package controllers;

import exception.*;
import useCases.IAccountManager;
import gateway.ISleeper;
import gateway.Sleeper;

import java.util.Scanner;

public class AdminManagementController {

    private IAccountManager accountManager;
    private ISleeper sleeper = new Sleeper();

    public AdminManagementController(IAccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void presentMakeAdmin() {
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
    }

    public void presentPromoteUser() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter username of account to promote: ");
            String target = scanner.nextLine();
            sleeper.sleep(200);
            accountManager.promoteToAdmin(target);
            System.out.println("Successfully promoted user " + target + " to admin.");
        } catch (UsernameNotFoundException | UserIsAdminException e) {
            System.out.println(e.getMessage());
        }
    }
}
