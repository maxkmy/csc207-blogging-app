package gateway;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class JsonWriter implements  IJsonWriter {
    /**
     * Given a directory it will edit the json object within the file at the given.
     * Adds or replaces the key/value at path location with input key value parameters
     * Leaving this method public as it allows to overwrite file with java object
     * instead of with string value/pair as the overloaded method is restricted to that
     * @param fileDirectory a string representing the directory of a JSON file
     * @param jsonObject    jsonObject that will be written to file
     */
    @Override
    public void writeJsonObjectTo(String fileDirectory, JSONObject jsonObject) {
        try (FileWriter file = new FileWriter(fileDirectory)) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
