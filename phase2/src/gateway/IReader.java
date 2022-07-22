package gateway;

public interface IReader {
    /**
     * Reads an object from memory in the required class.
     *
     * @param castClass the class that the object in memory casts to.
     * @return an object from memory in the required class.
     */
     <T> T read(Class<T> castClass);
}
