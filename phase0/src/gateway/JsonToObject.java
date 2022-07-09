package gateway;

import com.google.gson.Gson;

public class JsonToObject implements IJsonToObject {
    public <T> T fromJson(String jsonString, Class<T> className) { // Type, Class<Object>
        Gson gson = new Gson();
        return gson.fromJson(jsonString, className);
    }
}
