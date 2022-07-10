package controllers.search;

import gateway.SearchByTitle;
import useCases.IAccountManager;
import useCases.IPostManager;
import gateway.SearchByUsername;
import gateway.SearchContext;
import gateway.SearchByContent;


public class SearchController {

    IAccountManager accountManager;
    IPostManager postManager;

    public SearchController(IAccountManager accountManager, IPostManager postManager) {
        this.accountManager = accountManager;
        this.postManager = postManager;
    }

    public boolean searchTitle(String query) {
        SearchContext context = new gateway.SearchContext(new SearchByTitle(postManager));
        context.executeSearch(query);
        return true;
    }

    public boolean searchContent(String query) {
        SearchContext context = new gateway.SearchContext(new SearchByContent(postManager));
        context.executeSearch(query);
        return true;
    }

    public boolean searchUsername(String query) {
        SearchContext context = new gateway.SearchContext(new SearchByUsername(accountManager));
        context.executeSearch(query);
        return true;
    }
}
