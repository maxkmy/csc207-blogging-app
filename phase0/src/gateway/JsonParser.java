package gateway;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;

public class JsonParser implements IJsonParser {
    private final JSONParser parser = new JSONParser();
    @Override
    public JSONObject toJSONObject(String jsonString) {
        try {
            return (JSONObject) parser.parse(jsonString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
