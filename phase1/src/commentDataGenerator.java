import entities.Comment;
import gateway.IWriter;
import gateway.Writer;

import java.util.HashMap;
import java.util.UUID;

public class commentDataGenerator {
    public static void main(String[] args) {
        IWriter writer = new Writer("data/commentData.txt");
        HashMap<UUID, Comment> map = new HashMap<>();
        writer.write(map);
    }
}
