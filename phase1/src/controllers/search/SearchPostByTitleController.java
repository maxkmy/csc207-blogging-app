package controllers.search;

import gateway.ISearch;

import useCases.IPostManager;
import gateway.SearchByTitle;
import controllers.appWide.RequestController;


import java.util.*;

public class SearchPostByTitleController extends RequestController {
    /**
     * a use case responsible for managing posts.
     */
    IPostManager postManager;
    /**
     * Search object which implements the search algorithm.
     */
    ISearch searcher;
    /**
     * Constructor for a controller responsible for handling input for search operations by username.
     *
     * @param postManager a use case responsible for managing accounts.
     */
    public SearchPostByTitleController(IPostManager postManager){
        this.postManager = postManager;
        searcher = new SearchByTitle(postManager);
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() { return "Search for a post by its title"; }

    /**
     * @inheritDOc
     */
    @Override
    public boolean handleRequest(String requester){
        Scanner scanner = new Scanner(System.in);
        presenter.inlinePrint("Enter the title of the post you wish to search: ");
        String title = scanner.nextLine();
        sleeper.sleep(200);
        ArrayList<String> result = searcher.doSearch(title);
        presenter.inlinePrint("Here are the top search results:");
        presenter.printMessages(result, "\n");

        return false;
    }


}
