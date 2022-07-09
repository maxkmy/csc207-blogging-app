import controllers.LandingPageFacade;

import useCases.AccountManager;
import useCases.IAccountManager;
import gateway.IReader;
import gateway.Reader;

import entities.Account;

import java.util.HashMap;

public class App {
    public static void main(String[] args) {
        final String dataFileDirectory = "data/userData.txt";
        IReader reader = new Reader(dataFileDirectory);
        HashMap<String, Account> accountMap = (HashMap<String, Account>) reader.read();
        IAccountManager accountManager = new AccountManager(accountMap);
        LandingPageFacade landingPageFacade = new LandingPageFacade(accountManager);
        landingPageFacade.presentRequest();
    }
}
