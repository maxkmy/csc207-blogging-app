package controllers.search;

import gateway.ISearch;
import useCases.IAccountManager;
import gateway.SearchByUsernameRegular;
import controllers.appWide.RequestController;

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
    public SearchUserByUsernameController(IAccountManager accountManager, ISearch searcher){
        this.accountManager = accountManager;
        this.searcher = searcher;
    }
    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() { return "Search for a user by their username"; }
    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester){
        Scanner scanner = new Scanner(System.in);
        presenter.inlinePrint("Enter the username of the account you wish to search: ");
        String username = scanner.nextLine();
        sleeper.sleep(200);
        presenter.inlinePrint("Here are the top search results:");
        ArrayList<String> result = searcher.doSearch(username);
        presenter.printMessages(result, "\n");
        return false;
    }

}
