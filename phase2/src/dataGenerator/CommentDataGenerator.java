package dataGenerator;

import entities.Comment;
import gateway.IWriter;
import gateway.Writer;

import java.util.HashMap;
import java.util.UUID;

public class CommentDataGenerator {
    public static void main(String[] args) {
        IWriter writer1 = new Writer("data/commentData.txt");
        IWriter writer2 = new Writer("test/testData/testCommentData.txt");
        HashMap<UUID, Comment> map = new HashMap<>();
        writer1.write(map);
        writer2.write(map);
    }
}
