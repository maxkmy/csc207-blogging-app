package gateway;

import entities.Account;
import useCases.IAccountManager;

import java.util.HashMap;

public class SearchByUsername implements ISearch{
    IAccountManager accountManager;

    public SearchByUsername(IAccountManager accountManager) {
        this.accountManager = accountManager;
    }
    @Override
    public HashMap<String, Double> doSearch(String query) {
        HashMap<String, Account> curr = accountManager.getMap();
        HashMap<String, Double> map = new HashMap<>();
        for (String key: curr.keySet()) {
            SimilarityScore score = new SimilarityScore();
            map.put(key,score.getSimilarityScore(key,query));
        }
        return map;
    }
}
