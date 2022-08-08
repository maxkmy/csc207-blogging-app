package viewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewModel {
    private Map<String, Object> context;

    public ViewModel() {
        context = new HashMap<>();
    }

    /**
     * Adds a field (for HTML forms) to the context map.
     */
    public void addFormField(String id, String label, String type) {
        if (context.get("fields") == null) {
            List<Map<String, String>> fields = new ArrayList<>();
            context.put("fields", fields);
        }
        List<Map<String, String>> fields = (List<Map<String, String>>) context.get("fields");
        Map<String, String> field = new HashMap<>();
        field.put("id", id);
        field.put("label", label);
        field.put("type", type);
        fields.add(field);
    }

    /**
     * Adds an HTTP endpoint and its description to the context map.
     */
    public void addEndpoint(String endpoint, String description) {
        if (context.get("requests") == null) {
            List<Map<String, String>> requests = new ArrayList<>();
            context.put("requests", requests);
        }
        List<Map<String, String>> requests = (List<Map<String, String>>) context.get("requests");
        Map<String, String> request = new HashMap<>();
        request.put("endpoint", endpoint);
        request.put("description", description);
        requests.add(request);
    }

    /**
     * Adds a key-value mapping to the context map.
     */
    public void put(String key, Object value) {
        context.put(key, value);
    }

    /**
     * Returns the context mapping.
     */
    public Map<String, Object> getContext() {
        return context;
    }
}