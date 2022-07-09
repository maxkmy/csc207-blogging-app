package gateway;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;


public class Writer implements IWriter {
    String filePath;
    File file;
    FileOutputStream fileOutputStream;
    ObjectOutputStream objectOutputStream;

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
