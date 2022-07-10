package gateway;

import java.util.HashMap;

public class SearchContext {
    private ISearch searchObj;

    public SearchContext(ISearch searchObj) {
        this.searchObj = searchObj;
    }

    public HashMap<String, Double> executeSearch(String query) {
        return searchObj.doSearch(query);
    }
}
