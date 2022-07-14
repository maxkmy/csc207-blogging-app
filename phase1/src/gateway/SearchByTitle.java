package gateway;

import entities.Post;
import useCases.IPostManager;


import java.util.*;

public class SearchByTitle implements ISearch{
    /**
     * a use case responsible for managing posts.
     */
    private IPostManager postManager;
    /**
     * Constructor for a gateway responsible for performing Search using titles.
     *
     * @param postManager a use case responsible for managing postss.
     */
    public SearchByTitle(IPostManager postManager) {
        this.postManager = postManager;
    }
    /**
     * Perform the search operation using the specified string.
     *
     * @param query a String containing the data to be searched for.
     */
    @Override
    public ArrayList<String> doSearch(String query){
        HashMap<UUID, Post> curr = postManager.getMap();
        HashMap<String, Double> map = new HashMap<>();
        for(Map.Entry<UUID, Post> entry : curr.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().getTitle();
            SimilarityScoreLevenshtein score = new SimilarityScoreLevenshtein();
            map.put(postManager.getPost(entry.getKey()).getTitle(),score.getSimilarityScore(value,query));
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
