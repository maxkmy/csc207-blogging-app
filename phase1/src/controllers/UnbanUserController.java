package controllers;

import exception.UserIsAdminException;
import exception.UsernameNotFoundException;
import gateway.ISleeper;
import gateway.Sleeper;
import useCases.IAccountManager;

import java.util.Scanner;

public class UnbanUserController extends RequestController {
    public UnbanUserController(IAccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @Override
    public String getRequestDescription() {
        return "unban an account";
    }

    @Override
    public boolean handleRequest(String requester) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the username of the account to unban: ");
            String target = scanner.nextLine();
            sleeper.sleep(200);
            if (accountManager.unban(target)) {
                System.out.println("Successfully unbanned account " + target + ".");
            } else {
                System.out.println("Unsuccessful ban, account " + target + " was not banned.");
            }
        } catch (UsernameNotFoundException | UserIsAdminException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
