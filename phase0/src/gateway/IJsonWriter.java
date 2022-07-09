package gateway;

import org.json.simple.JSONObject;

public interface IJsonWriter {
    void writeJsonObjectTo(String fileDirectory, JSONObject jsonObject);
}
