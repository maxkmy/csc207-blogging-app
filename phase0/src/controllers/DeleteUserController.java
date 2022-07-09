package controllers;

import exception.UserIsAdminException;
import exception.UsernameNotFoundException;
import useCases.IAccountManager;

import gateway.ISleeper;
import gateway.Sleeper;

import java.util.Scanner;

public class DeleteUserController {

    public IAccountManager accountManager;
    private ISleeper sleeper = new Sleeper();

    public DeleteUserController(IAccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void presentDeleteSelf(String requester) {
        accountManager.deleteSelf(requester);
        System.out.println("Successfully deleted user " + requester);
    }

    public void presentDeleteUser() {
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
    }
}
