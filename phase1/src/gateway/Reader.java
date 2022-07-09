package gateway;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Reader implements IReader {
    /**
     * a string representing the file path of the file
     */
    String filePath;

    /**
     * Constructor of an object that reads serialized data in a given file path.
     *
     * @param filePath a string representing the file path of the file.
     */
    public Reader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads the object stored in the file given by filePath.
     *
     * @return the object that is read from the file stored in the file given by filePath
     */
    @Override
    public <T> T read(Class<T> castClass) {
        try {
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return castClass.cast(objectInputStream.readObject());
        } catch (FileNotFoundException e) {
            System.out.println("The provided file path is invalid.");
        } catch (IOException | ClassNotFoundException e){
            System.out.println("An error has occurred.");
        }
        return null;
    }
}

