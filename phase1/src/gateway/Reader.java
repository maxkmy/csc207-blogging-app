package gateway;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Reader implements IReader {
    String filePath;
    File file;
    FileInputStream fileInputStream;
    ObjectInputStream objectInputStream;
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

