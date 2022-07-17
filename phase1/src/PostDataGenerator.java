import entities.Post;
import gateway.IWriter;
import gateway.Writer;

import java.util.HashMap;

import java.util.UUID;


public class PostDataGenerator {
    public static void main(String[] args) {
        IWriter writer = new Writer("data/postData.txt");
        HashMap<UUID, Post> map = new HashMap<>();
        writer.write(map);
    }
}
