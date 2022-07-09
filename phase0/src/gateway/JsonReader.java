package gateway;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonReader implements IJsonReader {
    @Override
    public JSONObject getJsonObject(String fileDirectory) {
        JSONParser jsonParser = new JSONParser();

        try {
            FileReader reader = new FileReader(fileDirectory);
            Object obj = jsonParser.parse(reader);
            return (JSONObject) obj;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}