package controllers.search;

import gateway.ISearch;
import useCases.IAccountManager;
import gateway.SearchByUsername;
import controllers.appWide.RequestController;
import exception.ResultNotFoundException;

import java.util.*;

public class SearchUserByUsernameController extends RequestController{
    /**
     * a use case responsible for managing accounts.
     */
    IAccountManager accountManager;
    /**
     * Search object which implements the search algorithm.
     */
    ISearch searcher;
    /**
     * Constructor for a controller responsible for handling input for search operations by username.
     *
     * @param accountManager a use case responsible for managing accounts.
     */

    public SearchUserByUsernameController(IAccountManager accountManager){
        this.accountManager = accountManager;
        searcher = new SearchByUsername(accountManager);
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() { return "Search for a user by their username"; }

    /**
     * @inheritDOc
     */
    @Override
    public boolean handleRequest(String requester){
        Scanner scanner = new Scanner(System.in);
        presenter.inlinePrint("Enter the username of the account you wish to search: ");
        String username = scanner.nextLine();
        sleeper.sleep(200);
        ArrayList<String> result = searcher.doSearch(username);
        presenter.printMessages(result, "\n");

        return false;
    }

}
