package controllers;

import exception.UserIsAdminException;
import exception.UsernameNotFoundException;

import useCases.IAccountManager;

import gateway.ISleeper;
import gateway.Sleeper;

import java.util.Scanner;

public class BanUserController {

    private IAccountManager accountManager;
    private ISleeper sleeper = new Sleeper();

    public BanUserController(IAccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void presentBanUser() {
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
    }

    public void presentUnbanUser() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the username of the account to unban: ");
            String target = scanner.nextLine();
            sleeper.sleep(200);
            if (accountManager.unban(target)) {
                System.out.println("Successfully unbanned account " + target + ".");
            }
            else {
                System.out.println("Unsuccessful ban, account " + target + " was not banned.");
            }
        } catch (UsernameNotFoundException | UserIsAdminException e) {
            System.out.println(e.getMessage());
        }
    }
}
