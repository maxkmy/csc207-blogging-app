package gateway;

import entities.Account;
import useCases.IAccountManager;

import java.util.*;

public class SearchByUsernameAdmin implements ISearch{
    /**
     * a use case responsible for managing accounts.
     */
    IAccountManager accountManager;
    /**
     * Constructor for a gateway responsible for performing Search using usernames.
     *
     * @param accountManager a use case responsible for managing.
     */
    public SearchByUsernameAdmin(IAccountManager accountManager) {
        this.accountManager = accountManager;
    }
    /**
     * Perform the search operation using the specified string.
     *
     * @param query a String containing the data to be searched for.
     */
    @Override
    public ArrayList<String> doSearch(String query){
        HashMap<String, Account> curr = accountManager.getMap();
        Map<String, Double> map = new HashMap<>();
        for (String key: curr.keySet()) {
            SimilarityScoreJaroWrinkler score = new SimilarityScoreJaroWrinkler();
            map.put(key,score.getSimilarityScore(key,query));
        }
        ArrayList<Map.Entry> sorted = sortMap(map);
        ArrayList<String> results = new ArrayList<>();

        for (Map.Entry e : sorted){
            results.add(e.getKey().toString());
        }
        return results;
    }

    private ArrayList<Map.Entry> sortMap (Map map) {
        ArrayList<Map.Entry> result = new ArrayList<>();
        for (Object entry : map.entrySet()) {
            result.add((Map.Entry) entry);
        }
        class CompareResults implements Comparator<Map.Entry> {
            @Override
            public int compare(Map.Entry e1, Map.Entry e2){
                if (e1.getValue() == e2.getValue()){
                    return e1.getKey().toString().compareTo(e2.getKey().toString());
                }
                return Double.compare((double) e2.getValue(), (double) e1.getValue());
            }
        }
        Collections.sort(result, new CompareResults());
        return result;
    }
}
