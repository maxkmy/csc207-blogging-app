package controllers;

import exception.UserIsAdminException;
import exception.UsernameNotFoundException;

import useCases.IAccountManager;

import gateway.ISleeper;
import gateway.Sleeper;

import java.util.Scanner;

public class BanUserController extends RequestController {

    final private IAccountManager accountManager;

    public BanUserController(IAccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @Override
    public String getRequestDescription() {
        return "ban an account";
    }

    @Override
    public boolean handleRequest(String requester) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the username of the account you wish to ban: ");
            String target = scanner.nextLine();
            sleeper.sleep(200);
            if (accountManager.ban(target)) {
                System.out.println("Successfully banned account: " + target + ".");
            } else {
                System.out.println("Unsuccessful ban, account " + target + " was already banned.");
            }
        } catch (UsernameNotFoundException |  UserIsAdminException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
