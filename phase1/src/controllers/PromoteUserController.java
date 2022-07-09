package controllers;

import exception.*;
import useCases.IAccountManager;
import gateway.ISleeper;
import gateway.Sleeper;

import java.util.Scanner;

public class PromoteUserController extends RequestController {

    public PromoteUserController(IAccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @Override
    public String getRequestDescription() {
        return "promote user to admin";
    }

    @Override
    public boolean handleRequest(String requester) {
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
        return false;
    }
}
