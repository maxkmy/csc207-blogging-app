package gateway;

import java.io.Serializable;

public interface IWriter {
    /**
     * Write an object to some external data storage.
     *
     * @param object an object that can be serialized
     */
    void write(Serializable object);
}
