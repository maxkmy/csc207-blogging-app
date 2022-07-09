package gateway;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;


public class Writer implements IWriter {
    /**
     * a string representing the file path of the file
     */
    String filePath;
    /**
     * a file object to be instantiated based on the filePath
     */
    File file;
    /**
     * a file output stream object to be instantiated based on the file object
     */
    FileOutputStream fileOutputStream;
    /**
     * an object output stream object to be instantiated based on the file output stream
     */
    ObjectOutputStream objectOutputStream;

    /**
     * Constructor of an object that writes a serializable object to  data in a given file path.
     *
     * @param filePath a string representing the file path of the file.
     */
    public Writer(String filePath)  {
        this.filePath = filePath;
        file = new File(filePath);
        try {
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
        } catch(FileNotFoundException e) {
            System.out.println("The provided file path is invalid.");
        } catch (IOException e) {
            System.out.println("An error has occurred.");
        }
    }

    /**
     * Write the serializable object to the file given by filePath.
     */
    @Override
    public void write(Serializable object) {
        try {
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println("An error has occurred.");
        }
    }
}
