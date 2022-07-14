import entities.Like;
import gateway.IWriter;
import gateway.Writer;

import java.util.HashMap;
import java.util.UUID;

public class likeDataGenerator {
    public static void main(String[] args) {
        IWriter writer = new Writer("data/likeData.txt");
        HashMap<UUID, Like> map = new HashMap<>();
        writer.write(map);
    }
}
