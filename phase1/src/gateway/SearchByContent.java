package gateway;

import entities.Post;
import useCases.IPostManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SearchByContent implements ISearch{
    private IPostManager postManager;

    public SearchByContent(IPostManager postManager) {
        this.postManager = postManager;
    }
    @Override
    public HashMap<String, Double> doSearch(String query) {
        HashMap<UUID, Post> curr = postManager.getMap();
        HashMap<String, Double> map = new HashMap<>();
        for(Map.Entry<UUID, Post> entry : curr.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().getContent();
            SimilarityScore score = new SimilarityScore();
            map.put(key,score.getSimilarityScore(value,query));
        }
        return map;
    }
}
