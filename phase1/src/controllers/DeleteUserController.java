package controllers;

import exception.UserIsAdminException;
import exception.UsernameNotFoundException;
import useCases.IAccountManager;

import gateway.ISleeper;
import gateway.Sleeper;

import java.util.Scanner;

public class DeleteUserController extends RequestController {

    public DeleteUserController(IAccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @Override
    public String getRequestDescription() {
        return "Delete an account";
    }

    @Override
    public boolean handleRequest(String requester) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the username of the account you wish to delete: ");
            String target = scanner.nextLine();
            sleeper.sleep(200);
            accountManager.deleteUser(target);
            System.out.println("Successfully deleted user: " + target);
        } catch (UsernameNotFoundException | UserIsAdminException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
