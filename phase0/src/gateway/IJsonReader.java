package gateway;

import org.json.simple.JSONObject;

public interface IJsonReader {
    JSONObject getJsonObject(String fileDirectory);
}
