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
     * Constructor of an object that writes a serializable object to  data in a given file path.
     *
     * @param filePath a string representing the file path of the file.
     */
    public Writer(String filePath)  {
        this.filePath = filePath;
    }

    /**
     * Write the serializable object to the file given by filePath.
     */
    @Override
    public void write(Serializable object) {
        try {
            File file = new File(filePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println("An error has occurred.");
        }
    }
}
