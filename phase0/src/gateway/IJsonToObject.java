package gateway;

public interface IJsonToObject {
     public <T> T fromJson(String jsonString, Class<T> className);
}
