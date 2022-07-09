package gateway;

import com.google.gson.Gson;

public class ObjectToJson implements IObjectToJson {
    public <T> String toJson(T obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
}
