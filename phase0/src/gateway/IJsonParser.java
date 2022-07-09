package gateway;

import org.json.simple.JSONObject;

public interface IJsonParser {
    JSONObject toJSONObject(String jsonString);
}
