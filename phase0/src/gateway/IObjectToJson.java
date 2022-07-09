package gateway;

public interface IObjectToJson {
    public <T> String toJson(T obj);
}
