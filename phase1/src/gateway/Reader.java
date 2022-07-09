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
     * a file object to be instantiated based on the filePath
     */
    File file;
    /**
     * a file input stream object to be instantiated based on the file object
     */
    FileInputStream fileInputStream;
    /**
     * an object input stream object to be instantiated based on the file input stream
     */
    ObjectInputStream objectInputStream;

    /**
     * Constructor of an object that reads serialized data in a given file path.
     *
     * @param filePath a string representing the file path of the file.
     */
    public Reader(String filePath) {
        this.filePath = filePath;
        file = new File(filePath);
        try {
            fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);
        } catch(FileNotFoundException e) {
            System.out.println("The provided file path is invalid.");
        } catch (IOException e) {
            System.out.println("An error has occurred.");
        }
    }

    /**
     * Reads the object stored in the file given by filePath.
     *
     * @return the object that is read from the file stored in the file given by filePath
     */
    @Override
    public Object read() {
        try {
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("An error has occurred.");
            return null;
        }
    }
}

